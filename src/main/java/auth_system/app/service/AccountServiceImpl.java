package auth_system.app.service;

import java.util.Collections;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import auth_system.app.entities.Role;
import auth_system.app.entities.AppUser;
import auth_system.app.repository.RoleRepository;
import auth_system.app.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Transactional
@Service
public class AccountServiceImpl implements AccountService {

	private final UserRepository userRepo;
	private final RoleRepository roleRepo;
	private final PasswordEncoder passwordEncoder;
	
	@Override
	public AppUser addNewUser(String username, String password, String email, String confirmPassword) {
		
		
		if(userRepo.findByUsername(username) != null) throw new RuntimeException("this user existe Already");
		if(!password.equals(confirmPassword)) throw new RuntimeException("passwords not match");
		
	    Role userRole = roleRepo.findByRole("USER");
	  
	    AppUser appUser = AppUser.builder()
	            .username(username)
	            .password(passwordEncoder.encode(password))
	            .email(email)
	            .roles(Collections.singletonList(userRole)) // default role is USER
	            .build();
	    AppUser savedUser = userRepo.save(appUser);
		return savedUser;
	}

	@Override
	public Role addNewRole(String role) {
		Role Approle = roleRepo.findByRole(role);
		if(Approle != null) throw new RuntimeException("this role already exist");
		Approle = Role.builder() 
					.role(role)
					.build();
		return roleRepo.save(Approle);
	}

	@Override
	public void addRoleToUser(String username, String role) {
		AppUser appUser = userRepo.findByUsername(username);
		Role appRole = roleRepo.findByRole(role);
		appUser.getRoles().add(appRole);
	}

	@Override
	public void removeRoleFromUser(String username, String role) {
		AppUser appUser = userRepo.findByUsername(username);
		Role appRole = roleRepo.findByRole(role);
		appUser.getRoles().remove(appRole);
		
	}

	@Override
	public AppUser loadUserByUsername(String username) {
		return userRepo.findByUsername(username);
	}

}
