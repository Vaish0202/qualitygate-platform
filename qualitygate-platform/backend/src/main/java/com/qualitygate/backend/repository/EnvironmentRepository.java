package com.qualitygate.backend.repository;

import com.qualitygate.backend.entity.Environment;
import com.qualitygate.backend.entity.EnvironmentType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface EnvironmentRepository extends JpaRepository<Environment, Long> {
    List<Environment> findByApplicationId(Long applicationId);
    Optional<Environment> findByApplicationIdAndName(Long applicationId, EnvironmentType name);
}