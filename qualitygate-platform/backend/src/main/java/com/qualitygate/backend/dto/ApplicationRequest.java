package com.qualitygate.backend.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ApplicationRequest {

    @NotBlank(message = "Application name is required")
    @Size(max = 100, message = "Application name must not exceed 100 characters")
    private String name;

    @Size(max = 500, message = "Description must not exceed 500 characters")
    private String description;

    @NotBlank(message = "Repository URL is required")
    private String repositoryUrl;
}