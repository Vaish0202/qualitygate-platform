
package com.qualitygate.backend.dto;

import com.qualitygate.backend.entity.TestType;
import com.qualitygate.backend.entity.ValidationStatus;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ValidationResultRequest {

    @NotNull(message = "testType is required")
    private TestType testType;

    @NotNull(message = "status is required")
    private ValidationStatus status;

    @Size(max = 2000, message = "details must not exceed 2000 characters")
    private String details;
}