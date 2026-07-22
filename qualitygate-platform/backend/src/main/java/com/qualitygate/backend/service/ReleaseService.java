package com.qualitygate.backend.service;

import com.qualitygate.backend.dto.ReleaseRequest;
import com.qualitygate.backend.dto.ReleaseResponse;
import com.qualitygate.backend.dto.ReleaseStatusUpdateRequest;
import com.qualitygate.backend.entity.Application;
import com.qualitygate.backend.entity.Environment;
import com.qualitygate.backend.entity.Release;
import com.qualitygate.backend.entity.ReleaseStatus;
import com.qualitygate.backend.exception.ResourceNotFoundException;
import com.qualitygate.backend.repository.ApplicationRepository;
import com.qualitygate.backend.repository.ReleaseRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ReleaseService {

    private final ReleaseRepository releaseRepository;
    private final ApplicationRepository applicationRepository;
    private final EnvironmentService environmentService;

    public ReleaseResponse create(ReleaseRequest request) {
        Application application = applicationRepository.findById(request.getApplicationId())
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Application not found with id: " + request.getApplicationId()));

        Environment environment = environmentService.getOrThrow(
                request.getApplicationId(), request.getEnvironmentName());

        Release release = new Release();
        release.setApplication(application);
        release.setEnvironment(environment);
        release.setVersion(request.getVersion());
        release.setGitCommit(request.getGitCommit());
        release.setJenkinsBuildNumber(request.getJenkinsBuildNumber());
        release.setStatus(ReleaseStatus.PENDING);

        Release saved = releaseRepository.save(release);
        return toResponse(saved);
    }

    public List<ReleaseResponse> findAll() {
        return releaseRepository.findAll()
                .stream()
                .map(this::toResponse)
                .toList();
    }

    public ReleaseResponse findById(Long id) {
        return toResponse(getOrThrow(id));
    }

    public ReleaseResponse updateStatus(Long id, ReleaseStatusUpdateRequest request) {
        Release release = getOrThrow(id);

        if (request.getStatus() == ReleaseStatus.APPROVED
                && (request.getApprovedBy() == null || request.getApprovedBy().isBlank())) {
            throw new IllegalArgumentException("approvedBy is required when approving a release");
        }

        release.setStatus(request.getStatus());

        if (request.getApprovedBy() != null && !request.getApprovedBy().isBlank()) {
            release.setApprovedBy(request.getApprovedBy());
        }

        if (request.getStatus() == ReleaseStatus.DEPLOYED) {
            release.setDeployedAt(LocalDateTime.now());
        }

        Release updated = releaseRepository.save(release);
        return toResponse(updated);
    }

    private Release getOrThrow(Long id) {
        return releaseRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Release not found with id: " + id));
    }

    private ReleaseResponse toResponse(Release release) {
        return new ReleaseResponse(
                release.getId(),
                release.getApplication().getId(),
                release.getApplication().getName(),
                release.getVersion(),
                release.getGitCommit(),
                release.getJenkinsBuildNumber(),
                release.getEnvironment().getName().name(),
                release.getStatus(),
                release.getApprovedBy(),
                release.getDeployedAt(),
                release.getCreatedAt()
        );
    }
}