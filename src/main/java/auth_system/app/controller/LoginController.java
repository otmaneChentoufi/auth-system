package auth_system.app.controller;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import auth_system.app.service.AccountService;
import auth_system.app.service.AccountServiceImpl;
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
    public String register() {
        return "register"; // Return the register.html template
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
    public String registerUser(@RequestParam String username,
                               @RequestParam String password,
                               @RequestParam String email,
                               @RequestParam String confirmePassword) { // Fixed spelling
    	System.out.println(email);
        try {
            accountService.addNewUser(username, password, email, confirmePassword);
            return "redirect:/login"; // Redirect to login page after successful registration
        } catch (RuntimeException e) {
        	System.out.println(e.getMessage());
            // Handle registration errors (e.g., user already exists, password mismatch)
            return "register"; // Return to registration page with an error message
        }
    }

    @GetMapping("/home")
    public String home(Model model, Authentication authentication) {
        if (authentication != null && authentication.isAuthenticated()) {
            UserDetails userDetails = (UserDetails) authentication.getPrincipal();
            model.addAttribute("username", userDetails.getUsername());
            model.addAttribute("roles", userDetails.getAuthorities());
        }
        return "home"; // Return the home.html template
    }
}
