package ru.ramazanmamyrbek.kazinsightmonolith.repository;

import org.springframework.data.domain.Limit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.ramazanmamyrbek.kazinsightmonolith.entity.Tour;

import java.util.List;

@Repository
public interface TourRepository extends JpaRepository<Tour, Long> {
    List<Tour> findAllByNameLikeIgnoreCase(String name);
    List<Tour> findByLocation(String location, Limit limit);

}
