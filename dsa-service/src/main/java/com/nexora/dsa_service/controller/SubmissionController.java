package com.nexora.dsa_service.controller;

import com.nexora.dsa_service.dto.request.SubmissionRequestDto;
import com.nexora.dsa_service.dto.response.SubmissionResponseDto;
import com.nexora.dsa_service.service.ISubmissionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/submission")
@RequiredArgsConstructor
public class SubmissionController {
    private final ISubmissionService submissionService;

    @PostMapping("/submit-code")
    public ResponseEntity<SubmissionResponseDto> submitCode(@RequestBody SubmissionRequestDto submissionRequestDto){
        return ResponseEntity.ok(submissionService.submitCode(submissionRequestDto));
    }
}
