package com.example.LMS.mapper;

import com.example.LMS.dto.Request.EnrollmentRequest;
import com.example.LMS.dto.Response.EnrollmentResponse;
import com.example.LMS.entity.Enrollment;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-11-29T22:26:05+0700",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 21.0.5 (Oracle Corporation)"
)
@Component
public class EnrollmentMapperImpl implements EnrollmentMapper {

    @Override
    public Enrollment toEnrollment(EnrollmentRequest enrollmentRequest) {
        if ( enrollmentRequest == null ) {
            return null;
        }

        Enrollment.EnrollmentBuilder enrollment = Enrollment.builder();

        enrollment.userId( enrollmentRequest.getUserId() );

        return enrollment.build();
    }

    @Override
    public EnrollmentResponse toEnrollmentResponse(Enrollment enrollment) {
        if ( enrollment == null ) {
            return null;
        }

        EnrollmentResponse enrollmentResponse = new EnrollmentResponse();

        enrollmentResponse.setId( enrollment.getId() );
        enrollmentResponse.setEnrolledAt( enrollment.getEnrolledAt() );

        return enrollmentResponse;
    }

    @Override
    public List<Enrollment> toEnrollments(List<EnrollmentRequest> enrollmentRequests) {
        if ( enrollmentRequests == null ) {
            return null;
        }

        List<Enrollment> list = new ArrayList<Enrollment>( enrollmentRequests.size() );
        for ( EnrollmentRequest enrollmentRequest : enrollmentRequests ) {
            list.add( toEnrollment( enrollmentRequest ) );
        }

        return list;
    }

    @Override
    public List<EnrollmentResponse> toEnrollmentResponses(List<Enrollment> enrollments) {
        if ( enrollments == null ) {
            return null;
        }

        List<EnrollmentResponse> list = new ArrayList<EnrollmentResponse>( enrollments.size() );
        for ( Enrollment enrollment : enrollments ) {
            list.add( toEnrollmentResponse( enrollment ) );
        }

        return list;
    }
}
