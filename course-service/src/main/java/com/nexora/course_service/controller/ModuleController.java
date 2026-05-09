package com.nexora.course_service.controller;

import com.nexora.course_service.dto.request.ModuleRequestDto;
import com.nexora.course_service.dto.response.ModuleResponseDto;
import com.nexora.course_service.service.IModuleService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/module")
@RequiredArgsConstructor
public class ModuleController {

    private final IModuleService moduleService;

    @PostMapping("/{courseId}")
    public ResponseEntity<ModuleResponseDto> createModule(@PathVariable(required = true) String courseId, @RequestBody(required = true) @Valid ModuleRequestDto moduleRequestDto){
        return ResponseEntity.ok(moduleService.createModule(courseId, moduleRequestDto));
    }


}
