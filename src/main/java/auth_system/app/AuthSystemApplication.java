package auth_system.app;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import auth_system.app.entities.Classe;
import auth_system.app.entities.Formation;
import auth_system.app.service.AccountService;
import auth_system.app.service.ClasseService;
import auth_system.app.service.FormationService;

@SpringBootApplication
public class AuthSystemApplication {

	public static void main(String[] args) {
		SpringApplication.run(AuthSystemApplication.class, args);
	}
	
	@Bean
	CommandLineRunner commandLineRunnerUserDetails(AccountService accountService, ClasseService classeService, FormationService formationService) {
		return args->{
			
			accountService.addNewRole("USER");
			accountService.addNewRole("ADMIN");
			
			// Example classes to be assigned
			Classe classe1 = new Classe();
			classe1.setName("Math 101");
			classe1.setDescription("Introduction to Mathematics");
			classe1.setNombreHeurs(40);

			Classe classe2 = new Classe();
			classe2.setName("Science 101");
			classe2.setDescription("Introduction to Science");
			classe2.setNombreHeurs(40);
			
			Formation formation1 = Formation.builder()
								.name("Formation 1")
								.description("Formation 1 Description")
								.build();
			
			Formation formation2 = Formation.builder()
								.name("Formation 2")
								.description("Formation 2 Description")
								.build();
			
			
			formationService.createFormation(formation1);
			formationService.createFormation(formation2);

			
			// Save classes to the database (make sure you have a ClasseService to do this)
			classeService.createClass(classe1);
			classeService.createClass(classe2);

			// Create users and assign roles
			accountService.addNewUser("user 1", "1234", "user1@gmail.com", "1234");
			accountService.addNewUser("user 2", "12345", "user2@gmail.com", "12345");
			accountService.addNewUser("admin", "123456", "admin@gmail.com", "123456");

			accountService.addRoleToUser("user 1", "USER");
			accountService.addRoleToUser("user 2", "USER");
			accountService.addRoleToUser("admin", "ADMIN");

			// Assign classes to users
			accountService.assignClassToUser("user 1", classe1.getClasseId());
			accountService.assignClassToUser("user 2", classe2.getClasseId());
			
			accountService.assignFormationToUser("user 1", formation1.getFormationId());
			accountService.assignFormationToUser("user 1", formation2.getFormationId());
						
		};
	}
	
	@Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }	 

}
