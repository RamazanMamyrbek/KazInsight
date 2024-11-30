package ru.ramazanmamyrbek.kazinsightmonolith.service;

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
}