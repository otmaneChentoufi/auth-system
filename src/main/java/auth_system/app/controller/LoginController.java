package auth_system.app.controller;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import auth_system.app.dto.AppUserDTO;
import auth_system.app.service.AccountService;
import auth_system.app.service.AccountServiceImpl;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Controller
public class LoginController {

    private final AccountServiceImpl accountService;

    @GetMapping("/login")
    public String login() {
        return "login"; // Return the login.html template
    }

    @GetMapping("/registration")
    public String register(Model model) {
        model.addAttribute("appUserDTO", new AppUserDTO());
        return "register";
    }
    
    @GetMapping("/access-denied")
    public String accessDenied() {
        return "access-denied"; // Returns the Thymeleaf template name
    }

    @GetMapping("/")
    public String index() {
        return "redirect:/login"; // Redirect to login page
    }

    @GetMapping("/product")
    public String productList() {
        return "product"; // Return the product.html template
    }

    @PostMapping("/register")
    public String registerUser(@Valid AppUserDTO registerDTO, BindingResult bindingResult, Model model) {
        // Check for validation errors
        if (bindingResult.hasErrors()) {
            model.addAttribute("errors", bindingResult.getAllErrors());
            return "register"; // Return to the registration page with errors
        }

        try {
            accountService.addNewUser(
                registerDTO.getUsername(),
                registerDTO.getPassword(),
                registerDTO.getEmail(),
                registerDTO.getConfirmPassword()
            );
            return "redirect:/login"; // Redirect on success
        } catch (RuntimeException e) {
            model.addAttribute("errorMessage", e.getMessage());
            return "register"; // Return to the registration page with custom errors
        }
    }

    @GetMapping("/home")
    public String home(Model model, Authentication authentication) { // Authentication is an object provided by Spring security
        if (authentication != null && authentication.isAuthenticated()) {
            UserDetails userDetails = (UserDetails) authentication.getPrincipal();
            model.addAttribute("username", userDetails.getUsername());
            model.addAttribute("roles", userDetails.getAuthorities());
        }
        return "home"; // Return the home.html template
    }
}
