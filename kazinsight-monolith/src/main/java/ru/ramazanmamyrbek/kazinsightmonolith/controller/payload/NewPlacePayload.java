package ru.ramazanmamyrbek.kazinsightmonolith.controller.payload;

import jakarta.validation.constraints.*;
import org.springframework.web.multipart.MultipartFile;
import ru.ramazanmamyrbek.kazinsightmonolith.entity.enums.PlaceType;

import java.util.List;

public record NewPlacePayload(
        @NotNull(message = "{places.create.errors.title_is_null}")
        @NotBlank(message = "{places.create.errors.title_is_blank}")
        @Size(min = 2, max = 200, message = "{places.create.errors.title_size_is_invalid}")
        String name,

        @Size(max = 5000, message = "{places.create.errors.description_size_is_invalid}")
        String description,

        @NotNull(message = "{places.create.errors.longitude_is_null}")
        @Min(value = -180, message = "{places.create.errors.longitude_is_less_than_min}")
        @Max(value = 180, message = "{places.create.errors.longitude_is_greater_than_max}")
        Double longitude,

        @NotNull(message = "{places.create.errors.latitude_is_null}")
        @Min(value = -90, message = "{places.create.errors.latitude_is_less_than_min}")
        @Max(value = 90, message = "{places.create.errors.latitude_is_greater_than_max}")
        Double latitude,

        @NotNull(message = "{places.create.errors.location_is_null}")
        @NotBlank(message = "{places.create.errors.location_is_blank}")
        @Size(min = 2, max = 200, message = "{places.create.errors.location_size_is_invalid}")
        String location,
        PlaceType type,
        List<MultipartFile> images){
}
