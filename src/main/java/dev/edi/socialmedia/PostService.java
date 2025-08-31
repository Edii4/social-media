package dev.edi.socialmedia;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PostService {
    @Autowired
    private PostRepository postRepository;

    @Autowired
    private UserRepository userRepository;

    public Post createPost(String userId, String content, String imageUrl) {
        Post post = new Post();
        post.setUserId(userId);
        post.setContent(content);
        post.setImageUrl(imageUrl);
        return postRepository.save(post);
    }

    public List<PostResponse> getPostByUser(String userId) {
        List<Post> posts = postRepository.findByUserId(userId);

        return posts.stream().map(post -> {
            User user = userRepository.findById(post.getUserId()).orElse(null);
            return new PostResponse(
                    post.getId(),
                    post.getContent(),
                    post.getImageUrl(),
                    post.getCreatedAt(),
                    user != null ? user.getUsername() : "Unknown",
                    user != null ? user.getProfilePicUrl() : null,
                    post.getLikes().size(),
                    false
            );
        }).toList();
    }

    public List<PostResponse> getPostById(String id) {
        Optional<Post> posts = postRepository.findById(id);

        return posts.stream().map(post -> {
            User user = userRepository.findById(post.getUserId()).orElse(null);
            return new PostResponse(
                    post.getId(),
                    post.getContent(),
                    post.getImageUrl(),
                    post.getCreatedAt(),
                    user != null ? user.getUsername() : "Unknown",
                    user != null ? user.getProfilePicUrl() : null,
                    post.getLikes().size(),
                    false
            );
        }).toList();
    }

    public List<PostResponse> getAllPosts() {
        List<Post> posts = postRepository.findAll();

        return posts.stream().map(post -> {
            User user = userRepository.findById(post.getUserId()).orElse(null);
            return new PostResponse(
                    post.getId(),
                    post.getContent(),
                    post.getImageUrl(),
                    post.getCreatedAt(),
                    user != null ? user.getUsername() : "Unknown",
                    user != null ? user.getProfilePicUrl() : null,
                    post.getLikes().size(),
                    false
            );
        }).toList();
    }

    public Post likePost(String userId, String postId) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new RuntimeException("Post not found"));

        boolean alreadyLiked = post.getLikes().stream()
                .anyMatch(like -> like.getUserId().equals(userId));

        if(!alreadyLiked) {
            Like like = new Like();
            like.setUserId(userId);
            post.getLikes().add(like);
            return postRepository.save(post);
        }
        return post;
    }

    public Post unlikePost(String userId, String postId) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new RuntimeException("Post not found"));

        post.getLikes().removeIf(like -> like.getUserId().equals(userId));

        return postRepository.save(post);
    }
}
