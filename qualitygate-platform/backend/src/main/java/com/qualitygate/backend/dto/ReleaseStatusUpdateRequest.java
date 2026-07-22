
package com.qualitygate.backend.dto;

import com.qualitygate.backend.entity.ReleaseStatus;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ReleaseStatusUpdateRequest {

    @NotNull(message = "status is required")
    private ReleaseStatus status;

    private String approvedBy;
}