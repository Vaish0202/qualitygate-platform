//package com.qualitygate.backend.controller;
//
//import com.fasterxml.jackson.databind.ObjectMapper;
//import com.qualitygate.backend.dto.ApplicationRequest;
//import com.qualitygate.backend.dto.ApplicationResponse;
//import com.qualitygate.backend.exception.ResourceNotFoundException;
//import com.qualitygate.backend.service.ApplicationService;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
//import org.springframework.http.MediaType;
//import org.springframework.test.web.servlet.MockMvc;
//
//import java.time.LocalDateTime;
//import java.util.List;
//
//import static org.mockito.ArgumentMatchers.any;
//import static org.mockito.ArgumentMatchers.eq;
//import static org.mockito.Mockito.when;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
//
//import org.springframework.test.context.bean.override.mockito.MockitoBean;
//@WebMvcTest(ApplicationController.class)
//class ApplicationControllerTest {
//
//    @Autowired
//    private MockMvc mockMvc;
//
//    @Autowired
//    private ObjectMapper objectMapper;
//
//    @MockBean
//    private ApplicationService applicationService;
//
//    private ApplicationResponse sampleResponse() {
//        return new ApplicationResponse(
//                1L,
//                "QualityGate Backend",
//                "Spring Boot service",
//                "https://github.com/you/qualitygate-platform",
//                LocalDateTime.now(),
//                LocalDateTime.now()
//        );
//    }
//
//    @Test
//    void create_returns201_withValidRequest() throws Exception {
//        ApplicationRequest request = new ApplicationRequest();
//        request.setName("QualityGate Backend");
//        request.setRepositoryUrl("https://github.com/you/qualitygate-platform");
//
//        when(applicationService.create(any(ApplicationRequest.class))).thenReturn(sampleResponse());
//
//        mockMvc.perform(post("/api/applications")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(objectMapper.writeValueAsString(request)))
//                .andExpect(status().isCreated())
//                .andExpect(jsonPath("$.name").value("QualityGate Backend"));
//    }
//
//    @Test
//    void create_returns400_whenNameMissing() throws Exception {
//        ApplicationRequest request = new ApplicationRequest();
//        request.setRepositoryUrl("https://github.com/you/qualitygate-platform");
//        // name intentionally left blank
//
//        mockMvc.perform(post("/api/applications")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(objectMapper.writeValueAsString(request)))
//                .andExpect(status().isBadRequest())
//                .andExpect(jsonPath("$.details[0]").value(org.hamcrest.Matchers.containsString("name")));
//    }
//
//    @Test
//    void findAll_returns200_withList() throws Exception {
//        when(applicationService.findAll()).thenReturn(List.of(sampleResponse()));
//
//        mockMvc.perform(get("/api/applications"))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$[0].name").value("QualityGate Backend"));
//    }
//
//    @Test
//    void findById_returns404_whenMissing() throws Exception {
//        when(applicationService.findById(eq(99L)))
//                .thenThrow(new ResourceNotFoundException("Application not found with id: 99"));
//
//        mockMvc.perform(get("/api/applications/99"))
//                .andExpect(status().isNotFound())
//                .andExpect(jsonPath("$.message").value("Application not found with id: 99"));
//    }
//
//    @Test
//    void delete_returns204() throws Exception {
//        mockMvc.perform(delete("/api/applications/1"))
//                .andExpect(status().isNoContent());
//    }
//}