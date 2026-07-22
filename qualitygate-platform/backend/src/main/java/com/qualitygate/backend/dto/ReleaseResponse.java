package com.qualitygate.backend.dto;

import com.qualitygate.backend.entity.ReleaseStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class ReleaseResponse {
    private Long id;
    private Long applicationId;
    private String applicationName;
    private String version;
    private String gitCommit;
    private Integer jenkinsBuildNumber;
    private String environmentName;
    private ReleaseStatus status;
    private String approvedBy;
    private LocalDateTime deployedAt;
    private LocalDateTime createdAt;
}