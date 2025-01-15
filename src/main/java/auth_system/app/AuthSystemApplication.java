package auth_system.app;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import auth_system.app.service.AccountService;

@SpringBootApplication
public class AuthSystemApplication {

	public static void main(String[] args) {
		SpringApplication.run(AuthSystemApplication.class, args);
	}
	
	@Bean
	CommandLineRunner commandLineRunnerUserDetails(AccountService accountService) {
		return args->{
			
			accountService.addNewRole("USER");
			accountService.addNewRole("ADMIN");
			
			accountService.addNewUser("user 1", "1234", "user1@gmail.com", "1234");
			accountService.addNewUser("user 2", "12345", "user2@gmail.com", "12345");
			accountService.addNewUser("admin", "123456", "admin@gmail.com", "123456");
			
			accountService.addRoleToUser("admin", "ADMIN");

		};
	}
	
	@Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }	 

}
