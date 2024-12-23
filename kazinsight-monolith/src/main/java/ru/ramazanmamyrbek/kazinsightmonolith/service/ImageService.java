package ru.ramazanmamyrbek.kazinsightmonolith.service;

import org.springframework.web.multipart.MultipartFile;
import ru.ramazanmamyrbek.kazinsightmonolith.entity.Image;
import ru.ramazanmamyrbek.kazinsightmonolith.entity.Place;

import java.io.IOException;
import java.util.List;

public interface ImageService {
    <T> List<Image> saveListImage(List<MultipartFile> images, T place) throws IOException;
    void saveListImageToDB(List<Image> images);

    void deleteById(Long imageId);
}
