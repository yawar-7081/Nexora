package com.nexora.course_service.dto.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class CourseRequestDto {

    @NotNull(message = "User ID cannot be null")
    @Size(min = 1, message = "User ID cannot be empty")
    private String userId;

    @NotNull(message = "Title cannot be null")
    @Size(min = 1, max = 100, message = "Title cannot be empty")
    @NotBlank(message = "Title cannot be blank")
    private String title;

    @NotBlank(message = "Description cannot be blank")
    @Size(min = 1, max = 500, message = "Description must be between 1 and 500 characters")
    @NotNull(message = "Description cannot be null")
    private String description;

    @NotNull(message = "Price cannot be null")
    @Min(value = 0, message = "Price must be greater than or equal to 0")
    private Double price;

    @Size(min = 1, message = "At least one category must be provided")
    @NotNull(message = "Categories cannot be null")
    private List<String> categoryIds=new ArrayList<>();
}
