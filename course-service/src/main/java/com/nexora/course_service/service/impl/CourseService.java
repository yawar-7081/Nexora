package com.nexora.course_service.service.impl;

import com.nexora.course_service.dto.request.CourseRequestDto;
import com.nexora.course_service.dto.response.CourseResponseDto;
import com.nexora.course_service.entity.Category;
import com.nexora.course_service.entity.Course;
import com.nexora.course_service.entity.enums.CourseStatus;
import com.nexora.course_service.repository.CategoryRepository;
import com.nexora.course_service.repository.CourseRepository;
import com.nexora.course_service.service.CloudinaryService;
import com.nexora.course_service.service.ICourseService;
import com.nexora.course_service.transformer.CourseTransformer;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CourseService implements ICourseService {

    private final CourseRepository courseRepository;
    private final CloudinaryService cloudinaryService;
    private final CategoryRepository categoryRepository;

    @Override
    public CourseResponseDto createCourse(CourseRequestDto courseRequestDto) {
        boolean isExists = courseRepository.existsByTitle(courseRequestDto.getTitle());

        if(isExists){
            throw new RuntimeException("Course with title " + courseRequestDto.getTitle() + " already exists");
        }

        Course newCourse = new Course();
        newCourse.setUserId(courseRequestDto.getUserId());
        newCourse.setTitle(courseRequestDto.getTitle());
        newCourse.setDescription(courseRequestDto.getDescription());
        newCourse.setPrice(courseRequestDto.getPrice());
        newCourse.setCourseStatus(CourseStatus.DRAFT);
        List<Category> categories = categoryRepository.findAllById(courseRequestDto.getCategoryIds());

        if(categories.size() != courseRequestDto.getCategoryIds().size()){
             throw new RuntimeException("One or more categories not found with ids: " + courseRequestDto.getCategoryIds());
        }

        newCourse.setCategories(categories);

        Course savedCourse = courseRepository.save(newCourse);

        return CourseTransformer.courseToCourseResponseDto(savedCourse);
    }

    @Override
    public CourseResponseDto updateCourse(CourseRequestDto courseRequestDto, String courseId) {
        return null;
    }

    @Override
    public CourseResponseDto getCourse(String courseId) {
        return null;
    }

    @Override
    public void deleteCourse(String courseId) {

    }

    @Override
    public CourseResponseDto addCategoryToCourse(String courseId, String categoryId) {
        return null;
    }

    @Override
    public CourseResponseDto removeCategoryToCourse(String courseId, String categoryId) {
        return null;
    }

    @Override
    public CourseResponseDto uploadCourseThumbnail(MultipartFile file, String courseId) {
        return null;
    }
}
