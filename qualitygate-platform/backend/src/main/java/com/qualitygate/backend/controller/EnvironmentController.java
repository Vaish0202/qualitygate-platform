package com.qualitygate.backend.controller;

import com.qualitygate.backend.dto.EnvironmentResponse;
import com.qualitygate.backend.service.EnvironmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/applications/{applicationId}/environments")
@RequiredArgsConstructor
public class EnvironmentController {

    private final EnvironmentService environmentService;

    @GetMapping
    public ResponseEntity<List<EnvironmentResponse>> findByApplication(@PathVariable Long applicationId) {
        return ResponseEntity.ok(environmentService.findByApplication(applicationId));
    }
}