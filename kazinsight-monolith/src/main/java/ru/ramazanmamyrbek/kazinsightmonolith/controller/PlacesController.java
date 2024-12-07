package ru.ramazanmamyrbek.kazinsightmonolith.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.ramazanmamyrbek.kazinsightmonolith.entity.Place;
import ru.ramazanmamyrbek.kazinsightmonolith.service.PlaceService;
import ru.ramazanmamyrbek.kazinsightmonolith.service.UserService;

import java.security.Principal;
import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/places")
public class PlacesController {
    private final PlaceService placeService;
    private final UserService userService;

    @GetMapping
    public String getAllPlaces(Model model,
                               @RequestParam(name = "filter", required = false, defaultValue = "") String filter) {
        List<Place> places = placeService.findAll(filter);
        model.addAttribute("filter", filter);
        model.addAttribute("places", places);
        return "user/places/places";
    }

    @GetMapping("/{placeId}")
    public String getPlacePage(@PathVariable("placeId") Long placeId, Model model,
                               Principal principal) {
        Place place = placeService.findPlace(placeId);
        model.addAttribute("user", userService.getUserByEmail(principal.getName()));
        model.addAttribute(place);
        return "user/places/place";
    }
}
