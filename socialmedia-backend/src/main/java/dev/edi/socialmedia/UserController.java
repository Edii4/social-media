package dev.edi.socialmedia;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/users")
public class UserController {
    @Autowired
    private UserService userService;

    @GetMapping
    public ResponseEntity<List<User>> getAllUsers() {
        return new ResponseEntity<List<User>>(userService.allUsers(), HttpStatus.OK);
    }

    @PostMapping("/register")
    public ResponseEntity<User> registerUser(@RequestBody User user) {
        User newUser = userService.registerUser(user);
        return ResponseEntity.ok(newUser);
    }

    @PostMapping("/login")
    public ResponseEntity<?> loginUser(@RequestBody LoginRequest request) {
        return userService.login(request.getEmail(), request.getPassword())
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.status(HttpStatus.UNAUTHORIZED).build());
    }

    @PostMapping("/{followerId}/follow/{followeeId}")
    public ResponseEntity<String> follow(@PathVariable String followerId, @PathVariable String followeeId) {
        userService.followUser(followerId, followeeId);
        return ResponseEntity.ok("Followed successfully");
    }

    @PostMapping("/{followerId}/unfollow/{followeeId}")
    public ResponseEntity<String> unfollow(@PathVariable String followerId, @PathVariable String followeeId) {
        userService.unfollowUser(followerId, followeeId);
        return ResponseEntity.ok("Unfollowed successfully");
    }

    @GetMapping("{userId}/following")
    public ResponseEntity<List<String>> getFollowing(@PathVariable String userId) {
        return new ResponseEntity<List<String>>(userService.followingUsers(userId), HttpStatus.OK);
    }

    @GetMapping("{userId}/followers")
    public ResponseEntity<List<String>> getFollowers(@PathVariable String userId) {
        return new ResponseEntity<List<String>>(userService.followers(userId), HttpStatus.OK);
    }
}
