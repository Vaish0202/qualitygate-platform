package com.qualitygate.backend.service;

import com.qualitygate.backend.dto.ApplicationRequest;
import com.qualitygate.backend.dto.ApplicationResponse;
import com.qualitygate.backend.entity.Application;
import com.qualitygate.backend.exception.ResourceNotFoundException;
import com.qualitygate.backend.repository.ApplicationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ApplicationService {

    private final ApplicationRepository applicationRepository;
    private final EnvironmentService environmentService;

    public ApplicationResponse create(ApplicationRequest request) {
        Application application = new Application();
        application.setName(request.getName());
        application.setDescription(request.getDescription());
        application.setRepositoryUrl(request.getRepositoryUrl());

        Application saved = applicationRepository.save(application);
        environmentService.provisionDefaultEnvironments(saved);
        return toResponse(saved);
    }

    public List<ApplicationResponse> findAll() {
        return applicationRepository.findAll()
                .stream()
                .map(this::toResponse)
                .toList();
    }

    public ApplicationResponse findById(Long id) {
        Application application = getOrThrow(id);
        return toResponse(application);
    }

    public ApplicationResponse update(Long id, ApplicationRequest request) {
        Application application = getOrThrow(id);
        application.setName(request.getName());
        application.setDescription(request.getDescription());
        application.setRepositoryUrl(request.getRepositoryUrl());

        Application updated = applicationRepository.save(application);
        return toResponse(updated);
    }

    public void delete(Long id) {
        Application application = getOrThrow(id);
        applicationRepository.delete(application);
    }

    private Application getOrThrow(Long id) {
        return applicationRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Application not found with id: " + id));
    }

    private ApplicationResponse toResponse(Application application) {
        return new ApplicationResponse(
                application.getId(),
                application.getName(),
                application.getDescription(),
                application.getRepositoryUrl(),
                application.getCreatedAt(),
                application.getUpdatedAt()
        );
    }
}