package com.nexora.course_service.transformer;

import com.nexora.course_service.dto.response.ModuleResponseDto;
import com.nexora.course_service.entity.Module;

public class ModuleTransformer {
    public static ModuleResponseDto moduleToModuleResponseDto(Module module){
        ModuleResponseDto moduleResponseDto = new ModuleResponseDto();
        moduleResponseDto.setId(module.getId());
        moduleResponseDto.setTitle(module.getTitle());
        moduleResponseDto.setModuleOrder(module.getModuleOrder());
        return moduleResponseDto;
    }
}
