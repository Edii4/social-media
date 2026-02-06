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

    public void followUser(String followerId, String followeeId) {
        if (followerId.equals(followeeId)) return; // cannot follow self

        User follower = userRepository.findById(followerId)
                .orElseThrow(() -> new RuntimeException("Follower not found"));

        User followee = userRepository.findById(followeeId)
                .orElseThrow(() -> new RuntimeException("User to follow not found"));

        if (!follower.getFollowing().contains(followeeId)) {
            follower.getFollowing().add(followeeId);
        }

        if (!followee.getFollowers().contains(followerId)) {
            followee.getFollowers().add(followerId);
        }

        userRepository.save(follower);
        userRepository.save(followee);
    }

    public void unfollowUser(String followerId, String followeeId) {
        User follower = userRepository.findById(followerId)
                .orElseThrow(() -> new RuntimeException("Follower not found"));

        User followee = userRepository.findById(followeeId)
                .orElseThrow(() -> new RuntimeException("User to unfollow not found"));

        follower.getFollowing().remove(followeeId);
        followee.getFollowers().remove(followerId);

        userRepository.save(follower);
        userRepository.save(followee);
    }

    public List<String> followingUsers(String userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        return user.getFollowing();
    }
    public List<String> followers(String userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        return user.getFollowers();
    }
}
