package com.qualitygate.backend.dto;

import com.qualitygate.backend.entity.EnvironmentType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ReleaseRequest {

    @NotNull(message = "applicationId is required")
    private Long applicationId;

    @NotBlank(message = "version is required")
    private String version;

    @NotBlank(message = "gitCommit is required")
    private String gitCommit;

    private Integer jenkinsBuildNumber;

    @NotNull(message = "environmentName is required")
    private EnvironmentType environmentName;
}