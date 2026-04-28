package com.nexora.dsa_service.dto.request;

import com.nexora.dsa_service.entity.enums.CodingLanguage;
import com.nexora.dsa_service.entity.enums.ProblemDificulty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ProblemRequestDto {

    private String title;
    private String description;
    private ProblemDificulty difficulty;
    private Set<String> constraints;
    private Integer timeLimit;
    private Integer memoryLimit;
    private List<String> topicNames;
    private List<ProblemLanguageRequest> problemLanguages;
    private List<TestCaseRequest> testCases;

    @Data
    public static class ProblemLanguageRequest{
        private CodingLanguage codingLanguage;
        private String functionSignature;
        private String templateCode;
        private String driverCode;
    }

    @Data
    public static class TestCaseRequest{
        private String inputData;
        private String outputData;
        private boolean isHidden;
        private Integer orderIndex;
    }

}

