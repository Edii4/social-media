package dev.edi.socialmedia;

import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

//@EnableWebSecurity
@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JwtService jwtService;

    //private BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder(12);

    public List<User> allUsers() {
        return userRepository.findAll();
    }

    public User registerUser(User user) {

        if(userRepository.findByEmail(user.getEmail()).isPresent()) {
            return null;
        }
        //String hashedPassword = bCryptPasswordEncoder.encode(user.getPassword());
        //user.setPassword(hashedPassword);

        return userRepository.save(user);
    }

    public Optional<String> login(String email, String password) {
        return userRepository.findByEmail(email)
                .filter(user -> password.equals(user.getPassword()))
                .map(user -> jwtService.generateToken(user.getEmail()));
    }
}
