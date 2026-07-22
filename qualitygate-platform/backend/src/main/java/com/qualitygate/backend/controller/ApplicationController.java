package com.qualitygate.backend.controller;

import com.qualitygate.backend.dto.ApplicationRequest;
import com.qualitygate.backend.dto.ApplicationResponse;
import com.qualitygate.backend.service.ApplicationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/applications")
@RequiredArgsConstructor
public class ApplicationController {

    private final ApplicationService applicationService;

    @PostMapping
    public ResponseEntity<ApplicationResponse> create(@Valid @RequestBody ApplicationRequest request) {
        ApplicationResponse response = applicationService.create(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping
    public ResponseEntity<List<ApplicationResponse>> findAll() {
        return ResponseEntity.ok(applicationService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApplicationResponse> findById(@PathVariable Long id) {
        return ResponseEntity.ok(applicationService.findById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApplicationResponse> update(
            @PathVariable Long id,
            @Valid @RequestBody ApplicationRequest request) {
        return ResponseEntity.ok(applicationService.update(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        applicationService.delete(id);
        return ResponseEntity.noContent().build();
    }
}