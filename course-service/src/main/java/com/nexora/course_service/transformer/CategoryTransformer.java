package com.nexora.course_service.transformer;

import com.nexora.course_service.dto.request.CategoryRequestDTO;
import com.nexora.course_service.dto.response.CategoryResponseDTO;
import com.nexora.course_service.entity.Category;
import org.springframework.stereotype.Component;

@Component
public class CategoryTransformer {
    public static Category toEntity(CategoryRequestDTO dto) {
        Category category = new Category();
        category.setCategoryName(dto.getCategoryName());
        return category;
    }

    public static CategoryResponseDTO toDTO(Category entity) {
        return CategoryResponseDTO.builder()
                .id(entity.getId())
                .categoryName(entity.getCategoryName())
                .build();
    }
}
