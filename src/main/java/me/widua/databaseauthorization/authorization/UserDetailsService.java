package me.widua.databaseauthorization.authorization;

import me.widua.databaseauthorization.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsService implements org.springframework.security.core.userdetails.UserDetailsService {

    private final UserRepository repository;

    @Autowired
    UserDetailsService (UserRepository repository){
        this.repository = repository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return repository.getUserModelByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException(String.format("User %s does not exist",username)));
    }
}
