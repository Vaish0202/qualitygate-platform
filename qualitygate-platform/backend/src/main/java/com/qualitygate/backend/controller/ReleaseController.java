package com.qualitygate.backend.controller;

import com.qualitygate.backend.dto.*;
import com.qualitygate.backend.service.ReleaseService;
import com.qualitygate.backend.service.ValidationResultService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/releases")
@RequiredArgsConstructor
public class ReleaseController {

    private final ReleaseService releaseService;
    private final ValidationResultService validationResultService;

    @PostMapping
    public ResponseEntity<ReleaseResponse> create(@Valid @RequestBody ReleaseRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(releaseService.create(request));
    }

    @GetMapping
    public ResponseEntity<List<ReleaseResponse>> findAll() {
        return ResponseEntity.ok(releaseService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ReleaseResponse> findById(@PathVariable Long id) {
        return ResponseEntity.ok(releaseService.findById(id));
    }

    @PatchMapping("/{id}/status")
    public ResponseEntity<ReleaseResponse> updateStatus(
            @PathVariable Long id,
            @Valid @RequestBody ReleaseStatusUpdateRequest request) {
        return ResponseEntity.ok(releaseService.updateStatus(id, request));
    }

    @PostMapping("/{id}/validation-results")
    public ResponseEntity<ValidationResultResponse> addValidationResult(
            @PathVariable Long id,
            @Valid @RequestBody ValidationResultRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(validationResultService.create(id, request));
    }

    @GetMapping("/{id}/validation-results")
    public ResponseEntity<List<ValidationResultResponse>> getValidationResults(@PathVariable Long id) {
        return ResponseEntity.ok(validationResultService.findByRelease(id));
    }
}
