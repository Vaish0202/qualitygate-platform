package com.qualitygate.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.qualitygate.backend.entity.Application;



@Repository
public interface ApplicationRepository extends JpaRepository<Application, Long> {
}
