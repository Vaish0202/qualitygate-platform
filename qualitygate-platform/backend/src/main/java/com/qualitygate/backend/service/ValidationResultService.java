package com.qualitygate.backend.service;

import com.qualitygate.backend.dto.ValidationResultRequest;
import com.qualitygate.backend.dto.ValidationResultResponse;
import com.qualitygate.backend.entity.Release;
import com.qualitygate.backend.entity.ValidationResult;
import com.qualitygate.backend.exception.ResourceNotFoundException;
import com.qualitygate.backend.repository.ReleaseRepository;
import com.qualitygate.backend.repository.ValidationResultRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ValidationResultService {

    private final ValidationResultRepository validationResultRepository;
    private final ReleaseRepository releaseRepository;

    public ValidationResultResponse create(Long releaseId, ValidationResultRequest request) {
        Release release = releaseRepository.findById(releaseId)
                .orElseThrow(() -> new ResourceNotFoundException("Release not found with id: " + releaseId));

        ValidationResult result = new ValidationResult();
        result.setRelease(release);
        result.setTestType(request.getTestType());
        result.setStatus(request.getStatus());
        result.setDetails(request.getDetails());

        ValidationResult saved = validationResultRepository.save(result);
        return toResponse(saved);
    }

    public List<ValidationResultResponse> findByRelease(Long releaseId) {
        return validationResultRepository.findByReleaseId(releaseId)
                .stream()
                .map(this::toResponse)
                .toList();
    }

    private ValidationResultResponse toResponse(ValidationResult result) {
        return new ValidationResultResponse(
                result.getId(),
                result.getRelease().getId(),
                result.getTestType(),
                result.getStatus(),
                result.getDetails(),
                result.getExecutedAt()
        );
    }
}
