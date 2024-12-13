package ru.ramazanmamyrbek.kazinsightmonolith.service;

import org.springframework.web.multipart.MultipartFile;
import ru.ramazanmamyrbek.kazinsightmonolith.controller.payload.NewTourPayload;
import ru.ramazanmamyrbek.kazinsightmonolith.controller.payload.UpdateTourPayload;
import ru.ramazanmamyrbek.kazinsightmonolith.entity.Tour;

import java.io.IOException;
import java.util.List;

public interface TourService {
    List<Tour> findAll(String filter);

    Tour createTour(NewTourPayload payload) throws IOException;

    Tour findTour(Long tourId);

    Tour updateTour(Long tourId, UpdateTourPayload payload);

    void deleteTour(Long tourId);

    void addImageForTour(Long tourId, List<MultipartFile> images) throws IOException;

    Tour saveTour(Tour tour);

    List<Tour> findTopForCity(String city, int top);

    List<Tour> findAllByCity(String filter, String city);

    double computeRating(long id);

    List<Tour> findFavouriteToursByCityAndUsername(String city, String name, String filter);

    List<Tour> findToursAndParticipantName(String filter, String name);
}
