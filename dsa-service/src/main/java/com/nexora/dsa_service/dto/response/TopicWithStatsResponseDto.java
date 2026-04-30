package com.nexora.dsa_service.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TopicWithStatsResponseDto {
    private String id;
    private String name;
    private String description;
    private Integer totalProblems;
    private Integer easyProblems;
    private Integer mediumProblems;
    private Integer hardProblems;
    private Integer acceptanceRate;
    private LocalDateTime createdAt;
}
