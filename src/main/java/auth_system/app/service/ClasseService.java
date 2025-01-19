package auth_system.app.service;

import java.util.List;

import auth_system.app.entities.Classe;

public interface ClasseService {
	
	 	Classe createClass(Classe classe);
	    Classe getClassById(Long classeId);
	    List<Classe> getAllClasses();
	    List<Classe> getClassesByUser(String username); 
	    Classe updateClass(Long classeId, Classe classe);
	    void deleteClass(Long classeId);
	    void removeClassFromUser(Long userId, Long classeId);
	    List<Classe> getClassesByUser(Long userId);
	    
}
