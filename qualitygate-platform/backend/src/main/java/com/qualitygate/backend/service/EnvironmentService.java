package com.qualitygate.backend.service;

import com.qualitygate.backend.dto.EnvironmentResponse;
import com.qualitygate.backend.entity.Application;
import com.qualitygate.backend.entity.Environment;
import com.qualitygate.backend.entity.EnvironmentType;
import com.qualitygate.backend.exception.ResourceNotFoundException;
import com.qualitygate.backend.repository.EnvironmentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class EnvironmentService {

    private final EnvironmentRepository environmentRepository;

    public void provisionDefaultEnvironments(Application application) {
        for (EnvironmentType type : EnvironmentType.values()) {
            Environment environment = new Environment();
            environment.setApplication(application);
            environment.setName(type);
            environmentRepository.save(environment);
        }
    }

    public List<EnvironmentResponse> findByApplication(Long applicationId) {
        return environmentRepository.findByApplicationId(applicationId)
                .stream()
                .map(this::toResponse)
                .toList();
    }

    public Environment getOrThrow(Long applicationId, EnvironmentType type) {
        return environmentRepository.findByApplicationIdAndName(applicationId, type)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Environment " + type + " not provisioned for application id: " + applicationId));
    }

    private EnvironmentResponse toResponse(Environment environment) {
        return new EnvironmentResponse(
                environment.getId(),
                environment.getApplication().getId(),
                environment.getName().name(),
                environment.getUrl(),
                environment.getDescription(),
                environment.getCreatedAt()
        );
    }
}
