package ru.ramazanmamyrbek.kazinsightmonolith.controller.payload;

import jakarta.validation.constraints.*;

public record UpdatePlacePayload(
        @NotNull(message = "{places.create.errors.title_is_null}")
        @NotBlank(message = "{places.create.errors.title_is_blank}")
        @Size(min = 2, max = 200, message = "{places.create.errors.title_size_is_invalid}")
        String name,

        @Size(max = 5000, message = "{places.create.errors.description_size_is_invalid}")
        String description,

        @Min(value = -180, message = "{places.create.errors.longitude_is_less_than_min}")
        @Max(value = 180, message = "{places.create.errors.longitude_is_greater_than_max}")
        Double longitude,

        @Min(value = -90, message = "{places.create.errors.latitude_is_less_than_min}")
        @Max(value = 90, message = "{places.create.errors.latitude_is_greater_than_max}")
        Double latitude,

        @NotNull(message = "{places.create.errors.location_is_null}")
        @NotBlank(message = "{places.create.errors.location_is_blank}")
        @Size(min = 2, max = 200, message = "{places.create.errors.location_size_is_invalid}")
        String location) {
}
