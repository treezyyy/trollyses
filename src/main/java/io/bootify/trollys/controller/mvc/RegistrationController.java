/*package io.bootify.trollys.controller.mvc;

import io.bootify.trollys.dto.UserDTO;
import io.bootify.trollys.service.UserServiceImpl;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@AllArgsConstructor
@RequestMapping("/registration")
public class RegistrationController {

    private final UserServiceImpl userService;

    @GetMapping
    public String registrationForm(Model model) {
        model.addAttribute("userDTO", new UserDTO());
        return "registration";
    }

    @PostMapping
    public String registerUser(UserDTO userDTO, Model model) {
        try {
            userService.saveUser(userDTO);
            return "redirect:/login";
        } catch (Exception e) {
            model.addAttribute("error", "Registration failed: " + e.getMessage());
            return "registration";
        }
    }
}*/
