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
import ru.ramazanmamyrbek.kazinsightmonolith.service.UserService;

import java.security.Principal;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
    @GetMapping("/home")
    public String homePage() {
        return "user/home";
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
        List<Place> favorites = userService.getFavorites(userId);
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
}
