package com.example.banner.repo;

import com.example.banner.entity.Banner;
import com.example.banner.enums.BannerPosition;
import com.example.banner.enums.Status;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface BannerRepo extends JpaRepository<Banner, UUID> {
    long countByPositionAndStatus(BannerPosition position, Status status);

    List<Banner> findByPositionAndStatusOrderByCreatedAtDesc(BannerPosition position, Status status);

    List<Banner> findByPositionOrderByCreatedAtDesc(BannerPosition position);

    List<Banner> findByStatusOrderByCreatedAtDesc(Status status);

    Optional<Banner> findByIdAndStatus(UUID id, Status status);

    boolean existsByTitle(String title);
}
