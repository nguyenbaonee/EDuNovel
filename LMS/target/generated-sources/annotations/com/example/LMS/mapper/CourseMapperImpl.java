package com.example.LMS.mapper;

import com.example.LMS.dto.Request.CourseRequest;
import com.example.LMS.dto.Request.CourseUpdate;
import com.example.LMS.dto.Response.CourseResponse;
import com.example.LMS.dto.dtoProjection.CourseDTO;
import com.example.LMS.entity.Course;
import com.example.LMS.entity.Image;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-11-29T22:26:05+0700",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 21.0.5 (Oracle Corporation)"
)
@Component
public class CourseMapperImpl implements CourseMapper {

    @Autowired
    private ImageMapper imageMapper;

    @Override
    public Course toCourse(CourseRequest courseRequest) {
        if ( courseRequest == null ) {
            return null;
        }

        Course.CourseBuilder course = Course.builder();

        course.name( courseRequest.getName() );
        course.code( courseRequest.getCode() );
        course.description( courseRequest.getDescription() );

        return course.build();
    }

    @Override
    public Course toCourseFromDTO(CourseDTO courseDTO) {
        if ( courseDTO == null ) {
            return null;
        }

        Course.CourseBuilder course = Course.builder();

        course.id( courseDTO.getId() );
        course.name( courseDTO.getName() );
        course.code( courseDTO.getCode() );
        List<Image> list = courseDTO.getThumbnail();
        if ( list != null ) {
            course.thumbnail( new ArrayList<Image>( list ) );
        }
        course.description( courseDTO.getDescription() );
        course.status( courseDTO.getStatus() );

        return course.build();
    }

    @Override
    public CourseResponse toCourseResponse(Course course) {
        if ( course == null ) {
            return null;
        }

        CourseResponse courseResponse = new CourseResponse();

        courseResponse.setId( course.getId() );
        courseResponse.setName( course.getName() );
        courseResponse.setCode( course.getCode() );
        courseResponse.setThumbnail( imageMapper.toImageResponses( course.getThumbnail() ) );
        courseResponse.setDescription( course.getDescription() );
        courseResponse.setStatus( course.getStatus() );

        return courseResponse;
    }

    @Override
    public CourseResponse toResponseFromDTO(CourseDTO courseDTO) {
        if ( courseDTO == null ) {
            return null;
        }

        CourseResponse courseResponse = new CourseResponse();

        courseResponse.setId( courseDTO.getId() );
        courseResponse.setName( courseDTO.getName() );
        courseResponse.setCode( courseDTO.getCode() );
        courseResponse.setThumbnail( imageMapper.toImageResponses( courseDTO.getThumbnail() ) );
        courseResponse.setDescription( courseDTO.getDescription() );
        courseResponse.setStatus( courseDTO.getStatus() );

        return courseResponse;
    }

    @Override
    public List<Course> toCourses(List<CourseRequest> courseRequests) {
        if ( courseRequests == null ) {
            return null;
        }

        List<Course> list = new ArrayList<Course>( courseRequests.size() );
        for ( CourseRequest courseRequest : courseRequests ) {
            list.add( toCourse( courseRequest ) );
        }

        return list;
    }

    @Override
    public List<Course> toCourseFromDTOs(List<CourseDTO> courseDTOs) {
        if ( courseDTOs == null ) {
            return null;
        }

        List<Course> list = new ArrayList<Course>( courseDTOs.size() );
        for ( CourseDTO courseDTO : courseDTOs ) {
            list.add( toCourseFromDTO( courseDTO ) );
        }

        return list;
    }

    @Override
    public List<CourseResponse> toCourseResponses(List<Course> courses) {
        if ( courses == null ) {
            return null;
        }

        List<CourseResponse> list = new ArrayList<CourseResponse>( courses.size() );
        for ( Course course : courses ) {
            list.add( toCourseResponse( course ) );
        }

        return list;
    }

    @Override
    public List<CourseResponse> toResponseFromDTOs(List<CourseDTO> courseDTOs) {
        if ( courseDTOs == null ) {
            return null;
        }

        List<CourseResponse> list = new ArrayList<CourseResponse>( courseDTOs.size() );
        for ( CourseDTO courseDTO : courseDTOs ) {
            list.add( toResponseFromDTO( courseDTO ) );
        }

        return list;
    }

    @Override
    public void updateCourse(CourseUpdate courseUpdate, Course course) {
        if ( courseUpdate == null ) {
            return;
        }

        if ( courseUpdate.getName() != null ) {
            course.setName( courseUpdate.getName() );
        }
        if ( courseUpdate.getDescription() != null ) {
            course.setDescription( courseUpdate.getDescription() );
        }
    }
}
