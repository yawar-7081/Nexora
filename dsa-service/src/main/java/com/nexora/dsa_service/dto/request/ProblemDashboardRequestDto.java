package com.nexora.dsa_service.dto.request;

import com.nexora.dsa_service.entity.Topic;
import com.nexora.dsa_service.entity.enums.ProblemDificulty;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Set;

@Data
@NoArgsConstructor
public class ProblemDashboardRequestDto {
    private List<Topic> topic;
    private Set<ProblemDificulty> problemDifficulty;
    private String searchProblemByTitle;
    private Integer pageNumber;
    private Integer pageSize;
    private List<SortingParameters> sortingPerametersList;

    @Data
    public static class SortingParameters {
        private String sortBy;
        private boolean sortDirection;
    }
}
