package com.nexora.dsa_service.dto.response;

import com.nexora.dsa_service.entity.ProblemLanguage;
import com.nexora.dsa_service.entity.enums.CodingLanguage;
import lombok.Data;

import java.util.List;

@Data
public class SubmissionResponseDto {
    private String submissionId;
    private String problemId;
    private String userId;
    private String status;          // ACCEPTED / WRONG_ANSWER / ERROR
    private Long executionTime;
    private Long memoryUsed;
    private CodingLanguage language;

    private List<TestCaseResultDto> results;

    @Data
    public static class TestCaseResultDto {
        private String testCaseId;
        private String input;          // optional (hide later if needed)
        private String expectedOutput;
        private String actualOutput;
        private String status;         // PASS / FAIL
    }
}
