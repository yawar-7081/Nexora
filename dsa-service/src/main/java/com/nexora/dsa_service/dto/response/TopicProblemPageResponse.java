package com.nexora.dsa_service.dto.response;

import com.nexora.dsa_service.entity.enums.ProblemDificulty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TopicProblemPageResponse {

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ProblemInTopic {
        private String id;
        private String title;
        private ProblemDificulty difficulty;
        private Integer submissionCount;
        private Integer acceptedCount;
    }

    private String topicId;
    private String topicName;
    private List<ProblemInTopic> problems;
    private long totalElements;
    private int totalPages;
    private int currentPage;
    private int pageSize;
    private boolean hasNext;
    private boolean hasPrevious;
}
