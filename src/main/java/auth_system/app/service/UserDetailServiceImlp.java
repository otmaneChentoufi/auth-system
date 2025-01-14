package auth_system.app.service;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.security.core.userdetails.User;

import auth_system.app.entities.AppUser;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserDetailServiceImlp implements UserDetailsService  {
	
	private final AccountService accountService;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		
		AppUser appUser = accountService.loadUserByUsername(username);
		if(appUser==null) throw new UsernameNotFoundException(String.format("AppUser %s not found", username));
		
		UserDetails userDetails = User.withUsername(appUser.getUsername())
									.password(appUser.getPassword())
									.roles(appUser.getRoles().stream().map(
											u->u.getRole()).toArray(String[]::new))
									.build();
		
		return userDetails;
	}

}
