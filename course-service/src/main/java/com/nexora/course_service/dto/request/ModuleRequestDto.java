package com.nexora.course_service.dto.request;

import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class ModuleRequestDto {

    @NotBlank(message = "Title is required")
    @Size(min = 5, max = 50, message = "Title must be between 10 and 50 characters")
    String title;

    @Positive(message = "Module order must be a positive integer")
    @NotNull(message = "Module order is required")
    @Min(value = 1, message = "Module order must be at least 1")
    Integer moduleOrder;
}
