package ru.ramazanmamyrbek.kazinsightmonolith.repository;

import org.springframework.data.domain.Limit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.ramazanmamyrbek.kazinsightmonolith.entity.Place;
import ru.ramazanmamyrbek.kazinsightmonolith.entity.enums.PlaceType;

import java.util.List;

@Repository
public interface PlaceRepository extends JpaRepository<Place, Long> {
    List<Place> findAllByNameLikeIgnoreCase(String name);

    List<Place> findByLocationAndType(String city, PlaceType placeType);

    List<Place> findByLocationAndType(String city, PlaceType placeType, Limit limit);

    List<Place> findByLocationAndTypeAndNameLikeIgnoreCase(String city, PlaceType placeType, String name);
}
