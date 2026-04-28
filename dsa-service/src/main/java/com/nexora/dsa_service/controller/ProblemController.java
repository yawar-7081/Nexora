package com.nexora.dsa_service.controller;

import com.nexora.dsa_service.dto.request.ProblemRequestDto;
import com.nexora.dsa_service.dto.response.ProblemResponseDto;
import com.nexora.dsa_service.service.IProblemService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/problem")
@RequiredArgsConstructor
public class ProblemController {

    private final IProblemService problemService;

    @PostMapping("/create-problem")
    public ResponseEntity<ProblemResponseDto> createProblem(@RequestBody(required = true) ProblemRequestDto problemRequestDto){
        return ResponseEntity.ok(problemService.createProblem(problemRequestDto));
    }

    @GetMapping("/{problemId}/get-problem")
    public ResponseEntity<ProblemResponseDto> getProblemById(@PathVariable String problemId){
        return ResponseEntity.ok(problemService.getProblemById(problemId));
    }



}
