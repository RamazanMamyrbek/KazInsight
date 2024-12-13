package ru.ramazanmamyrbek.kazinsightmonolith.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.ramazanmamyrbek.kazinsightmonolith.controller.payload.NewReviewPayload;
import ru.ramazanmamyrbek.kazinsightmonolith.entity.Place;
import ru.ramazanmamyrbek.kazinsightmonolith.entity.Review;
import ru.ramazanmamyrbek.kazinsightmonolith.entity.User;
import ru.ramazanmamyrbek.kazinsightmonolith.entity.enums.PlaceType;
import ru.ramazanmamyrbek.kazinsightmonolith.service.PlaceService;
import ru.ramazanmamyrbek.kazinsightmonolith.service.ReviewService;
import ru.ramazanmamyrbek.kazinsightmonolith.service.UserService;

import java.security.Principal;
import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/places")
public class PlacesController {
    private final PlaceService placeService;
    private final UserService userService;
    private final ReviewService reviewService;

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
        User user = userService.getUserByEmail(principal.getName());
        Place place = placeService.findPlace(placeId);
        List<Review> reviews = reviewService.findReviewsByPlaceId(placeId);
        model.addAttribute("user", user);
        model.addAttribute("place", place);
        model.addAttribute("isFavourite", userService.getFavoritePlaces(user.getId()).contains(place));
        model.addAttribute("rating", reviewService.computePlaceRating(placeId));
        model.addAttribute("reviews", reviews);
        model.addAttribute("ratings", reviews.stream().map(Review::getRating).toList());
        return "user/places/place";
    }

    @GetMapping("/popular")
    public String getPopularPlacesPage(@RequestParam(value = "search", defaultValue = "") String filter,
            @RequestParam(value = "city", defaultValue = "Almaty") String city, Model model) {
        List<Place> places = placeService.findPopularsByCityAndFilter(city, filter);
        model.addAttribute("filter", filter);
        model.addAttribute("places", places);
        return "user/places/popular";
    }

    @GetMapping("/indoor")
    public String getIndoorPage(@RequestParam(value = "search", defaultValue = "") String filter,
                                       @RequestParam(value = "city", defaultValue = "Almaty") String city, Model model) {
        List<Place> places = placeService.findByLocationAndTypeAndFilter(city, PlaceType.INDOOR, filter);
        model.addAttribute("filter", filter);
        model.addAttribute("places", places);
        return "user/places/indoor";
    }

    @GetMapping("/outdoor")
    public String getOutdoorPage(@RequestParam(value = "search", defaultValue = "") String filter,
                                @RequestParam(value = "city", defaultValue = "Almaty") String city, Model model) {
        List<Place> places = placeService.findByLocationAndTypeAndFilter(city, PlaceType.OUTDOOR, filter);
        model.addAttribute("filter", filter);
        model.addAttribute("places", places);
        return "user/places/outdoor";
    }

    @PostMapping("/{placeId}/add-to-favourite")
    public String addToFavourite(@PathVariable Long placeId, Principal principal) {
        userService.addPlaceToFavorites(principal.getName(), placeId);
        return "redirect:/places/%d".formatted(placeId);
    }

    @PostMapping("/{placeId}/add-review")
    public String addReview(@PathVariable Long placeId, NewReviewPayload payload, Principal principal) {
        reviewService.addReviewToPlace(principal.getName(), placeId, payload);
        return "redirect:/places/%d".formatted(placeId);
    }
}
