package ru.ramazanmamyrbek.kazinsightmonolith.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Limit;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import ru.ramazanmamyrbek.kazinsightmonolith.controller.payload.NewPlacePayload;
import ru.ramazanmamyrbek.kazinsightmonolith.controller.payload.UpdatePlacePayload;
import ru.ramazanmamyrbek.kazinsightmonolith.entity.Image;
import ru.ramazanmamyrbek.kazinsightmonolith.entity.Place;
import ru.ramazanmamyrbek.kazinsightmonolith.entity.User;
import ru.ramazanmamyrbek.kazinsightmonolith.entity.enums.PlaceType;
import ru.ramazanmamyrbek.kazinsightmonolith.repository.PlaceRepository;
import ru.ramazanmamyrbek.kazinsightmonolith.repository.UserRepository;
import ru.ramazanmamyrbek.kazinsightmonolith.service.ImageService;
import ru.ramazanmamyrbek.kazinsightmonolith.service.PlaceService;

import java.io.IOException;
import java.util.List;
import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class PlaceServiceImpl implements PlaceService {
    private final PlaceRepository placeRepository;
    private final ImageService imageService;
    private final UserRepository userRepository;

    @Override
    public List<Place> findAll(String filter) {
        if (filter != null && !filter.isBlank()) {
            return placeRepository.findAllByNameLikeIgnoreCase("%" + filter + "%");
        }
        return placeRepository.findAll();
    }

    @Override
    @Transactional
    public Place createPlace(NewPlacePayload payload) throws IOException {
        Place place = new Place();
        place.setName(payload.name());
        place.setDescription(payload.description());
        place.setLongitude(payload.longitude());
        place.setLatitude(payload.latitude());
        place.setLocation(payload.location());
        place.setType(payload.type());
        List<Image> images = imageService.saveListImage(payload.images(), place);
        imageService.saveListImageToDB(images);
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
        place.setType(payload.type());

        placeRepository.save(place);
    }

    @Override
    @Transactional
    public void deletePlace(Long placeId) {
        Place place = findPlace(placeId);
        placeRepository.delete(place);
    }

    @Override
    @Transactional
    public void addImageForPlace(Long placeId, List<MultipartFile> images) throws IOException {
        Place place = findPlace(placeId);
        List<Image> imageList = imageService.saveListImage(images, place);
        place.setImages(imageList);
        placeRepository.save(place);
    }

    @Override
    @Transactional
    public Place savePlace(Place place) {
        return placeRepository.save(place);
    }

    @Override
    public List<Place> findTopForCityByPlaceType(String city, PlaceType placeType, int top) {
        return placeRepository.findByLocationAndType(city, placeType, Limit.of(top));
    }

    @Override
    public List<Place> findPopularsByCity(String city) {
        return placeRepository.findByLocationAndType(city, PlaceType.INDOOR);
    }

    @Override
    public List<Place> findTopPopularByCity(String city, int top) {
        return placeRepository.findByLocationAndType(city, PlaceType.INDOOR, Limit.of(top));
    }

    @Override
    public List<Place> findPopularsByCityAndFilter(String city, String filter) {
        return findAll(filter).stream()
                .filter(place -> place.getLocation().equalsIgnoreCase(city))
                .toList();
    }

    @Override
    public List<Place> findByLocationAndType(String city, PlaceType placeType) {
        return placeRepository.findByLocationAndType(city, placeType);
    }

    @Override
    public List<Place> findByLocationAndTypeAndFilter(String city, PlaceType placeType, String filter) {
        return placeRepository.findByLocationAndTypeAndNameLikeIgnoreCase(city, placeType, "%" + filter + "%");
    }

    @Override
    public List<Place> findFavouriteToursByCityAndUsername(String city, String name, String filter) {
        User user = userRepository.findByEmail(name).get();
        return findAll(filter).stream()
                .filter(place -> place.getLocation().equalsIgnoreCase(city))
                .filter(place -> place.getFavoriteUsers().contains(user))
                .toList();
    }
}
