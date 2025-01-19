package auth_system.app.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import auth_system.app.entities.Classe;

@Repository
public interface ClasseRepository extends JpaRepository<Classe, Long> {
	Classe findClasseByClasseId(Long classeId);
}
