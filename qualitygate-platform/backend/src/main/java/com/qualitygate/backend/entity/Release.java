package com.qualitygate.backend.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "releases")
@Getter
@Setter
@NoArgsConstructor
public class Release {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "application_id", nullable = false)
    private Application application;

    @ManyToOne
    @JoinColumn(name = "environment_id", nullable = false)
    private Environment environment;

    @Column(nullable = false)
    private String version;

    @Column(name = "git_commit", nullable = false)
    private String gitCommit;

    @Column(name = "jenkins_build_number")
    private Integer jenkinsBuildNumber;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 30)
    private ReleaseStatus status;

    @Column(name = "approved_by")
    private String approvedBy;

    @Column(name = "deployed_at")
    private LocalDateTime deployedAt;

    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
        if (this.status == null) {
            this.status = ReleaseStatus.PENDING;
        }
    }
}
