package com.example.banner.dto.request;

import com.example.banner.enums.BannerPosition;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BannerRequest {
    private String title;
    private BannerPosition position;
}
