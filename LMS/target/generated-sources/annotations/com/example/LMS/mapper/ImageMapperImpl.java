package com.example.LMS.mapper;

import com.example.LMS.dto.Request.ImageRequest;
import com.example.LMS.dto.Response.ImageResponse;
import com.example.LMS.entity.Image;
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
public class ImageMapperImpl implements ImageMapper {

    @Override
    public Image toImage(ImageRequest imageRequest) {
        if ( imageRequest == null ) {
            return null;
        }

        Image.ImageBuilder image = Image.builder();

        image.objectType( imageRequest.getObjectType() );
        image.objectId( imageRequest.getObjectId() );
        image.url( imageRequest.getUrl() );
        image.type( imageRequest.getType() );

        return image.build();
    }

    @Override
    public ImageResponse toImageResponse(Image image) {
        if ( image == null ) {
            return null;
        }

        ImageResponse imageResponse = new ImageResponse();

        imageResponse.setId( image.getId() );
        imageResponse.setObjectType( image.getObjectType() );
        imageResponse.setObjectId( image.getObjectId() );
        imageResponse.setUrl( image.getUrl() );
        imageResponse.setType( image.getType() );
        imageResponse.setPrimary( image.isPrimary() );
        imageResponse.setFileName( image.getFileName() );

        return imageResponse;
    }

    @Override
    public List<Image> toImages(List<ImageRequest> imageRequests) {
        if ( imageRequests == null ) {
            return null;
        }

        List<Image> list = new ArrayList<Image>( imageRequests.size() );
        for ( ImageRequest imageRequest : imageRequests ) {
            list.add( toImage( imageRequest ) );
        }

        return list;
    }

    @Override
    public List<ImageResponse> toImageResponses(List<Image> images) {
        if ( images == null ) {
            return null;
        }

        List<ImageResponse> list = new ArrayList<ImageResponse>( images.size() );
        for ( Image image : images ) {
            list.add( toImageResponse( image ) );
        }

        return list;
    }
}
