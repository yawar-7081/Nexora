package com.nexora.course_service.controller;

import com.nexora.course_service.dto.request.CourseRequestDto;
import com.nexora.course_service.dto.response.CourseResponseDto;
import com.nexora.course_service.service.ICourseService;
import com.nexora.course_service.service.impl.CourseService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/course")
@RequiredArgsConstructor
public class CourseController {

    private final ICourseService courseService;

    @PostMapping
    public ResponseEntity<CourseResponseDto> createCourse(@RequestBody(required = true) @Valid CourseRequestDto courseRequestDto) {
        return ResponseEntity.ok(courseService.createCourse(courseRequestDto));
    }

}
