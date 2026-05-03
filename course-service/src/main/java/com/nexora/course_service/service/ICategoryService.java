package com.nexora.course_service.service;

import com.nexora.course_service.dto.request.CategoryRequestDTO;
import com.nexora.course_service.dto.response.CategoryResponseDTO;

import java.util.List;

public interface ICategoryService {

    CategoryResponseDTO create(CategoryRequestDTO request);
    List<CategoryResponseDTO> getAll();
    CategoryResponseDTO getById(String id);
    CategoryResponseDTO update(String id, CategoryRequestDTO request);
    void delete(String id);
}
