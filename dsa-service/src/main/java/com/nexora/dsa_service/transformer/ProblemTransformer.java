package com.nexora.dsa_service.transformer;

import com.nexora.dsa_service.dto.response.ProblemResponseDto;
import com.nexora.dsa_service.entity.Problem;
import com.nexora.dsa_service.entity.TestCase;
import com.nexora.dsa_service.entity.Topic;

import java.util.Comparator;
import java.util.List;

public class ProblemTransformer {
    public static ProblemResponseDto entityToResponse(Problem problem) {

        ProblemResponseDto response = new ProblemResponseDto();

        response.setProblemId(problem.getId());
        response.setTitle(problem.getTitle());
        response.setDescription(problem.getDescription());
        response.setDifficulty(problem.getProblemDificulty());
        response.setConstraints(problem.getConstraints());
        response.setTimeLimit(problem.getTimeLimit());
        response.setMemoryLimit(problem.getMemoryLimit());

        // Topics → names
        List<String> topicNames = problem.getTopics()
                .stream()
                .map(Topic::getName)
                .toList();

        response.setTopicNames(topicNames);

        // Problem Languages
        List<ProblemResponseDto.ProblemLanguageResponse> languages =
                problem.getProblemLanguages()
                        .stream()
                        .map(pl -> {
                            ProblemResponseDto.ProblemLanguageResponse dto =
                                    new ProblemResponseDto.ProblemLanguageResponse();

                            dto.setProblemLanguageId(pl.getId());
                            dto.setCodingLanguage(pl.getLanguage());
                            dto.setFunctionSignature(pl.getFunctionSignature());
                            dto.setTemplateCode(pl.getTemplateCode());
                            dto.setDriverCode(pl.getDriverCode());

                            return dto;
                        })
                        .toList();

        response.setProblemLanguages(languages);

        // Test Cases (IMPORTANT: sort by orderIndex)
        List<ProblemResponseDto.TestCaseResponse> testCases =
                problem.getTestCases()
                        .stream()
                        .sorted(Comparator.comparing(TestCase::getOrderIndex))
                        .map(tc -> {
                            ProblemResponseDto.TestCaseResponse dto =
                                    new ProblemResponseDto.TestCaseResponse();

                            dto.setTestCaseId(tc.getId());
                            dto.setInputData(tc.getInputData());
                            dto.setOutputData(tc.getOutputData());
                            dto.setHidden(tc.getIsHidden());
                            dto.setOrderIndex(tc.getOrderIndex());

                            return dto;
                        })
                        .toList();

        response.setTestCases(testCases);

        return response;
    }
}


