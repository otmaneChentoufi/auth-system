package auth_system.app.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import auth_system.app.entities.AppUser;

@Repository
public interface UserRepository extends JpaRepository<AppUser, Long> {

	AppUser findByUsername(String username);
}