package com.nexora.course_service.dto.response;

import com.nexora.course_service.entity.Category;
import com.nexora.course_service.entity.enums.CourseStatus;
import lombok.Data;

import java.util.List;

@Data
public class CourseResponseDto {
    private String courseId;
    private String userId;
    private String title;
    private String description;
    private Double price;
    private List<CategoryResponseDTO> categories;
    private List<ModuleResponseDto> modules;
    private String thumbnailUrl;
    private CourseStatus courseStatus;
}
