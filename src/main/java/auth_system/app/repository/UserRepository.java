package auth_system.app.repository;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import auth_system.app.entities.AppUser;
import auth_system.app.entities.Classe;
import auth_system.app.entities.Formation;

@Repository
public interface UserRepository extends JpaRepository<AppUser, Long> {
	AppUser findByUsername(String username);
	
	@Query("SELECT u FROM AppUser u JOIN u.classes c WHERE c = :classe")
	List<AppUser> findByClassesContaining(@Param("classe") Classe classe);
	
	@Query("SELECT u FROM AppUser u JOIN u.formations c WHERE c = :formation")
	List<AppUser> findByFormationContaining(@Param("formation") Formation formation);
}