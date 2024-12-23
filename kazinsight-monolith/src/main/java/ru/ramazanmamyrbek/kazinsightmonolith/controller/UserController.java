package ru.ramazanmamyrbek.kazinsightmonolith.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;
import ru.ramazanmamyrbek.kazinsightmonolith.controller.payload.NewUserPayload;
import ru.ramazanmamyrbek.kazinsightmonolith.entity.Place;
import ru.ramazanmamyrbek.kazinsightmonolith.entity.Tour;
import ru.ramazanmamyrbek.kazinsightmonolith.entity.enums.PlaceType;
import ru.ramazanmamyrbek.kazinsightmonolith.service.PlaceService;
import ru.ramazanmamyrbek.kazinsightmonolith.service.TourService;
import ru.ramazanmamyrbek.kazinsightmonolith.service.UserService;

import java.security.Principal;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
    private final PlaceService placeService;
    private final TourService tourService;
    private static final int TOP = 3;

    @GetMapping("/home")
    public String homePage() {
        return "user/index";
    }

    @GetMapping("/auth/login-page")
    public String getLoginPage() {

        return "user/login";
    }

    @GetMapping("/auth/register")
    public String registerPage(@ModelAttribute("user")NewUserPayload newUserPayload) {
        return "user/auth/register";
    }

    @PostMapping("/auth/register")
    public String register(@Valid NewUserPayload newUserPayload, Model model, BindingResult bindingResult) {
        if(bindingResult.hasErrors()) {
            model.addAttribute("errors", bindingResult.getAllErrors()
                    .stream()
                    .map(ObjectError::getDefaultMessage)
                    .toList());
            model.addAttribute("user", newUserPayload);
            return "user/auth/register";
        }
        userService.saveUser(newUserPayload);
        return "redirect:/home";
    }

    @GetMapping("/users/profile")
    public String profilePage(Principal principal, Model model) {
        model.addAttribute("user", userService.getUserByEmail(principal.getName()));
        return "user/profile/profile";
    }

    @PatchMapping("/users/{userId}/balance/replenish")
    public String replenishBalance(@PathVariable Long userId,
                                @RequestParam Double value,
                                Model model) {
        userService.replenishBalance(userId, value);
        return "redirect:/users/profile";
    }

    @PatchMapping("/users/{userId}/balance/reset")
    public String resetBalance(@PathVariable Long userId,
                                Model model) {
        userService.resetBalance(userId);
        return "redirect:/users/profile";
    }

    @GetMapping("/users/{userId}/favorites")
    public String getFavorites(@PathVariable Long userId,
                               Model model) {
        List<Place> favorites = userService.getFavoritePlaces(userId);
        model.addAttribute("favorites", favorites);
        return "user/places/favorites";
    }

    @PostMapping("/users/favorites/{placeId}/add")
    public String addToFavorites(@PathVariable Long placeId,
                                 Principal principal) {
        userService.addPlaceToFavorites(principal.getName(),placeId);
        return "redirect:/places/%d".formatted(placeId);
    }
    @DeleteMapping("/users/favorites/{placeId}/remove")
    public String removeFromFavorites(@PathVariable Long placeId,
                                      Principal principal) {
        userService.removePlaceFromFavorites(principal.getName(), placeId);
        return "redirect:/places/%d".formatted(placeId);
    }

    @GetMapping("/users/{userId}/tours")
    public String getTours(@PathVariable Long userId,
                               Model model) {
        List<Tour> tours = userService.getTours(userId);
        model.addAttribute("tours", tours);
        return "user/tours/user-tours";
    }
    @PostMapping("/users/tours/{tourId}/add")
    public String addToMyTours(@PathVariable Long tourId,
                                 Principal principal) {
        userService.addTourToMyTours(principal.getName(),tourId);
        return "redirect:/tours/%d".formatted(tourId);
    }
    @DeleteMapping("/users/tours/{tourId}/remove")
    public String removeFromMyTours(@PathVariable Long tourId,
                                      Principal principal) {
        userService.removeTourFromMyTours(principal.getName(), tourId);
        return "redirect:/tours/%d".formatted(tourId);
    }

    @GetMapping("/users/main")
    public String getMainPage(@RequestParam(name = "city", defaultValue = "Almaty") String city, Model model) {
        List<Tour> tours = tourService.findTopForCity(city, TOP);
        model.addAttribute("tours", tours);

        List<Place> populars = placeService.findTopPopularByCity(city, TOP);
        model.addAttribute("populars", populars);

        List<Place> indoor = placeService.findTopForCityByPlaceType(city, PlaceType.INDOOR, TOP);
        model.addAttribute("indoors", indoor);

        List<Place> outdoor = placeService.findTopForCityByPlaceType(city, PlaceType.OUTDOOR, TOP);
        model.addAttribute("outdoors", outdoor);
        return "user/main";
    }

    @GetMapping("/users/favourites")
    public String getFavouritesPlace(@RequestParam(name = "filter", defaultValue = "") String filter,
                                     @RequestParam(name = "city", defaultValue = "Almaty") String city,
                                     Principal principal,
                                     Model model) {
        List<Tour> tours = tourService.findFavouriteToursByCityAndUsername(city, principal.getName(), filter);
        List<Place> places = placeService.findFavouriteToursByCityAndUsername(city, principal.getName(), filter);

        model.addAttribute("tours", tours);
        model.addAttribute("places", places);
        model.addAttribute("filter", filter);

        return "user/favourites";
    }

    @GetMapping("/users/my-tours")
    public String getUserTours(@RequestParam(name = "filter", defaultValue = "") String filter,
                               Principal principal, Model model) {
        List<Tour> tours = tourService.findToursAndParticipantName(filter, principal.getName());
        model.addAttribute("tours", tours);
        model.addAttribute("filter", filter);
        return "user/tours/my-tours";
    }
}
