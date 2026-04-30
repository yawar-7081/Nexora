package com.nexora.dsa_service.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserTopicProgressDto {
    private String topicId;
    private String topicName;
    private Integer totalProblems;
    private Integer solvedProblems;
    private Integer easyAttempted;
    private Integer mediumAttempted;
    private Integer hardAttempted;
    private Integer easySolved;
    private Integer mediumSolved;
    private Integer hardSolved;
    private Double completionPercentage;
    private Double successRate;
}
