package auth_system.app.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import auth_system.app.entities.AppUser;
import auth_system.app.entities.Classe;
import auth_system.app.repository.ClasseRepository;
import auth_system.app.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.Optional;


@RequiredArgsConstructor
@Transactional
@Service
public class ClasseServiceImpl implements ClasseService {

    @Autowired
    private ClasseRepository classeRepository;

    @Autowired
    private UserRepository appUserRepository; // Assuming you have a repository for AppUser

    @Override
    public Classe createClass(Classe classe) {
        return classeRepository.save(classe);
    }

    @Override
    public Classe getClassById(Long classeId) {
        Optional<Classe> optionalClasse = classeRepository.findById(classeId);
        return optionalClasse.orElse(null); // Return null or handle as appropriate
    }

    @Override
    public List<Classe> getAllClasses() {
        return classeRepository.findAll();
    }

    @Override
    public Classe updateClass(Long classeId, Classe classe) {
        if (classeRepository.existsById(classeId)) {
            classe.setClasseId(classeId); // Set the ID to update the existing class
            return classeRepository.save(classe);
        }
        return null; // Handle not found case as appropriate
    }

    @Override
    public void deleteClass(Long classeId) {
    
    	Classe classe = classeRepository.findById(classeId)
    						.orElseThrow(() -> new RuntimeException("Classe not found"));

        List<AppUser> associatedUsers = appUserRepository.findByClassesContaining(classe);
        for (AppUser user : associatedUsers) {
            user.getClasses().remove(classe);
            appUserRepository.save(user);
        }
        classeRepository.delete(classe);
    }

    @Override
    public void removeClassFromUser(Long userId, Long classeId) {
        Optional<AppUser> optionalUser = appUserRepository.findById(userId);
        if (optionalUser.isPresent()) {
            AppUser user = optionalUser.get();
            user.getClasses().removeIf(classe -> classe.getClasseId().equals(classeId)); // Remove class from user's class list
            appUserRepository.save(user); // Save updated user
        }
        // Optionally handle case where user was not found
    }

    @Override
    public List<Classe> getClassesByUser(Long userId) {
        Optional<AppUser> optionalUser = appUserRepository.findById(userId);
        return optionalUser.map(AppUser::getClasses).orElse(null);
    }
}
