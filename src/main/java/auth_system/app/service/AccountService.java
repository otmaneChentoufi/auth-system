package auth_system.app.service;

import auth_system.app.entities.Role;
import auth_system.app.entities.AppUser;

public interface AccountService {
	
	AppUser addNewUser(String username, String password, String email, String confirmPassword);
	Role addNewRole(String role);
	AppUser loadUserByUsername(String username);
	
	void addRoleToUser(String username, String role);
	void removeRoleFromUser(String username, String role);
    
	void assignClassToUser(String username, Long classeId);
    void assignFormationToUser(String username, Long formationId);


}
