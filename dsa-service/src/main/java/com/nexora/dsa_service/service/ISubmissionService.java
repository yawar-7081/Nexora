package com.nexora.dsa_service.service;

import com.nexora.dsa_service.dto.request.SubmissionRequestDto;
import com.nexora.dsa_service.dto.response.SubmissionResponseDto;

public interface ISubmissionService {
    SubmissionResponseDto submitCode(SubmissionRequestDto submissionRequestDto);
}
