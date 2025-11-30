package com.example.LMS.service;

import com.example.LMS.Exception.AppException;
import com.example.LMS.Exception.ErrorCode;
import com.example.LMS.dto.ApiResponse;
import com.example.LMS.dto.Request.EnrollmentRequest;
import com.example.LMS.dto.Request.EnrollmentUpdate;
import com.example.LMS.dto.Response.EnrollmentResponse;
import com.example.LMS.dto.dtoProjection.*;
import com.example.LMS.entity.Course;
import com.example.LMS.entity.Enrollment;
import com.example.LMS.entity.Image;
import com.example.LMS.enums.Status;
import com.example.LMS.mapper.CourseMapper;
import com.example.LMS.mapper.EnrollmentMapper;
import com.example.LMS.repo.CourseRepo;
import com.example.LMS.repo.EnrollmentRepo;
import org.springframework.context.MessageSource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class EnrollmentService {
    EnrollmentRepo enrollmentRepo;
    EnrollmentMapper enrollmentMapper;
    CourseRepo courseRepo;
    MessageSource messageSource;
    CourseMapper courseMapper;

    public EnrollmentService(EnrollmentRepo enrollmentRepo, EnrollmentMapper enrollmentMapper,
                             CourseRepo courseRepo, MessageSource messageSource,
                             CourseMapper courseMapper) {
        this.enrollmentRepo = enrollmentRepo;
        this.enrollmentMapper = enrollmentMapper;
        this.courseRepo = courseRepo;
        this.messageSource = messageSource;
        this.courseMapper = courseMapper;
    }
    @Transactional(rollbackFor = Exception.class)
    public List<EnrollmentResponse> createEnrollment(EnrollmentRequest enrollmentRequest){
        List<Enrollment> enrollmentList = new ArrayList<>();

        //check courseId request ton tai khong
        List<CourseImageDTO> coursesImg = courseRepo.findCoursesWithActiveThumbnails(enrollmentRequest.getCourseIds());
        List<CourseDTO> courses = courseRepo.findCourseDTOsByIds(enrollmentRequest.getCourseIds());
        if(courses.size() != enrollmentRequest.getCourseIds().size()){
            throw new AppException(ErrorCode.COURSE_NOT_FOUND);
        }
        Map<Long, List<Image>> thumbnailsMap = coursesImg.stream()
                .collect(Collectors.groupingBy(
                        CourseImageDTO::getId,
                        Collectors.mapping(CourseImageDTO::getThumbnail, Collectors.toList())
                ));
        courses.forEach(course -> course.setThumbnail(
                thumbnailsMap.getOrDefault(course.getId(), List.of())));
        List<Course> courseList = courseMapper.toCourseFromDTOs(courses);

        List<Enrollment> enrollments = enrollmentRepo.findAllByCourseIdInAndUserId(enrollmentRequest.getCourseIds(),enrollmentRequest.getUserId());
        if(enrollments != null && !enrollments.isEmpty()){
            throw new AppException(ErrorCode.ENROLLMENT_EXISTS);
        }
        Boolean a = (enrollmentRequest.getCourseIds().size() == courses.size());
        courseList.forEach(course -> {
            Enrollment enrollment = Enrollment.builder()
                .userId(enrollmentRequest.getUserId())
                .courseId(course.getId())
                .enrolledAt(LocalDateTime.now())
                .build();
            enrollmentList.add(enrollment);});
        enrollmentRepo.saveAll(enrollmentList);
        return enrollmentMapper.toEnrollmentResponses(enrollmentList);
    }
    public Page<EnrollmentResponse> getEnrollmentsByUser(
            int page, int size, Long userId, Status status
    ) {
        Pageable pageable = PageRequest.of(page, size);

        Page<EnrollmentCourseDTO> enrollmentPage =
                enrollmentRepo.findEnrollmentsWithCourse(userId, status, pageable);

        List<Long> courseIds = enrollmentPage.getContent().stream()
                .map(e -> e.getCourse().getId())
                .toList();

        List<CourseImageDTO> courseImages = enrollmentRepo.findCourseImageDTOBy1(courseIds);
        Map<Long, List<Image>> thumbMap = courseImages.stream()
                .collect(Collectors.groupingBy(
                        CourseImageDTO::getId,
                        Collectors.mapping(CourseImageDTO::getThumbnail, Collectors.toList())
                ));

        List<EnrollmentResponse> responses = enrollmentPage.getContent().stream()
                .map(e -> {
                    CourseDTO courseDTO = e.getCourse();
                    Course course = courseMapper.toCourseFromDTO(courseDTO);
                    courseDTO.setThumbnail(thumbMap.getOrDefault(courseDTO.getId(), List.of()));

                    EnrollmentResponse resp = new EnrollmentResponse();
                    resp.setId(e.getEnrollmentId());
                    resp.setCourse(course);
                    resp.setEnrolledAt(e.getEnrolledAt());
                    return resp;
                }).toList();

        return new PageImpl<>(responses, pageable, enrollmentPage.getTotalElements());
    }
    @Transactional
    public void updateEnrollment(Long enrollmentId, EnrollmentUpdate enrollmentUpdate) {
        Enrollment enrollment = enrollmentRepo.findByIdAndStatus(enrollmentId, Status.ACTIVE)
                .orElseThrow(() -> new AppException(ErrorCode.ENROLLMENT_NOT_FOUND));
        Long userId = enrollment.getUserId();
        List<Long> newCourseIds = enrollmentUpdate.getNewCourseIds();
        List<Long> deleteCourseIds = enrollmentUpdate.getDeleteCourseIds();

        //list cac course xoa
        if(deleteCourseIds != null && !deleteCourseIds.isEmpty()){

            List<Enrollment> enrollments = enrollmentRepo.findAllByCourseIdInAndUserId(deleteCourseIds,userId);
            Set<Long> enrolledCourseIds = enrollments.stream()
                    .map(e -> e.getCourseId())
                    .collect(Collectors.toSet());

            for (Long id : deleteCourseIds) {
                if (!enrolledCourseIds.contains(id)) {
                    throw new AppException(ErrorCode.ENROLLMENT_NOT_FOUND);
                }
            }
            enrollmentRepo.deleteByCourseIdInAndUserId(deleteCourseIds, userId);
        }

        //list cac courst add
        if(newCourseIds != null && !newCourseIds.isEmpty()){
            List<Course> courseListNew = courseRepo.findAllByIdInAndStatus(newCourseIds,Status.ACTIVE);
            if(newCourseIds.size() != courseListNew.size()){
                throw new AppException(ErrorCode.COURSE_NOT_FOUND);
            }

            List<Enrollment> enrollments = enrollmentRepo.findAllByCourseIdInAndUserId(newCourseIds,userId);


            Set<Long> alreadyEnrolled = enrollments.stream()
                    .map(e -> e.getCourseId())
                    .collect(Collectors.toSet());

            List<Long> idsToAdd = newCourseIds.stream()
                    .filter(id -> !alreadyEnrolled.contains(id))
                    .toList();

            List<Enrollment> toSave = new ArrayList<>();
            for(Long id : idsToAdd){
                toSave.add(
                        Enrollment.builder()
                                .userId(userId)
                                .courseId(id)
                                .enrolledAt(LocalDateTime.now())
                                .build()
                );
            }
            enrollmentRepo.saveAll(toSave);
        }
    }
    public Page<Long> getUserIdsOfCourse(int page, int size, Long courseId, Status status) {
        if (!courseRepo.existsByIdAndStatus(courseId, status)) {
            throw new AppException(ErrorCode.COURSE_NOT_FOUND);
        }

        Pageable pageable = PageRequest.of(page, size);

        return enrollmentRepo.findUserIdsByCourseId(courseId, status, pageable);
    }

    @Transactional
    public ApiResponse<Void> deleteEnrollment(Long enrollmentId) {
        Enrollment enrollment = enrollmentRepo.findByIdAndStatus(enrollmentId, Status.ACTIVE)
                        .orElseThrow(() -> new AppException(ErrorCode.ENROLLMENT_NOT_FOUND));
        enrollmentRepo.delete(enrollment);
        return ApiResponse.<Void>builder()
                .build();
    }

}
