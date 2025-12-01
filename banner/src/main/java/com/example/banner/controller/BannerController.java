package com.example.banner.controller;

import com.example.banner.dto.request.BannerRequest;
import com.example.banner.dto.response.BannerResponse;
import com.example.banner.enums.BannerPosition;
import com.example.banner.enums.Status;
import com.example.banner.service.BannerService;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/banners")
public class BannerController {
    BannerService bannerService;
    public BannerController(BannerService bannerService) {
        this.bannerService = bannerService;
    }

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public BannerResponse createBanner(@ModelAttribute BannerRequest bannerRequest,
                                       @RequestParam(value = "image") MultipartFile image) throws IOException {
        return bannerService.createBanner(bannerRequest,image);
    }

    @PostMapping("/{id}/status")
    public BannerResponse changeStatus(@PathVariable("id") UUID id,
                                       @RequestParam("status") Status status) {
        return bannerService.changeStatus(id,status);
    }

    @DeleteMapping("/{id}")
    public void deleteBanner(@PathVariable("id") UUID id) {
        bannerService.deleteBanner(id);
    }

    @GetMapping
    public List<BannerResponse> searchBanners(BannerPosition position, Status status) {
        return bannerService.getBanners(position, status);
    }

    @GetMapping("/{id}")
    public BannerResponse getBanner(@PathVariable("id") UUID id) {
        return bannerService.getBannerById(id);
    }

    @PutMapping("/{id}")
    public BannerResponse updateBanner(@PathVariable("id") UUID id,@ModelAttribute BannerRequest bannerRequest,
                                       @RequestParam(value = "image") MultipartFile image) throws IOException {
        return bannerService.updateBanner(id, bannerRequest, image);
    }
}
