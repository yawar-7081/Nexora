package com.nexora.dsa_service.service;

import com.nexora.dsa_service.dto.request.ProblemRequestDto;
import com.nexora.dsa_service.dto.response.ProblemResponseDto;

public interface IProblemService {
    ProblemResponseDto createProblem(ProblemRequestDto problemRequestDto);

    ProblemResponseDto getProblemById(String problemId);
}
