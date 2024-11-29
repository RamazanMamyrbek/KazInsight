package ru.ramazanmamyrbek.kazinsightmonolith.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.ramazanmamyrbek.kazinsightmonolith.entity.Image;

@Repository
public interface ImageRepository extends JpaRepository<Image, Long> {
}
