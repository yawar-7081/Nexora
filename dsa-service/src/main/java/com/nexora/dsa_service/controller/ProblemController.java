package com.nexora.dsa_service.controller;

import com.nexora.dsa_service.dto.request.ProblemDashboardRequestDto;
import com.nexora.dsa_service.dto.request.ProblemRequestDto;
import com.nexora.dsa_service.dto.response.ProblemDashboardResponseDto;
import com.nexora.dsa_service.dto.response.ProblemResponseDto;
import com.nexora.dsa_service.service.IProblemService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/problem")
@RequiredArgsConstructor
public class ProblemController {

    private final IProblemService problemService;

    @PostMapping
    public ResponseEntity<ProblemResponseDto> createProblem(@RequestBody(required = true) ProblemRequestDto problemRequestDto){
        return ResponseEntity.ok(problemService.createProblem(problemRequestDto));
    }

    @GetMapping("/{problemId}")
    public ResponseEntity<ProblemResponseDto> getProblemById(@PathVariable String problemId){
        return ResponseEntity.ok(problemService.getProblemById(problemId));
    }

    @DeleteMapping("/{problemId}")
    public ResponseEntity<Void> deleteProblemById(@PathVariable String problemId){
        return ResponseEntity.ok(problemService.deleteProblemById(problemId));
    }

    @PostMapping("/dashboard")
    public ResponseEntity<Page<ProblemDashboardResponseDto>> getAllProblems(@RequestBody ProblemDashboardRequestDto problemDashboardRequestDto){
        return ResponseEntity.ok(problemService.getAllProblems(problemDashboardRequestDto));
    }

}
