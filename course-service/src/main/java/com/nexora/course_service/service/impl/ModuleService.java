package com.nexora.course_service.service.impl;

import com.nexora.course_service.dto.request.ModuleRequestDto;
import com.nexora.course_service.dto.response.ModuleResponseDto;
import com.nexora.course_service.entity.Course;
import com.nexora.course_service.entity.Module;
import com.nexora.course_service.repository.CategoryRepository;
import com.nexora.course_service.repository.CourseRepository;
import com.nexora.course_service.repository.ModuleRepository;
import com.nexora.course_service.service.IModuleService;
import com.nexora.course_service.transformer.ModuleTransformer;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ModuleService implements IModuleService {

    private final CourseRepository courseRepository;
    private final ModuleRepository moduleRepository;

    @Override
    public ModuleResponseDto createModule(String courseId, ModuleRequestDto moduleRequestDto) {

        Course course = courseRepository.findById(courseId)
                .orElseThrow(() -> new RuntimeException("Course not found with id: " + courseId));

        String title = moduleRequestDto.getTitle();
        Integer moduleOrder = moduleRequestDto.getModuleOrder();


        Module module = new Module();
        module.setTitle(title);
        module.setModuleOrder(moduleOrder);
        module.setCourse(course);

        Module savedModule = moduleRepository.save(module);

        return ModuleTransformer.moduleToModuleResponseDto(savedModule);
    }
}
