package com.example.banner.dto.response;

import com.example.banner.enums.BannerPosition;
import com.example.banner.enums.Status;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BannerResponse {
     UUID id;
     String title;
     String imageUrl;
     Status status;
     BannerPosition position;
     Instant createdAt;
     Instant updatedAt;
}
