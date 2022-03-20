package io.my.messagesystem.controller;

import io.my.messagesystem.dto.UserRegistrationDto;
import io.my.messagesystem.model.User;
import io.my.messagesystem.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class UserController {

    @Autowired
    private UserService userService;

    // Login page
    @GetMapping("/login")
    public String login() {
        return "login";
    }

    // Register
    @GetMapping("/register")
    public String showRegistrationForm(Model model) {
        UserRegistrationDto registrationDto = new UserRegistrationDto();
        model.addAttribute("user", registrationDto);
        return "register";
    }

    // Check if username already exists and register
    @PostMapping("/register")
    public String registerUserAccount(@ModelAttribute("user") UserRegistrationDto registrationDto) {
        User check = userService.findByUsername(registrationDto.getUsername());
        if (check == null) {
            userService.saveUser(registrationDto);
            return "redirect:/login?registered";
        } else {
            return "redirect:/register?existError";
        }
    }
}
