package ru.ramazanmamyrbek.kazinsightmonolith.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Limit;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import ru.ramazanmamyrbek.kazinsightmonolith.controller.payload.NewTourPayload;
import ru.ramazanmamyrbek.kazinsightmonolith.controller.payload.UpdateTourPayload;
import ru.ramazanmamyrbek.kazinsightmonolith.entity.Image;
import ru.ramazanmamyrbek.kazinsightmonolith.entity.Review;
import ru.ramazanmamyrbek.kazinsightmonolith.entity.Tour;
import ru.ramazanmamyrbek.kazinsightmonolith.entity.User;
import ru.ramazanmamyrbek.kazinsightmonolith.repository.ReviewRepository;
import ru.ramazanmamyrbek.kazinsightmonolith.repository.TourRepository;
import ru.ramazanmamyrbek.kazinsightmonolith.repository.UserRepository;
import ru.ramazanmamyrbek.kazinsightmonolith.service.ImageService;
import ru.ramazanmamyrbek.kazinsightmonolith.service.TourService;
import ru.ramazanmamyrbek.mapper.TourMapper;

import java.io.IOException;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class TourServiceImpl implements TourService {

    private final TourRepository tourRepository;
    private final ImageService imageService;
    private final ReviewRepository reviewRepository;
    private final UserRepository userRepository;

    @Override
    public List<Tour> findAll(String filter) {
        if(filter != null && !filter.isBlank()) {
            return tourRepository.findAllByNameLikeIgnoreCase("%"+filter+"%");
        }
        return tourRepository.findAll();
    }

    @Override
    @Transactional
    public Tour createTour(NewTourPayload payload) throws IOException {
        Tour tour = TourMapper.newTourPayloadToTour(payload, new Tour());
        List<Image> images = imageService.saveListImage(payload.images(), tour);
        imageService.saveListImageToDB(images);
        return tourRepository.save(tour);
    }

    @Override
    public Tour findTour(Long tourId) {
        return tourRepository.findById(tourId).orElseThrow(() -> new NoSuchElementException("tours.errors.tour_is_not_found"));
    }

    @Override
    @Transactional
    public Tour updateTour(Long tourId, UpdateTourPayload payload) {
        Tour tour = findTour(tourId);
        tour = TourMapper.updateTourPayloadToTour(payload, tour);
        return tourRepository.save(tour);
    }

    @Override
    @Transactional
    public void deleteTour(Long tourId) {
        tourRepository.deleteById(tourId);
    }

    @Override
    @Transactional
    public void addImageForTour(Long tourId, List<MultipartFile> images) throws IOException {
        Tour tour = findTour(tourId);
        List<Image> imageList = imageService.saveListImage(images, tour);
        tour.setImages(imageList);
        tourRepository.save(tour);
    }

    @Override
    @Transactional
    public Tour saveTour(Tour tour) {
        return tourRepository.save(tour);
    }

    @Override
    public List<Tour> findTopForCity(String city, int top) {
        return tourRepository.findByLocation(city, Limit.of(top));
    }

    @Override
    public List<Tour> findAllByCity(String filter, String city) {
        return findAll(filter).stream()
                .filter(tour -> tour.getLocation().equalsIgnoreCase(city))
                .toList();
    }

    @Override
    public double computeRating(long id) {
        Optional<Tour> optionalTour = tourRepository.findById(id);
        if (optionalTour.isPresent()) {
            List<Review> reviews = reviewRepository.findByTour(optionalTour.get());
            if (reviews.isEmpty()) {
                return 0;
            }
            double rating = reviews.stream()
                    .map(Review::getRating)
                    .reduce(Double::sum)
                    .orElse(0d) / reviews.size();
            return ((int) (rating * 100)) / 100d;
        }
        return 0;
    }

    @Override
    public List<Tour> findFavouriteToursByCityAndUsername(String city, String name, String filter) {
        User user = userRepository.findByEmail(name).get();
        return findAllByCity(filter, city).stream()
                .filter(tour -> tour.getFavouriteUsers().contains(user))
                .toList();
    }

    @Override
    public List<Tour> findToursAndParticipantName(String filter, String name) {
        User user = userRepository.findByEmail(name).get();
        return findAll(filter).stream()
                .filter(tour -> tour.getParticipants().contains(user))
                .toList();
    }
}
