package com.nexora.course_service.service;

import com.nexora.course_service.dto.request.ModuleRequestDto;
import com.nexora.course_service.dto.response.ModuleResponseDto;

public interface IModuleService {

    ModuleResponseDto createModule(String courseId, ModuleRequestDto moduleRequestDto);

}
