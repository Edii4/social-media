package dev.edi.socialmedia;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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
                    user != null ? user.getProfilePicUrl() : null
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
                    user != null ? user.getProfilePicUrl() : null
            );
        }).toList();
    }
}
