package com.nexora.course_service.service.impl;


import com.nexora.course_service.dto.request.CategoryRequestDTO;
import com.nexora.course_service.dto.response.CategoryResponseDTO;
import com.nexora.course_service.entity.Category;
import com.nexora.course_service.repository.CategoryRepository;
import com.nexora.course_service.service.ICategoryService;
import com.nexora.course_service.transformer.CategoryTransformer;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class CategoryService implements ICategoryService {

    private final CategoryRepository repository;
    private final CategoryTransformer categoryTransformer;


    @Override
    public CategoryResponseDTO create(CategoryRequestDTO request) {

        log.info("Creating category with name: {}", request.getCategoryName());

        if (repository.existsByCategoryName(request.getCategoryName())) {
            throw new RuntimeException("Category already exists with name: "
                    + request.getCategoryName());
        }

        Category entity = categoryTransformer.toEntity(request);
        Category saved = repository.save(entity);

        log.info("Category created with id: {} || category : {} || createdAt : {} || updatedAt : {}"
                , saved.getId(),saved.getCategoryName(),saved.getCreatedAt(),saved.getUpdatedAt());

        return categoryTransformer.toDTO(saved);
    }

    @Override
    public List<CategoryResponseDTO> getAll() {

        log.debug("Fetching all categories");

        return repository.findAll()
                .stream()
                .map(categoryTransformer::toDTO)
                .toList();
    }

    @Override
    public CategoryResponseDTO getById(String id) {

        log.debug("Fetching category with id: {}", id);

        Category category = repository.findById(id)
                .orElseThrow(() -> new RuntimeException(
                        "Category not found with id: " + id));

        return categoryTransformer.toDTO(category);
    }

    @Override
    @Transactional
    public CategoryResponseDTO update(String id, CategoryRequestDTO request) {

        log.info("Updating category id: {}", id);

        Category existing = repository.findById(id)
                .orElseThrow(() -> new RuntimeException(
                        "Category not found with id: " + id));

        // prevent duplicate names
        if (!existing.getCategoryName().equals(request.getCategoryName())
                && repository.existsByCategoryName(request.getCategoryName())) {

            throw new RuntimeException(
                    "Category already exists with name: " + request.getCategoryName());
        }

        existing.setCategoryName(request.getCategoryName());

        Category updated = repository.save(existing);

        log.info("Category updated with id: {}", updated.getId());

        return categoryTransformer.toDTO(updated);
    }

    @Override
    @Transactional
    public void delete(String id) {

        log.warn("Deleting category with id: {}", id);

        Category existing = repository.findById(id)
                .orElseThrow(() -> new RuntimeException(
                        "Category not found with id: " + id));

        repository.delete(existing);

        log.warn("Category deleted with id: {}", id);
    }
}
