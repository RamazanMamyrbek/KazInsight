package ru.ramazanmamyrbek.kazinsightmonolith.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.ramazanmamyrbek.kazinsightmonolith.controller.payload.NewReviewPayload;
import ru.ramazanmamyrbek.kazinsightmonolith.entity.Place;
import ru.ramazanmamyrbek.kazinsightmonolith.entity.Review;
import ru.ramazanmamyrbek.kazinsightmonolith.repository.PlaceRepository;
import ru.ramazanmamyrbek.kazinsightmonolith.repository.ReviewRepository;
import ru.ramazanmamyrbek.kazinsightmonolith.repository.TourRepository;
import ru.ramazanmamyrbek.kazinsightmonolith.repository.UserRepository;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ReviewServiceImpl implements ReviewService {
    private final ReviewRepository reviewRepository;
    private final TourRepository tourRepository;
    private final UserRepository userRepository;
    private final PlaceRepository placeRepository;

    @Override
    @Transactional
    public void addReviewToTour(String username, Long tourId, NewReviewPayload payload) {
        userRepository.findByEmail(username)
                .ifPresent(user -> {
                    Review review = new Review();
                    review.setTour(tourRepository.findById(tourId).orElse(null));
                    review.setCreator(user);
                    review.setText(payload.text());
                    review.setRating(payload.rating());
                    reviewRepository.save(review);
                });
    }

    @Override
    public List<Review> findReviewsByTourId(long tourId) {
        return reviewRepository.findByTour(tourRepository.findById(tourId).orElse(null));
    }

    @Override
    public List<Review> findReviewsByPlaceId(long placeId) {
        return reviewRepository.findByPlace(placeRepository.findById(placeId).get());
    }

    @Override
    public double computePlaceRating(long placeId) {
        Optional<Place> optionalPlace = placeRepository.findById(placeId);
        if (optionalPlace.isPresent()) {
            Place place = optionalPlace.get();
            List<Review> reviews = reviewRepository.findByPlace(place);
            return computeRating(reviews);
        }
        return 0;
    }

    @Override
    public void addReviewToPlace(String name, Long placeId, NewReviewPayload payload) {
        userRepository.findByEmail(name)
                .ifPresent(user -> {
                    Review review = new Review();
                    review.setPlace(placeRepository.findById(placeId).orElse(null));
                    review.setCreator(user);
                    review.setText(payload.text());
                    review.setRating(payload.rating());
                    reviewRepository.save(review);
                });
    }

    public double computeRating(List<Review> reviews) {
        if (reviews.isEmpty()) {
            return 0;
        }
        double rating = reviews.stream()
                .map(Review::getRating)
                .reduce(Double::sum)
                .orElse(0d) / reviews.size();
        return ((int) (rating * 100)) / 100d;
    }
}
