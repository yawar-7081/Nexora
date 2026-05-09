package com.nexora.course_service.service;

import com.nexora.course_service.dto.request.CourseRequestDto;
import com.nexora.course_service.dto.response.CourseResponseDto;
import org.springframework.web.multipart.MultipartFile;

public interface ICourseService {
    CourseResponseDto createCourse(CourseRequestDto courseRequestDto);

    CourseResponseDto updateCourse(CourseRequestDto courseRequestDto,String courseId);

    CourseResponseDto getCourse(String courseId);

    void deleteCourse(String courseId);

    CourseResponseDto addCategoryToCourse(String courseId, String categoryId);

    CourseResponseDto removeCategoryToCourse(String courseId, String categoryId);

    CourseResponseDto uploadCourseThumbnail(MultipartFile file, String courseId);
}
