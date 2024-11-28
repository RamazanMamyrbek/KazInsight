package ru.ramazanmamyrbek.kazinsightmonolith.service;

import ru.ramazanmamyrbek.kazinsightmonolith.controller.payload.NewPlacePayload;
import ru.ramazanmamyrbek.kazinsightmonolith.controller.payload.UpdatePlacePayload;
import ru.ramazanmamyrbek.kazinsightmonolith.entity.Place;

import java.util.List;

public interface PlaceService {
    List<Place> findAll(String filter);

    Place createPlace(NewPlacePayload payload);

    Place findPlace(Long placeId);

    void updatePlace(Long placeId, UpdatePlacePayload payload);

    void deletePlace(Long placeId);
}