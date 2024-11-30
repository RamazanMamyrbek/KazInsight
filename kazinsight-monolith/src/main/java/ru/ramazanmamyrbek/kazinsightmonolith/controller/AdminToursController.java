package ru.ramazanmamyrbek.kazinsightmonolith.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;
import ru.ramazanmamyrbek.kazinsightmonolith.controller.payload.NewTourPayload;
import ru.ramazanmamyrbek.kazinsightmonolith.controller.payload.UpdateTourPayload;
import ru.ramazanmamyrbek.kazinsightmonolith.entity.Tour;
import ru.ramazanmamyrbek.kazinsightmonolith.service.TourService;

import java.io.IOException;

@Controller
@RequiredArgsConstructor
@RequestMapping("/admin/tours")
public class AdminToursController {
    private final TourService tourService;

    @GetMapping
    public String showAllToursPage(
            @RequestParam(name = "filter", required = false, defaultValue = "") String filter,
            Model model) {
        model.addAttribute("tours", tourService.findAll(filter));
        model.addAttribute("filter", filter);
        return "admin/tours/tours";
    }

    @GetMapping("/create")
    public String createTourPage() {
        return "admin/tours/new_tour";
    }

    @PostMapping
    public String createTour(@Valid NewTourPayload newTourPayload, BindingResult bindingResult, Model model) throws IOException {
        if(bindingResult.hasErrors()) {
            model.addAttribute("errors", bindingResult
                    .getAllErrors()
                    .stream()
                    .map(ObjectError::getDefaultMessage)
                    .toList());
            model.addAttribute(newTourPayload);
            return "admin/tours/new_tour";
        }
        Tour tour = tourService.createTour(newTourPayload);
        return "redirect:/admin/tours/%d".formatted(tour.getId());
    }

    @GetMapping("/{tourId}")
    public String getTourPage(
            @PathVariable Long tourId,
            Model model
    ) {
        model.addAttribute("tour", tourService.findTour(tourId));
        return "admin/tours/tour";
    }

    @GetMapping("/{tourId}/edit")
    public String editTourPage(@PathVariable Long tourId, Model model) {
        Tour tour = tourService.findTour(tourId);
        model.addAttribute("tour", tour);
        return "admin/tours/edit";
    }

    @PutMapping("/{tourId}")
    public String editTour(@PathVariable Long tourId, @Valid UpdateTourPayload payload, BindingResult bindingResult, Model model) {
        if(bindingResult.hasErrors()) {
            model.addAttribute("errors", bindingResult.getAllErrors()
                    .stream()
                    .map(ObjectError::getDefaultMessage)
                    .toList());
            model.addAttribute("tour", tourService.findTour(tourId));
            model.addAttribute("updateTourPayload", payload);
            return "admin/tours/edit";
        }
        tourService.updateTour(tourId, payload);
        return "redirect:/admin/tours/%d".formatted(tourId);
    }

    @DeleteMapping("/{tourId}")
    public String deleteTour(@PathVariable Long tourId) {
        tourService.deleteTour(tourId);
        return "redirect:/admin/tours";
    }
}
