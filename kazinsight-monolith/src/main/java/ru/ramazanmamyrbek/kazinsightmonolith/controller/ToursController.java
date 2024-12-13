package ru.ramazanmamyrbek.kazinsightmonolith.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import ru.ramazanmamyrbek.kazinsightmonolith.controller.payload.NewReviewPayload;
import ru.ramazanmamyrbek.kazinsightmonolith.entity.Review;
import ru.ramazanmamyrbek.kazinsightmonolith.entity.Tour;
import ru.ramazanmamyrbek.kazinsightmonolith.entity.User;
import ru.ramazanmamyrbek.kazinsightmonolith.exception.BalanceNotEnoughException;
import ru.ramazanmamyrbek.kazinsightmonolith.service.ReviewService;
import ru.ramazanmamyrbek.kazinsightmonolith.service.TourService;
import ru.ramazanmamyrbek.kazinsightmonolith.service.UserService;

import java.security.Principal;
import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/tours")
public class ToursController {
    private final TourService tourService;
    private final UserService userService;
    private final ReviewService reviewService;

    @GetMapping
    public String getAllToursPage(@RequestParam(value = "search", defaultValue = "") String filter,
                              @RequestParam(value = "city", defaultValue = "Almaty") String city,
                              Model model) {
        model.addAttribute("tours", tourService.findAllByCity(filter, city));
        model.addAttribute("filter", filter);
        return "user/tours/tours";
    }

    @GetMapping("/{tourId}")
    public String getTourPage(@PathVariable Long tourId,
                              Model model,
                              Principal principal,
                              @RequestParam(value = "successfulTransaction", defaultValue = "false") boolean successfulTransaction,
                              @RequestParam(value = "errorTransaction", defaultValue = "false") boolean errorTransaction) {
        User user = userService.getUserByEmail(principal.getName());
        Tour tour = tourService.findTour(tourId);
        List<Review> reviews = reviewService.findReviewsByTourId(tourId);
        model.addAttribute("tour", tour);
        model.addAttribute("user", user);
        model.addAttribute("rating", tourService.computeRating(tourId));
        model.addAttribute("isFavourite", userService.getFavoriteTours(user.getId()).contains(tour));
        model.addAttribute("reviews", reviews);
        model.addAttribute("ratings", reviews.stream().map(Review::getRating).toList());
        model.addAttribute("successfulTransaction", successfulTransaction);
        model.addAttribute("errorTransaction", errorTransaction);
        return "user/tours/tour";
    }

    @PostMapping("/{tourId}/add-to-favourite")
    public String addToFavourite(@PathVariable Long tourId, Principal principal) {
        userService.addTourToFavourites(principal.getName(), tourId);
        return "redirect:/tours/%d".formatted(tourId);
    }

    @PostMapping("/{tourId}/buy")
    public String buyTour(@PathVariable Long tourId, Principal principal, RedirectAttributes redirectAttributes) {
        try {
            userService.addTourToMyTours(principal.getName(), tourId);
            redirectAttributes.addAttribute("successfulTransaction", true);
        } catch (BalanceNotEnoughException ex) {
            redirectAttributes.addAttribute("errorTransaction", true);
        }
        return "redirect:/tours/%d".formatted(tourId);
    }

    @PostMapping("/{tourId}/add-review")
    public String addReview(@PathVariable Long tourId, NewReviewPayload payload, Principal principal) {
        reviewService.addReviewToTour(principal.getName(), tourId, payload);
        return "redirect:/tours/%d".formatted(tourId);
    }
}
