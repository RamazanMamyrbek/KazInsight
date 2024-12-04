package ru.ramazanmamyrbek.kazinsightmonolith.service;

import org.springframework.web.multipart.MultipartFile;
import ru.ramazanmamyrbek.kazinsightmonolith.controller.payload.NewPlacePayload;
import ru.ramazanmamyrbek.kazinsightmonolith.controller.payload.UpdatePlacePayload;
import ru.ramazanmamyrbek.kazinsightmonolith.entity.Place;

import java.io.IOException;
import java.util.List;

public interface PlaceService {
    List<Place> findAll(String filter);

    Place createPlace(NewPlacePayload payload) throws IOException;

    Place findPlace(Long placeId);

    void updatePlace(Long placeId, UpdatePlacePayload payload);

    void deletePlace(Long placeId);

    void addImageForPlace(Long placeId, List<MultipartFile> images) throws IOException;
}
