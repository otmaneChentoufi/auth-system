package auth_system.app.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import auth_system.app.entities.Formation;

@Repository
public interface FormationRepository extends JpaRepository<Formation, Long> {
	Formation findFormationByFormationId(Long formationId);
}
