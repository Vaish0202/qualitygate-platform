package com.qualitygate.backend.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class EnvironmentResponse {
    private Long id;
    private Long applicationId;
    private String name;
    private String url;
    private String description;
    private LocalDateTime createdAt;
}