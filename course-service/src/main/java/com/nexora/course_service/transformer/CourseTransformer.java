package com.nexora.course_service.transformer;

import com.nexora.course_service.dto.response.CourseResponseDto;
import com.nexora.course_service.entity.Course;
import jakarta.validation.constraints.NotNull;


public class CourseTransformer {
    public static CourseResponseDto courseToCourseResponseDto(Course course){
        CourseResponseDto courseResponseDto = new CourseResponseDto();
        courseResponseDto.setUserId(course.getUserId());
        courseResponseDto.setCourseId(course.getId());
        courseResponseDto.setTitle(course.getTitle());
        courseResponseDto.setDescription(course.getDescription());
        courseResponseDto.setCategories(course.getCategories().stream().map(CategoryTransformer::toDTO).toList());
        courseResponseDto.setCourseStatus(course.getCourseStatus());
        courseResponseDto.setPrice(course.getPrice());
        courseResponseDto.setThumbnailUrl(course.getThumbnailUrl());
        courseResponseDto.setModules(null);
        return courseResponseDto;
    }
}
