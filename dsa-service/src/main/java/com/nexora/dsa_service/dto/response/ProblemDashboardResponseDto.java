package com.nexora.dsa_service.dto.response;

import com.nexora.dsa_service.entity.Topic;
import com.nexora.dsa_service.entity.enums.ProblemDificulty;
import com.nexora.dsa_service.entity.enums.ProblemSolveStatus;
import com.nexora.dsa_service.entity.enums.SubmissionStatus;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class ProblemDashboardResponseDto {
    private  String problemId;
    private String title;
    private ProblemDificulty difficulty;
}
