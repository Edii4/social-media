package dev.edi.socialmedia;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/posts")
public class PostController {
    @Autowired
    private PostService postService;

    @PostMapping
    public ResponseEntity<Post> create(@RequestBody Post post) {
        return ResponseEntity.ok(postService.createPost(post.getUserId(), post.getContent(), post.getImageUrl()));
    }

    @GetMapping
    public ResponseEntity<List<PostResponse>> getAll() {
        return ResponseEntity.ok(postService.getAllPosts());
    }

    @GetMapping("/users/{userId}")
    public ResponseEntity<List<PostResponse>> getByUser(@PathVariable String userId) {
        return ResponseEntity.ok(postService.getPostByUser(userId));
    }

    @GetMapping("/{postId}")
    public ResponseEntity<List<PostResponse>> getById(@PathVariable String id) {
        return ResponseEntity.ok(postService.getPostById(id));
    }

    @PostMapping("/{postId}/like")
    public ResponseEntity<Post> likePost(@PathVariable String postId, @RequestParam String userId) {
        return ResponseEntity.ok(postService.likePost(userId, postId));
    }

    @PostMapping("/{postId}/unlike")
    public ResponseEntity<Post> unlikePost(@PathVariable String postId, @RequestParam String userId) {
        return ResponseEntity.ok(postService.unlikePost(postId, userId));
    }
}
