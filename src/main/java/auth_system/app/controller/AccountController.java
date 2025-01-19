package auth_system.app.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import auth_system.app.entities.AppUser;
import auth_system.app.entities.Classe;
import auth_system.app.entities.Formation;
import auth_system.app.repository.ClasseRepository;
import auth_system.app.repository.FormationRepository;
import auth_system.app.service.AccountService;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Controller
@RequestMapping("/accounts")
public class AccountController {
	
	private final AccountService accountService;
	private final ClasseRepository classeRepository;
	private final FormationRepository formationRepository;
	
		@GetMapping("/list")
		 public String getAllUsers(Model model) {
	        List<AppUser> accounts = accountService.getAllUsers();
	        model.addAttribute("accounts", accounts);
	        return "accounts/list";
	    }	
	
	
	   @GetMapping("/delete/{userId}")
	   public String deleteUser(@PathVariable Long userId) {
	       accountService.removeUser(userId);
	       return "redirect:/accounts/list"; // Redirect to the user list page or wherever appropriate
	   }
	   
	    //----------------------------------- CLASSES --------------------------------------------------------

	   
	   @GetMapping("/{userId}/classes")
	    public String loadUserClassesById(@PathVariable Long userId, Model model) {
		   AppUser account = accountService.loadUserById(userId);
		    List<Classe> availableClasses = classeRepository.findAll(); // Assuming you have a repository for Classe
		    model.addAttribute("account", account);
		    model.addAttribute("userId",userId);
		    model.addAttribute("availableClasses", availableClasses);
		    return "accounts/accountClasses";
	    }
	   
	   
	   @PostMapping("/{userId}/assignClass")
	   public String assignClassToUser(@PathVariable Long userId, @RequestParam Long classId) {
	       accountService.assignClasseToUser(userId, classId);
	       return "redirect:/accounts/" + userId + "/classes"; // Redirect to user's classes page
	   }

	    @GetMapping("/delete/classes/{userId}/{classeId}")
	    public String removeClasseFromUser(@PathVariable Long userId, @PathVariable Long classeId) {
	        accountService.removeClasseFromUser(userId, classeId);
	        return "redirect:/accounts/" + userId + "/classes"; // Redirect to the user's classes
	    }
	    
	    //----------------------------------- FORMATIONS --------------------------------------------------------
	    
	    @GetMapping("/{userId}/formations")
	    public String loadUserformationsById(@PathVariable Long userId, Model model) {
	    	AppUser account = accountService.loadUserById(userId);
		    List<Formation> availableformations = formationRepository.findAll(); // Assuming you have a repository for formation
		    model.addAttribute("account", account);
		    model.addAttribute("userId",userId);
		    model.addAttribute("availableformations", availableformations);
		    return "accounts/accountFormations";
	    }
	   
	   
	   @PostMapping("/{userId}/assignFormation")
	   public String assignFormationToUser(@PathVariable Long userId, @RequestParam Long formationId) {
	       accountService.assignFormationToUser(userId, formationId);
	       return "redirect:/accounts/" + userId + "/formations"; // Redirect to user's formations page
	   }

	    @GetMapping("/delete/formations/{userId}/{formationId}")
	    public String removeformationFromUser(@PathVariable Long userId, @PathVariable Long formationId) {
	        accountService.removeFormationFromUser(userId, formationId);
	        return "redirect:/accounts/" + userId + "/formations"; // Redirect to the user's formations
	    }
	    
	    
	    
	    
	    
}
