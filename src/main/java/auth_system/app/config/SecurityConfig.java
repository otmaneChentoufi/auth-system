package auth_system.app.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

import auth_system.app.service.UserDetailServiceImlp;
import lombok.RequiredArgsConstructor;


@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {
	
	private final UserDetailServiceImlp userDetailServiceImpl;

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .csrf(csrf -> csrf.disable()) // Disable CSRF for simplicity; enable in production with proper config
            .formLogin(form -> form
                .loginPage("/login") // Specify a custom login page
                .defaultSuccessUrl("/home") // Redirect to home after login
                .permitAll() // Allow everyone to access the login page
            )
            .logout(logout -> logout
                .logoutUrl("/logout") // Specify a logout endpoint
                .logoutSuccessUrl("/login") // Redirect to login on logout success
                .invalidateHttpSession(true) // Invalidate the session on logout
                .deleteCookies("JSESSIONID") // Delete session cookies
            )
            .userDetailsService(userDetailServiceImpl)
            .authorizeHttpRequests(authorize -> authorize
                    .requestMatchers("/product/**","/registration", "/register").hasRole("ADMIN")// Match URLs for ADMIN role
                    .anyRequest().authenticated() // All other requests require authentication
             )
            .exceptionHandling(exceptionHandling -> 
                    exceptionHandling.accessDeniedPage("/access-denied") // Set the access denied page
        );
        
        return http.build();
    }
}