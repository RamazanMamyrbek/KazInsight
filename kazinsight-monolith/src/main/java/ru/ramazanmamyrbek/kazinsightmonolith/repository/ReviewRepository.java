package ru.ramazanmamyrbek.kazinsightmonolith.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.ramazanmamyrbek.kazinsightmonolith.entity.Place;
import ru.ramazanmamyrbek.kazinsightmonolith.entity.Review;
import ru.ramazanmamyrbek.kazinsightmonolith.entity.Tour;

import java.util.List;

public interface ReviewRepository extends JpaRepository<Review, Long> {
    List<Review> findByTour(Tour tour);

    List<Review> findByPlace(Place place);
}
