package com.example.LMS.repo;

import com.example.LMS.dto.dtoProjection.*;
import com.example.LMS.entity.Enrollment;
import com.example.LMS.enums.Status;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface EnrollmentRepo extends JpaRepository<Enrollment, Long> {
    Optional<Enrollment> findByIdAndStatus(Long id, Status status);

    void deleteByCourseIdInAndUserId(List<Long> courseIds, Long userId);

    List<Enrollment> findAllByCourseIdInAndUserId(List<Long> courseIds, Long userId);

    @Query("SELECT e.userId FROM Enrollment e WHERE e.courseId = :courseId AND e.status = :status")
    Page<Long> findUserIdsByCourseId(@Param("courseId") Long courseId, @Param("status") Status status, Pageable pageable);

    @Query("""
SELECT new com.example.LMS.dto.dtoProjection.CourseDTO(
    c.id,
    c.name,
    c.code,
    c.description,
    c.status
)
FROM Enrollment e
JOIN Course c ON e.courseId = c.id
WHERE e.userId = :userId AND e.status = :status
""")
    Page<CourseDTO> findCoursesByUserId(
            @Param("userId") Long userId,
            @Param("status") Status status,
            Pageable pageable
    );

    @Query("""
SELECT new com.example.LMS.dto.dtoProjection.CourseImageDTO(c.id, i)
FROM Course c
LEFT JOIN Image i ON i.objectId = c.id AND i.objectType = 'COURSE' AND i.status = 'ACTIVE'
WHERE c.id IN :courseIds
""")
    List<CourseImageDTO> findCourseImageDTOBy1(@Param("courseIds") List<Long> courseIds);

    @Query("""
SELECT new com.example.LMS.dto.dtoProjection.EnrollmentCourseDTO(
    e.id, e.enrolledAt,
    c.id, c.name, c.code,
    c.description, c.status
)
FROM Enrollment e
JOIN Course c ON e.courseId = c.id
WHERE e.userId = :userId AND e.status = :status
""")
    Page<EnrollmentCourseDTO> findEnrollmentsWithCourse(@Param("userId") Long userId,
                                                        @Param("status") Status status,
                                                        Pageable pageable);

    @Query("SELECT e.courseId FROM Enrollment e WHERE e.userId = :userId AND e.status = :status")
    Page<Long> findCourseIdsByUserIdAndStatus(@Param("userId") Long userId, @Param("status") Status status, Pageable pageable);
}
