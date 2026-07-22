package com.qualitygate.backend.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
public class ApplicationResponse {

    private Long id;
    private String name;
    private String description;
    private String repositoryUrl;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}