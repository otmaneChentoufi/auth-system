package auth_system.app.service;

import auth_system.app.entities.Role;

import java.util.List;

import auth_system.app.entities.AppUser;

public interface AccountService {
	
	AppUser addNewUser(String username, String password, String email, String role);
	Role addNewRole(String role);
	AppUser loadUserById(Long userId);
	AppUser loadUserByUsername(String username);
	List<AppUser> getAllUsers();
	void removeUser(Long userId);
	void addRoleToUser(String username, String role);
	void removeRoleFromUser(String username, String role);
    
	void assignClasseToUser(Long accountId, Long formationId);
	void assignFormationToUser(Long formationdId, Long formationId);
	
    
    void removeClasseFromUser(Long userId, Long classeId);
    void removeFormationFromUser(Long userId, Long classeId);


}
