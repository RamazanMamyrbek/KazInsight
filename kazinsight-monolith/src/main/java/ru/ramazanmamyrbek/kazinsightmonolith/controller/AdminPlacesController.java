package ru.ramazanmamyrbek.kazinsightmonolith.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.ramazanmamyrbek.kazinsightmonolith.controller.payload.NewPlacePayload;
import ru.ramazanmamyrbek.kazinsightmonolith.controller.payload.UpdatePlacePayload;
import ru.ramazanmamyrbek.kazinsightmonolith.entity.Place;
import ru.ramazanmamyrbek.kazinsightmonolith.service.ImageService;
import ru.ramazanmamyrbek.kazinsightmonolith.service.PlaceService;

import java.io.IOException;
import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/admin/places")
public class AdminPlacesController {
    private final PlaceService placeService;
    private final ImageService imageService;

    @GetMapping
    public String getAllPlaces(Model model,
                               @RequestParam(name = "filter", required = false, defaultValue = "") String filter) {
        List<Place> places = placeService.findAll(filter);
        model.addAttribute("filter", filter);
        model.addAttribute("places", places);
        return "admin/places/places";
    }

    @GetMapping("/create")
    public String getNewPlacePage() {
        return "admin/places/new_place";
    }

    @PostMapping
    public String createPlace(@Valid NewPlacePayload payload, BindingResult bindingResult, Model model) throws IOException {
        if (bindingResult.hasErrors()) {
            model.addAttribute("errors", bindingResult.getAllErrors()
                    .stream()
                    .map(ObjectError::getDefaultMessage)
                    .toList());
            model.addAttribute("payload", payload);
            return "admin/places/new_place";
        }
        Place place = placeService.createPlace(payload);

        return "redirect:/admin/places/%d".formatted(place.getId());
    }

    @GetMapping("/{placeId}")
    public String getPlacePage(@PathVariable("placeId") Long placeId, Model model) {
        Place place = placeService.findPlace(placeId);
        model.addAttribute("place", place);
        return "admin/places/place";
    }

    @GetMapping("/{placeId}/edit")
    public String getPlaceEditPage(@PathVariable("placeId") Long placeId, Model model) {
        Place place = placeService.findPlace(placeId);
        model.addAttribute("place", place);
        return "admin/places/edit";
    }

    @PostMapping("/{placeId}")
    public String updatePlace(
            @PathVariable("placeId") Long placeId, @Valid UpdatePlacePayload payload,
            BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            Place place = placeService.findPlace(placeId);
            model.addAttribute("place", place);
            model.addAttribute("errors", bindingResult.getAllErrors()
                    .stream()
                    .map(ObjectError::getDefaultMessage)
                    .toList());
            model.addAttribute("payload", payload);
            return "admin/places/edit";
        }

        placeService.updatePlace(placeId, payload);
        model.addAttribute("place", placeService.findPlace(placeId));
        return "redirect:/admin/places/%d".formatted(placeId);
    }

    @PostMapping("/{placeId}/delete")
    public String deletePlace(@PathVariable("placeId") Long placeId) {
        placeService.deletePlace(placeId);
        return "redirect:/admin/places";
    }

    @PostMapping("/{placeId}/add-image")
    public String addImage(@PathVariable("placeId") Long placeId, @RequestParam("images") List<MultipartFile> images) throws IOException {
        placeService.addImageForPlace(placeId, images);
        return "redirect:/admin/places/%d/edit".formatted(placeId);
    }

    @PostMapping("/{placeId}/delete-image/{imageId}")
    public String deleteImage(@PathVariable("placeId") Long placeId, @PathVariable("imageId") Long imageId) {
        imageService.deleteById(imageId);
        return "redirect:/admin/tours/%d/edit".formatted(placeId);
    }
}
