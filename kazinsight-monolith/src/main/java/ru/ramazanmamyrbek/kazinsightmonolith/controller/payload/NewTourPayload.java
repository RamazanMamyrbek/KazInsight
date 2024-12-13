package ru.ramazanmamyrbek.kazinsightmonolith.controller.payload;

import jakarta.validation.constraints.*;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.List;

public record NewTourPayload(
        @NotNull(message = "{tours.create.errors.title_is_null}")
        @NotBlank(message = "{tours.create.errors.title_is_blank}")
        @Size(min = 2, max = 200, message = "{tours.create.errors.title_size_is_invalid}")
        String name,
        @Size(max = 5000, message = "{tours.create.errors.description_size_is_invalid}")
        String description,
        @NotNull(message = "{tours.errors.start_date_is_empty}")
        @Future(message = "{tours.errors.start_date_is_in_past}") // Проверка, что startDate в будущем
        LocalDateTime startDate,

        @NotNull(message = "{tours.errors.end_date_is_empty}")
        @Future(message = "{tours.errors.end_date_is_in_past}") // Проверка, что endDate в будущем
        LocalDateTime endDate,
        @Min(value = 10, message = "{tours.create.errors.price_is_invalid}")
        Double price,
        @NotNull(message = "{tours.create.errors.location_is_null}")
        @NotBlank(message = "{tours.create.errors.location_is_blank}")
        String location,
        List<MultipartFile> images
) {
}
