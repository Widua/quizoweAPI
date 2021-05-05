package me.widua.databaseauthorization.manager;

import me.widua.databaseauthorization.model.UserModel;
import me.widua.databaseauthorization.repository.UserRepository;
import me.widua.databaseauthorization.roles.Roles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class UserManager  {

    private final UserRepository repository ;
    private final PasswordEncoder passwordEncoder ;

    @Autowired
    public UserManager(UserRepository userRepository, PasswordEncoder passwordEncoder){
        this.repository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public Optional<UserModel> findUserByUsername(String username){
        return repository.getUserModelByUsername(username);
    }

    public void registerUser(UserModel user){
        repository.save(user);
    }

    public void promoteUser(UserModel user, Roles role){
        user.setAuthorities(role.getGrangedAuthorities());
        repository.save(user);
    }

    public void deleteUser(String username){
        if (repository.getUserModelByUsername(username).isPresent()){
            repository.delete(repository.getUserModelByUsername(username).get());
        } else {
            throw new UsernameNotFoundException(String.format("User %s does not exist", username));
        }
    }

    public void changePassword(UserModel user, String password){
        user.setPassword( passwordEncoder.encode( password ) );
        repository.save(user);
    }


    public Roles findRoleByName(String roleName){
        roleName = roleName.toUpperCase();
        switch (roleName){
            case "USER": return Roles.USER;
            case "QUIZADMIN": return Roles.QUIZADMIN;
            case "ADMINISTRATOR": return Roles.ADMINISTRATOR;
            default: return null;
        }
    }


}
