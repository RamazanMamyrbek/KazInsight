package ru.ramazanmamyrbek.kazinsightmonolith.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import ru.ramazanmamyrbek.kazinsightmonolith.controller.payload.NewTourPayload;
import ru.ramazanmamyrbek.kazinsightmonolith.controller.payload.UpdateTourPayload;
import ru.ramazanmamyrbek.kazinsightmonolith.entity.Image;
import ru.ramazanmamyrbek.kazinsightmonolith.entity.Tour;
import ru.ramazanmamyrbek.kazinsightmonolith.repository.TourRepository;
import ru.ramazanmamyrbek.kazinsightmonolith.service.ImageService;
import ru.ramazanmamyrbek.kazinsightmonolith.service.TourService;
import ru.ramazanmamyrbek.mapper.TourMapper;

import java.io.IOException;
import java.util.List;
import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class TourServiceImpl implements TourService {
    private final TourRepository tourRepository;
    private final ImageService imageService;
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
}
