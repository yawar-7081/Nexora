package com.nexora.dsa_service.service;

import com.nexora.dsa_service.dto.request.ProblemDashboardRequestDto;
import com.nexora.dsa_service.dto.request.ProblemRequestDto;
import com.nexora.dsa_service.dto.response.ProblemDashboardResponseDto;
import com.nexora.dsa_service.dto.response.ProblemResponseDto;
import org.springframework.data.domain.Page;

public interface IProblemService {
    ProblemResponseDto createProblem(ProblemRequestDto problemRequestDto);

    ProblemResponseDto getProblemById(String problemId);

    Void deleteProblemById(String problemId);

    Page<ProblemDashboardResponseDto> getAllProblems(ProblemDashboardRequestDto problemDashboardRequestDto);
}
