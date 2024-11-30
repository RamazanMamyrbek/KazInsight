package ru.ramazanmamyrbek.kazinsightmonolith.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ru.ramazanmamyrbek.kazinsightmonolith.entity.Image;
import ru.ramazanmamyrbek.kazinsightmonolith.entity.Place;
import ru.ramazanmamyrbek.kazinsightmonolith.entity.Tour;
import ru.ramazanmamyrbek.kazinsightmonolith.exception.FileNotSupportedException;
import ru.ramazanmamyrbek.kazinsightmonolith.repository.ImageRepository;
import ru.ramazanmamyrbek.kazinsightmonolith.service.ImageService;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ImageServiceImpl implements ImageService {
    @Value("${image.link-constructor}")
    private String imageLinkConstructor;
    private final ImageRepository imageRepository;

    public <T> List<Image> saveListImage(List<MultipartFile> images, T entityType) throws IOException {
        Path uploadPath = Paths.get("images");
        if(!Files.exists(uploadPath)) {
            Files.createDirectories(uploadPath);
        }
        List<Image> imageEntities = new ArrayList<>();
        for(MultipartFile image: images) {
            if(!(image.getContentType().equals("image/png") ||
            image.getContentType().equals("image/jpeg") ||
            image.getContentType().equals("image/jpg"))) {
                if(entityType instanceof Place) {
                    throw new FileNotSupportedException("places.create.errors.file_type_not_supported");
                } else {
                    throw new FileNotSupportedException("tours.create.errors.file_type_not_supported");
                }

            }
            String fileName = UUID.randomUUID().toString() + "-" +image.getOriginalFilename();
            Path filePath = uploadPath.resolve(fileName);
            Files.copy(image.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);
            String imageLink = imageLinkConstructor + fileName;
            if(entityType instanceof Place) {
                imageEntities.add(Image.builder()
                        .link(imageLink)
                        .place((Place) entityType)
                        .tour(null)
                        .build());
            } else if (entityType instanceof Tour) {
                imageEntities.add(Image.builder()
                                .link(imageLink)
                                .place(null)
                                .tour((Tour) entityType)
                        .build());
            } else {
                imageEntities.add(Image.builder()
                        .link(imageLink)
                        .place(null)
                        .tour(null)
                        .build());
            }
        }
        return imageEntities;
    }

    @Override
    public void saveListImageToDB(List<Image> images) {
        imageRepository.saveAll(images);
    }


}
