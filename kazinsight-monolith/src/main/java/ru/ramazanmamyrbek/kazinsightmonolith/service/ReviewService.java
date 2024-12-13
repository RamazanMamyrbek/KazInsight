package ru.ramazanmamyrbek.kazinsightmonolith.service;

import ru.ramazanmamyrbek.kazinsightmonolith.controller.payload.NewReviewPayload;
import ru.ramazanmamyrbek.kazinsightmonolith.entity.Review;

import java.util.List;

public interface ReviewService {
    void addReviewToTour(String username, Long tourId, NewReviewPayload payload);

    List<Review> findReviewsByTourId(long tourId);

    List<Review> findReviewsByPlaceId(long placeId);

    double computePlaceRating(long placeId);

    void addReviewToPlace(String name, Long placeId, NewReviewPayload payload);
}
