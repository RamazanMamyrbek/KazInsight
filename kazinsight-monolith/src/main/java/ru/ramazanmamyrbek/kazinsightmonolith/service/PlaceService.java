package ru.ramazanmamyrbek.kazinsightmonolith.service;

import org.springframework.web.multipart.MultipartFile;
import ru.ramazanmamyrbek.kazinsightmonolith.controller.payload.NewPlacePayload;
import ru.ramazanmamyrbek.kazinsightmonolith.controller.payload.UpdatePlacePayload;
import ru.ramazanmamyrbek.kazinsightmonolith.entity.Place;
import ru.ramazanmamyrbek.kazinsightmonolith.entity.enums.PlaceType;

import java.io.IOException;
import java.util.List;

public interface PlaceService {
    List<Place> findAll(String filter);

    Place createPlace(NewPlacePayload payload) throws IOException;

    Place findPlace(Long placeId);

    void updatePlace(Long placeId, UpdatePlacePayload payload);

    void deletePlace(Long placeId);

    void addImageForPlace(Long placeId, List<MultipartFile> images) throws IOException;

    Place savePlace(Place place);

    List<Place> findTopForCityByPlaceType(String city, PlaceType placeType, int top);

    List<Place> findPopularsByCity(String city);

    List<Place> findTopPopularByCity(String city, int top);

    List<Place> findPopularsByCityAndFilter(String city, String filter);

    List<Place> findByLocationAndType(String city, PlaceType placeType);

    List<Place> findByLocationAndTypeAndFilter(String city, PlaceType placeType, String filter);

    List<Place> findFavouriteToursByCityAndUsername(String city, String name, String filter);
}
