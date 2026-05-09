package com.nexora.course_service.dto.response;

import lombok.Data;

import java.util.List;

@Data
public class ModuleResponseDto {
    private String id;
    private String title;
    private Integer moduleOrder;
    private List<LectureResponseDto> lectures;
}
