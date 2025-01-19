package auth_system.app.service;

import java.util.List;

import auth_system.app.entities.Formation;

public interface FormationService {
		
	 	Formation createFormation(Formation Formation);
	    Formation getFormationById(Long FormationId);
	    List<Formation> getAllFormations();
	    Formation updateFormation(Long FormationId, Formation Formation);
	    void deleteFormation(Long FormationId);
//	    void removeFormationFromUser(Long userId, Long FormationId);
	    List<Formation> getFormationsByUser(Long userId);
	    
}

