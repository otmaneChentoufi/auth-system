package auth_system.app.controller;

import java.util.List;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import auth_system.app.entities.Classe;
import auth_system.app.service.ClasseService;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Controller
@RequestMapping("/classes")
public class ClasseController {

    
    private final ClasseService classeService;

    // Display the list of classes
    @GetMapping("/list")
    public String getAllClasses(Model model, Authentication authentication) {
    	
    	 if (authentication != null && authentication.isAuthenticated()) {
    	        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
    	
		    	// Check if the user is an admin
		        if (userDetails.getAuthorities().stream()
		                .anyMatch(grantedAuthority -> grantedAuthority.getAuthority().equals("ROLE_ADMIN"))) {
		          // Fetch all classes for admin
		          List<Classe> classes = classeService.getAllClasses();
		          model.addAttribute("classes", classes);
		        } else {
		            // Fetch classes and formations for the connected user
		            List<Classe> classes = classeService.getClassesByUser(userDetails.getUsername());
		            model.addAttribute("classes", classes);		
		        }
    	 }

        return "classes/list";
    }
    
    @GetMapping("/createClasseView")
    public String createClasseView() {
    	return "classes/form";
    }
    
 // Handle form submission for creating a new class
    @PostMapping("/create")
    public String createClass(@ModelAttribute("classe") Classe classe) {
        classeService.createClass(classe);
        return "redirect:/classes/list"; // Redirect to the list of classes
    }
    
    // Display the form for editing an existing class
    @GetMapping("/{id}/edit")
    public String showEditClassForm(@PathVariable("id") Long classeId, Model model) {
        Classe classe = classeService.getClassById(classeId);
        model.addAttribute("classe", classe);
        return "classes/form"; // Points to form.html
    }
    
    // Handle form submission for updating an existing class
    @PostMapping("/{id}/edit")
    public String updateClass(@PathVariable("id") Long classeId, @ModelAttribute("classe") Classe classe) {
        classeService.updateClass(classeId, classe);
        return "redirect:/classes/list"; // Redirect to the list of classes
    }
    
    // Delete a class
    @GetMapping("/{id}/delete")
    public String deleteClass(@PathVariable("id") Long classeId) {
        classeService.deleteClass(classeId);
        return "redirect:/classes/list"; 
    }
}