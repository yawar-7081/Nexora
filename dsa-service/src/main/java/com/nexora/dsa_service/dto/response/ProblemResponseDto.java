package com.nexora.dsa_service.dto.response;

import com.nexora.dsa_service.dto.request.ProblemRequestDto;
import com.nexora.dsa_service.entity.enums.CodingLanguage;
import com.nexora.dsa_service.entity.enums.ProblemDificulty;
import lombok.Data;

import java.util.List;
import java.util.Set;

@Data
public class ProblemResponseDto {
    private String problemId;
    private String title;
    private String description;
    private ProblemDificulty difficulty;
    private Set<String> constraints;
    private Integer timeLimit;
    private Integer memoryLimit;
    private List<String> topicNames;
    private List<ProblemLanguageResponse> problemLanguages;
    private List<TestCaseResponse> testCases;

    @Data
    public static class ProblemLanguageResponse{
        private String problemLanguageId;
        private CodingLanguage codingLanguage;
        private String functionSignature;
        private String templateCode;
        private String driverCode;
    }

    @Data
    public static class TestCaseResponse{
        private String testCaseId;
        private String inputData;
        private String outputData;
        private boolean isHidden;
        private Integer orderIndex;
    }
}
