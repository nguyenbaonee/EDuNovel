package com.example.LMS.mapper;

import com.example.LMS.dto.Request.LessonRequest;
import com.example.LMS.dto.Response.LessonResponse;
import com.example.LMS.dto.dtoProjection.LessonDTO;
import com.example.LMS.entity.Image;
import com.example.LMS.entity.Lesson;
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
public class LessonMapperImpl implements LessonMapper {

    @Override
    public Lesson toLesson(LessonRequest lessonRequest) {
        if ( lessonRequest == null ) {
            return null;
        }

        Lesson.LessonBuilder lesson = Lesson.builder();

        lesson.title( lessonRequest.getTitle() );
        lesson.lessonOrder( lessonRequest.getLessonOrder() );

        return lesson.build();
    }

    @Override
    public LessonResponse toLessonResponse(Lesson lesson) {
        if ( lesson == null ) {
            return null;
        }

        LessonResponse lessonResponse = new LessonResponse();

        lessonResponse.setTitle( lesson.getTitle() );
        List<Image> list = lesson.getVideoUrl();
        if ( list != null ) {
            lessonResponse.setVideoUrl( new ArrayList<Image>( list ) );
        }
        List<Image> list1 = lesson.getThumbnail();
        if ( list1 != null ) {
            lessonResponse.setThumbnail( new ArrayList<Image>( list1 ) );
        }
        lessonResponse.setLessonOrder( lesson.getLessonOrder() );

        return lessonResponse;
    }

    @Override
    public LessonResponse toLessonResponseFrom(LessonDTO lessonDTO) {
        if ( lessonDTO == null ) {
            return null;
        }

        LessonResponse lessonResponse = new LessonResponse();

        lessonResponse.setTitle( lessonDTO.getTitle() );
        lessonResponse.setLessonOrder( lessonDTO.getLessonOrder() );

        return lessonResponse;
    }

    @Override
    public List<Lesson> toLessons(List<LessonRequest> lessonRequests) {
        if ( lessonRequests == null ) {
            return null;
        }

        List<Lesson> list = new ArrayList<Lesson>( lessonRequests.size() );
        for ( LessonRequest lessonRequest : lessonRequests ) {
            list.add( toLesson( lessonRequest ) );
        }

        return list;
    }

    @Override
    public List<LessonResponse> toLessonResponses(List<Lesson> lessons) {
        if ( lessons == null ) {
            return null;
        }

        List<LessonResponse> list = new ArrayList<LessonResponse>( lessons.size() );
        for ( Lesson lesson : lessons ) {
            list.add( toLessonResponse( lesson ) );
        }

        return list;
    }

    @Override
    public List<LessonDTO> toLessonResponseFroms(List<LessonDTO> lessonDTOs) {
        if ( lessonDTOs == null ) {
            return null;
        }

        List<LessonDTO> list = new ArrayList<LessonDTO>( lessonDTOs.size() );
        for ( LessonDTO lessonDTO : lessonDTOs ) {
            list.add( lessonDTO );
        }

        return list;
    }

    @Override
    public void updateLesson(LessonRequest lessonRequest, Lesson lesson) {
        if ( lessonRequest == null ) {
            return;
        }

        if ( lessonRequest.getTitle() != null ) {
            lesson.setTitle( lessonRequest.getTitle() );
        }
        if ( lessonRequest.getLessonOrder() != null ) {
            lesson.setLessonOrder( lessonRequest.getLessonOrder() );
        }
    }
}
