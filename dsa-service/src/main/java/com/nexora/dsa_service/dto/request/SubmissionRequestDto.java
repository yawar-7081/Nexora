package com.nexora.dsa_service.dto.request;

import com.nexora.dsa_service.entity.enums.CodingLanguage;
import lombok.Data;

@Data
public class SubmissionRequestDto {
    private String problemId;
    private CodingLanguage language;
    private String code; // user function body only
    private String userId;
}
