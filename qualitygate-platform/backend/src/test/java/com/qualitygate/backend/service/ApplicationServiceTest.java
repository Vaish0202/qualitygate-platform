package com.qualitygate.backend.service;

import com.qualitygate.backend.dto.ApplicationRequest;
import com.qualitygate.backend.dto.ApplicationResponse;
import com.qualitygate.backend.entity.Application;
import com.qualitygate.backend.exception.ResourceNotFoundException;
import com.qualitygate.backend.repository.ApplicationRepository;
import com.qualitygate.backend.service.ApplicationService;
import com.qualitygate.backend.service.EnvironmentService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ApplicationServiceTest {

    @Mock
    private ApplicationRepository applicationRepository;

    @Mock
    private EnvironmentService environmentService;

    @InjectMocks
    private ApplicationService applicationService;

    private Application sampleApplication;

    @BeforeEach
    void setUp() {
        sampleApplication = new Application();
        sampleApplication.setId(1L);
        sampleApplication.setName("QualityGate Backend");
        sampleApplication.setDescription("Spring Boot service");
        sampleApplication.setRepositoryUrl("https://github.com/you/qualitygate-platform");
        sampleApplication.setCreatedAt(LocalDateTime.now());
        sampleApplication.setUpdatedAt(LocalDateTime.now());
    }

    @Test
    void create_savesApplicationAndProvisionsEnvironments() {
        ApplicationRequest request = new ApplicationRequest();
        request.setName("QualityGate Backend");
        request.setDescription("Spring Boot service");
        request.setRepositoryUrl("https://github.com/you/qualitygate-platform");

        when(applicationRepository.save(any(Application.class))).thenReturn(sampleApplication);

        ApplicationResponse response = applicationService.create(request);

        assertThat(response.getId()).isEqualTo(1L);
        assertThat(response.getName()).isEqualTo("QualityGate Backend");
        verify(environmentService, times(1)).provisionDefaultEnvironments(sampleApplication);
    }

    @Test
    void findById_returnsApplication_whenExists() {
        when(applicationRepository.findById(1L)).thenReturn(Optional.of(sampleApplication));

        ApplicationResponse response = applicationService.findById(1L);

        assertThat(response.getName()).isEqualTo("QualityGate Backend");
    }

    @Test
    void findById_throwsResourceNotFoundException_whenMissing() {
        when(applicationRepository.findById(99L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> applicationService.findById(99L))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessageContaining("99");
    }

    @Test
    void findAll_returnsMappedList() {
        when(applicationRepository.findAll()).thenReturn(List.of(sampleApplication));

        List<ApplicationResponse> responses = applicationService.findAll();

        assertThat(responses).hasSize(1);
        assertThat(responses.get(0).getName()).isEqualTo("QualityGate Backend");
    }

    @Test
    void delete_removesApplication_whenExists() {
        when(applicationRepository.findById(1L)).thenReturn(Optional.of(sampleApplication));

        applicationService.delete(1L);

        verify(applicationRepository, times(1)).delete(sampleApplication);
    }

    @Test
    void delete_throwsResourceNotFoundException_whenMissing() {
        when(applicationRepository.findById(99L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> applicationService.delete(99L))
                .isInstanceOf(ResourceNotFoundException.class);

        verify(applicationRepository, never()).delete(any());
    }
}