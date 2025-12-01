package com.example.banner.entity;

import com.example.banner.enums.BannerPosition;
import com.example.banner.enums.Status;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name = "banner")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Banner {
    @Id
    @GeneratedValue
    @Column(name = "id", updatable = false, nullable = false)
    private UUID id;

    @Column(name = "title", nullable = false, length = 255)
    private String title;

    @Column(name = "image_url", nullable = false, length = 255)
    private String imageUrl;

    @Column(name = "active", nullable = false)
    @Builder.Default
    @Enumerated(EnumType.STRING)
    Status status = Status.DELETED;

    @Enumerated(EnumType.STRING)
    @Column(name = "position", nullable = false, length = 10)
    private BannerPosition position;

    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    private Instant createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private Instant updatedAt;

}