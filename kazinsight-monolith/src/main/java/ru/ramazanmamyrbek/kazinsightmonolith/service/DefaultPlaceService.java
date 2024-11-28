package ru.ramazanmamyrbek.kazinsightmonolith.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.ramazanmamyrbek.kazinsightmonolith.controller.payload.NewPlacePayload;
import ru.ramazanmamyrbek.kazinsightmonolith.controller.payload.UpdatePlacePayload;
import ru.ramazanmamyrbek.kazinsightmonolith.entity.Place;
import ru.ramazanmamyrbek.kazinsightmonolith.repository.PlaceRepository;

import java.util.List;
import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class DefaultPlaceService implements PlaceService {
    private final PlaceRepository placeRepository;

    @Override
    public List<Place> findAll(String filter) {
        if (filter != null && !filter.isBlank()) {
            return placeRepository.findAllByNameLikeIgnoreCase("%" + filter + "%");
        }
        return placeRepository.findAll();
    }

    @Override
    @Transactional
    public Place createPlace(NewPlacePayload payload) {
        Place place = new Place();

        place.setName(payload.name());
        place.setDescription(payload.description());
        place.setLongitude(payload.longitude());
        place.setLatitude(payload.latitude());
        place.setLocation(payload.location());

        return placeRepository.save(place);
    }

    @Override
    public Place findPlace(Long placeId) {
        return placeRepository.findById(placeId).orElseThrow(() -> new NoSuchElementException("places.errors.place_is_not_found"));
    }

    @Override
    @Transactional
    public void updatePlace(Long placeId, UpdatePlacePayload payload) {
        Place place = findPlace(placeId);
        place.setName(payload.name());
        place.setDescription(payload.description());
        place.setLongitude(payload.longitude());
        place.setLatitude(payload.latitude());
        place.setLocation(payload.location());

        placeRepository.save(place);
    }

    @Override
    public void deletePlace(Long placeId) {
        Place place = findPlace(placeId);
        placeRepository.delete(place);
    }
}
