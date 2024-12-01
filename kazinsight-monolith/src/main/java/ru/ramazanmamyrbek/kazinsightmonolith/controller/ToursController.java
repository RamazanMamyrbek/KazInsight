package ru.ramazanmamyrbek.kazinsightmonolith.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.ramazanmamyrbek.kazinsightmonolith.service.TourService;

@Controller
@RequiredArgsConstructor
@RequestMapping("/tours")
public class ToursController {
    private final TourService tourService;

    @GetMapping
    public String getAllToursPage(@RequestParam(value = "filter", required = false, defaultValue = "") String filter,
                              Model model) {
        model.addAttribute("tours", tourService.findAll(filter));
        model.addAttribute("filter", filter);
        return "user/tours/tours";
    }

    @GetMapping("/{tourId}")
    public String getTourPage(@PathVariable Long tourId,
                              Model model) {
        model.addAttribute("tour", tourService.findTour(tourId));
        return "user/tours/tour";
    }
}
