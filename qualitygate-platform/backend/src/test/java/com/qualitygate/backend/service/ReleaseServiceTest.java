package com.qualitygate.backend.service;

import com.qualitygate.backend.dto.ReleaseStatusUpdateRequest;
import com.qualitygate.backend.entity.Release;
import com.qualitygate.backend.entity.ReleaseStatus;
import com.qualitygate.backend.exception.ResourceNotFoundException;
import com.qualitygate.backend.repository.ApplicationRepository;
import com.qualitygate.backend.repository.ReleaseRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ReleaseServiceTest {

    @Mock
    private ReleaseRepository releaseRepository;

    @Mock
    private ApplicationRepository applicationRepository;

    @Mock
    private EnvironmentService environmentService;

    @InjectMocks
    private ReleaseService releaseService;

    private Release sampleRelease;

    @BeforeEach
    void setUp() {
        sampleRelease = new Release();
        sampleRelease.setId(1L);
        sampleRelease.setVersion("1.0.0-manual");
        sampleRelease.setGitCommit("a71bc20");
        sampleRelease.setStatus(ReleaseStatus.PENDING);

        com.qualitygate.backend.entity.Application app = new com.qualitygate.backend.entity.Application();
        app.setId(1L);
        app.setName("QualityGate Backend");
        sampleRelease.setApplication(app);

        com.qualitygate.backend.entity.Environment env = new com.qualitygate.backend.entity.Environment();
        env.setId(1L);
        env.setName(com.qualitygate.backend.entity.EnvironmentType.STAGING);
        sampleRelease.setEnvironment(env);
    }

    @Test
    void updateStatus_toApproved_withoutApprovedBy_throwsException() {
        when(releaseRepository.findById(1L)).thenReturn(Optional.of(sampleRelease));

        ReleaseStatusUpdateRequest request = new ReleaseStatusUpdateRequest();
        request.setStatus(ReleaseStatus.APPROVED);

        assertThatThrownBy(() -> releaseService.updateStatus(1L, request))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("approvedBy is required");
    }

    @Test
    void updateStatus_toApproved_withApprovedBy_succeeds() {
        when(releaseRepository.findById(1L)).thenReturn(Optional.of(sampleRelease));
        when(releaseRepository.save(any(Release.class))).thenAnswer(inv -> inv.getArgument(0));

        ReleaseStatusUpdateRequest request = new ReleaseStatusUpdateRequest();
        request.setStatus(ReleaseStatus.APPROVED);
        request.setApprovedBy("qa-user");

        var response = releaseService.updateStatus(1L, request);

        assertThat(response.getStatus()).isEqualTo(ReleaseStatus.APPROVED);
        assertThat(response.getApprovedBy()).isEqualTo("qa-user");
    }

    @Test
    void updateStatus_toDeployed_stampsDeployedAt() {
        when(releaseRepository.findById(1L)).thenReturn(Optional.of(sampleRelease));
        when(releaseRepository.save(any(Release.class))).thenAnswer(inv -> inv.getArgument(0));

        ReleaseStatusUpdateRequest request = new ReleaseStatusUpdateRequest();
        request.setStatus(ReleaseStatus.DEPLOYED);

        var response = releaseService.updateStatus(1L, request);

        assertThat(response.getStatus()).isEqualTo(ReleaseStatus.DEPLOYED);
        assertThat(response.getDeployedAt()).isNotNull();
    }

    @Test
    void updateStatus_missingRelease_throwsResourceNotFoundException() {
        when(releaseRepository.findById(99L)).thenReturn(Optional.empty());

        ReleaseStatusUpdateRequest request = new ReleaseStatusUpdateRequest();
        request.setStatus(ReleaseStatus.DEPLOYED);

        assertThatThrownBy(() -> releaseService.updateStatus(99L, request))
                .isInstanceOf(ResourceNotFoundException.class);
    }
}