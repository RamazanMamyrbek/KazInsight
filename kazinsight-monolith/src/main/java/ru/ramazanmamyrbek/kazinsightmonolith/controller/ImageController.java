package ru.ramazanmamyrbek.kazinsightmonolith.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.net.MalformedURLException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.NoSuchElementException;

@Controller
@RequiredArgsConstructor
@RequestMapping("/images")
public class ImageController {

    @GetMapping("/{filename}")
    public ResponseEntity<?> getImage(@PathVariable("filename") String fileName) {
        try {
            Path filePath = Paths.get("images").resolve(fileName);
            Resource resource = new UrlResource(filePath.toUri());
            if(resource.exists() && resource.isReadable()) {
                return ResponseEntity
                        .ok()
                        .header(HttpHeaders.CONTENT_DISPOSITION,"attachment; filename=\"" + resource.getFilename() + "\"")
                        .body(resource);
            } else {
                throw new NoSuchElementException("files.errors.file_is_not_found");
            }
        } catch (MalformedURLException e) {
            throw new NoSuchElementException("files.errors.file_is_not_found");
        }
    }
}
