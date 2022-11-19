package budgetsolve.security;

import budgetsolve.model.User;
import budgetsolve.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.HashSet;

@Service
public class UserDetailsServiceAdapter implements UserDetailsService {
    @Autowired
    UserRepository userRepository;

    public UserDetailsServiceAdapter() {}

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        try{
            User user = userRepository.getUserByEmail(email);
            return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(), true, true, true, true, new HashSet<>());
        } catch (Exception ex){
            throw new UsernameNotFoundException("User Not Found with username: " + email);
        }
    }

}