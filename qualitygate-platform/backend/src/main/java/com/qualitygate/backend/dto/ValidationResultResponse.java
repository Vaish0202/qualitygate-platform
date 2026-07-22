
package com.qualitygate.backend.dto;

import com.qualitygate.backend.entity.TestType;
import com.qualitygate.backend.entity.ValidationStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class ValidationResultResponse {
    private Long id;
    private Long releaseId;
    private TestType testType;
    private ValidationStatus status;
    private String details;
    private LocalDateTime executedAt;
}