package com.example.banner.mapper;

import com.example.banner.dto.request.BannerRequest;
import com.example.banner.dto.response.BannerResponse;
import com.example.banner.entity.Banner;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

import java.util.List;

@Mapper(componentModel = "spring")
public interface BannerMapper {
    Banner toBanner(BannerRequest request);
    BannerResponse toBannerResponse(Banner banner);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateBanner(@MappingTarget Banner banner, BannerRequest request);

    List<Banner> toBannerList(List<BannerRequest> requests);
    List<BannerResponse> toBannerResponseList(List<Banner> banners);
}
