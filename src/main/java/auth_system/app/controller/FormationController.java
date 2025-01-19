package auth_system.app.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import auth_system.app.entities.Formation;
import auth_system.app.service.FormationService;
import lombok.RequiredArgsConstructor;


@RequiredArgsConstructor
@Controller
@RequestMapping("/formations")
public class FormationController {
	
	private final FormationService formationService;
	
	@GetMapping("/list")
	public String getAllFormations(Model model) {
		List<Formation> formations = formationService.getAllFormations();
		model.addAttribute("formations", formations);
		return "formations/list";
	}
	
	@GetMapping("/formationFormView")
	public String getFormationFormView() {
		return "formations/form";
	}
	
	@PostMapping("/create")
	public String createFormation(@ModelAttribute("formation") Formation formation) {
		formationService.createFormation(formation);
		return "redirect:/formations/list" ;
	}
	
	@GetMapping("/{id}/edit")
	public String showEditClassForm(@PathVariable("id") Long formationId, Model model) {
	   Formation formation = formationService.getFormationById(formationId);
	   model.addAttribute("formation", formation);
	   return "formations/form"; // Points to form.html
	}
	
	  // Handle form submission for updating an existing class
    @PostMapping("/{id}/edit")
    public String updateClass(@PathVariable("id") Long formationId, @ModelAttribute("formation") Formation formation) {
        formationService.updateFormation(formationId, formation) ;
        return "redirect:/formations/list"; // Redirect to the list of classes
    }
    
    // Delete a class
    @GetMapping("/{id}/delete")
    public String deleteClass(@PathVariable("id") Long formationId) {
        formationService.deleteFormation(formationId);
        return "redirect:/formations/list"; 
    }
	
}
