package ru.ramazanmamyrbek.kazinsightmonolith.service;

import ru.ramazanmamyrbek.kazinsightmonolith.controller.payload.NewUserPayload;
import ru.ramazanmamyrbek.kazinsightmonolith.entity.Place;
import ru.ramazanmamyrbek.kazinsightmonolith.entity.Tour;
import ru.ramazanmamyrbek.kazinsightmonolith.entity.User;

import java.util.List;

public interface UserService {
    User getUserByEmail(String email);

    User saveUser(NewUserPayload newUserPayload);

    void replenishBalance(Long userId, Double value);

    User getUserById(Long userId);

    void resetBalance(Long userId);

    List<Place> getFavorites(Long userId);

    void addPlaceToFavorites(String name, Long placeId);

    void removePlaceFromFavorites(String name, Long placeId);

    List<Tour> getTours(Long userId);

    void addTourToMyTours(String name, Long tourId);

    void removeTourFromMyTours(String name, Long tourId);
}
