package uz.pdp.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import uz.pdp.demo.entity.User;
import uz.pdp.demo.repository.UserRepository;

import java.util.Optional;

@Service
public class MyAuthService implements UserDetailsService {

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Optional<User> optionalUser = userRepository.findByEmail(email);
        return optionalUser.orElse(null);
    }
}
