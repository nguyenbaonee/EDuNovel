package com.example.banner.service;

import com.example.banner.dto.request.BannerRequest;
import com.example.banner.dto.response.BannerResponse;
import com.example.banner.entity.Banner;
import com.example.banner.enums.BannerPosition;
import com.example.banner.enums.Status;
import com.example.banner.mapper.BannerMapper;
import com.example.banner.repo.BannerRepo;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

@Service
public class BannerService {
    BannerRepo bannerRepo;
    BannerMapper bannerMapper;
    FileStorageService fileStorageService;

    public BannerService(BannerRepo bannerRepo, BannerMapper bannerMapper, FileStorageService fileStorageService) {
        this.bannerRepo = bannerRepo;
        this.bannerMapper = bannerMapper;
        this.fileStorageService = fileStorageService;
    }

    @Transactional
    public BannerResponse createBanner(BannerRequest request, MultipartFile image) throws IOException {
        String imageUrl = fileStorageService.save(image);
        if(bannerRepo.existsByTitle(request.getTitle())){
            throw new RuntimeException("Tiêu đề banner đã tồn tại");
        }
        Banner banner = bannerMapper.toBanner(request);
        banner.setImageUrl(imageUrl);
        return bannerMapper.toBannerResponse(bannerRepo.save(banner));
    }

    @Transactional
    public BannerResponse updateBanner(UUID id, BannerRequest request, MultipartFile image) throws IOException {
        Banner banner = bannerRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Banner không tồn tại"));

        if (image != null && !image.isEmpty()) {
            fileStorageService.deleteFiles(banner.getImageUrl());
            String imageUrl = fileStorageService.save(image);
            banner.setImageUrl(imageUrl);
        }

        bannerMapper.updateBanner(banner, request);
        Banner updated = bannerRepo.save(banner);
        return bannerMapper.toBannerResponse(updated);
    }
    public BannerResponse changeStatus(UUID id, Status status) {
        Banner banner = bannerRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Banner không tồn tại"));
        if (status == Status.ACTIVE) {
            long countActive = bannerRepo.countByPositionAndStatus(banner.getPosition(), Status.ACTIVE);
            if (countActive >= 5 && banner.getStatus() != Status.ACTIVE) {
                throw new RuntimeException("Đã đạt số lượng banner tối đa cho vị trí này");
            }
        }

        banner.setStatus(status);
        Banner updated = bannerRepo.save(banner);
        return bannerMapper.toBannerResponse(updated);
    }

    @Transactional
    public void deleteBanner(UUID id) {
        Banner banner = bannerRepo.findByIdAndStatus(id,Status.ACTIVE)
                .orElseThrow(() -> new RuntimeException("Banner không tồn tại"));

        fileStorageService.deleteFiles(banner.getImageUrl());
        bannerRepo.delete(banner);
    }

    public List<BannerResponse> getBanners(BannerPosition position, Status status) {
        List<Banner> banners;
        if (position != null && status != null) {
            banners = bannerRepo.findByPositionAndStatusOrderByCreatedAtDesc(position, status);
        } else if (position != null) {
            banners = bannerRepo.findByPositionOrderByCreatedAtDesc(position);
        } else if (status != null) {
            banners = bannerRepo.findByStatusOrderByCreatedAtDesc(status);
        } else {
            banners = bannerRepo.findAll();
        }

        return bannerMapper.toBannerResponseList(banners);
    }

    public BannerResponse getBannerById(UUID id) {
        Banner banner = bannerRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Banner không tồn tại"));
        return bannerMapper.toBannerResponse(banner);
    }
}
