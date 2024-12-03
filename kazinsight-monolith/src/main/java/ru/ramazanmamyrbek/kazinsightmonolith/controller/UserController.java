package ru.ramazanmamyrbek.kazinsightmonolith.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import ru.ramazanmamyrbek.kazinsightmonolith.controller.payload.NewUserPayload;
import ru.ramazanmamyrbek.kazinsightmonolith.service.UserService;

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
}
