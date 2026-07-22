package com.qualitygate.backend.repository;

import com.qualitygate.backend.entity.ValidationResult;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ValidationResultRepository extends JpaRepository<ValidationResult, Long> {
    List<ValidationResult> findByReleaseId(Long releaseId);
}
