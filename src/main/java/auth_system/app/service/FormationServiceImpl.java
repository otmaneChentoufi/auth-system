package auth_system.app.service;

import org.springframework.stereotype.Service;

import auth_system.app.entities.AppUser;
import auth_system.app.entities.Formation;
import auth_system.app.repository.FormationRepository;
import auth_system.app.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Transactional
@Service
public class FormationServiceImpl implements FormationService {

    private final FormationRepository formationRepository;

    private final UserRepository appUserRepository; // Assuming you have a repository for AppUser

    @Override
    public Formation createFormation(Formation formation) {
        return formationRepository.save(formation);
    }

    @Override
    public Formation getFormationById(Long formationId) {
        Optional<Formation> optionalFormation = formationRepository.findById(formationId);
        return optionalFormation.orElse(null); // Retourne null si non trouvé, ou utilisez une exception
    }

    @Override
    public List<Formation> getAllFormations() {
        return formationRepository.findAll();
    }

    @Override
    public Formation updateFormation(Long formationId, Formation formation) {
        if (formationRepository.existsById(formationId)) {
            formation.setFormationId(formationId);
            return formationRepository.save(formation);
        }
        return null;
    }

    @Override
    public void deleteFormation(Long formationId) {
       
    	Formation formation = formationRepository.findById(formationId).
    							orElseThrow(() -> new RuntimeException("Formation not Found"));
    	
    	List<AppUser> associatedUsers = appUserRepository.findByFormationContaining(formation);
    	for (AppUser user : associatedUsers) {
            user.getFormations().remove(formation);
            appUserRepository.save(user);
        }
    	
    	formationRepository.delete(formation);
    }


    @Override
    public List<Formation> getFormationsByUser(Long userId) {
        Optional<AppUser> optionalUser = appUserRepository.findById(userId);
        return optionalUser.map(AppUser::getFormations).orElse(null); // Retourne les formations ou null
    }
}
