package ru.ramazanmamyrbek.mapper;

import lombok.experimental.UtilityClass;
import ru.ramazanmamyrbek.kazinsightmonolith.controller.payload.NewTourPayload;
import ru.ramazanmamyrbek.kazinsightmonolith.controller.payload.UpdateTourPayload;
import ru.ramazanmamyrbek.kazinsightmonolith.entity.Tour;

@UtilityClass
public class TourMapper {
    public Tour newTourPayloadToTour(NewTourPayload newTourPayload, Tour tour) {
        tour.setName(newTourPayload.name());
        tour.setDescription(newTourPayload.description());
        tour.setStartDate(newTourPayload.startDate());
        tour.setEndDate(newTourPayload.endDate());
        tour.setPrice(newTourPayload.price());
        return tour;
    }
    public Tour updateTourPayloadToTour(UpdateTourPayload newTourPayload, Tour tour) {
        tour.setName(newTourPayload.name());
        tour.setDescription(newTourPayload.description());
        tour.setStartDate(newTourPayload.startDate());
        tour.setEndDate(newTourPayload.endDate());
        tour.setPrice(newTourPayload.price());
        return tour;
    }
}
