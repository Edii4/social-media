package dev.edi.socialmedia;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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

            //boolean likedByCurrentUser = post.getLikes().stream().anyMatch(like -> like.getUserId().equals(currenUserId));

            return new PostResponse(
                    post.getId().toHexString(),
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

    public List<PostResponse> getPostByFollowingUsers(String userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));
        List<String> followingIds = user.getFollowing();

        List<Post> posts = postRepository.findAll().stream()
                .filter(post -> followingIds.contains(post.getUserId()))
                .sorted((a, b) -> b.getCreatedAt().compareTo(a.getCreatedAt()))
                .collect(Collectors.toList());

        return posts.stream().map(post -> {
            User postUser = userRepository.findById(post.getUserId()).orElse(null);
            return new PostResponse(
                    post.getId().toHexString(),
                    post.getContent(),
                    post.getImageUrl(),
                    post.getCreatedAt(),
                    postUser != null ? postUser.getUsername() : "Unknown",
                    postUser != null ? postUser.getProfilePicUrl() : null,
                    post.getLikes().size(),
                    false
            );
        }).toList();
    }

    public List<PostResponse> getLikedPosts(String userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));
        List<Post> likedPosts = postRepository.findAll().stream()
                .filter(post -> post.getLikes().stream()
                        .anyMatch(like -> like.getUserId().equals(userId)))
                .sorted((a, b) -> b.getCreatedAt().compareTo(a.getCreatedAt())) // newest first
                .collect(Collectors.toList());

        return likedPosts.stream().map(post -> {
            User postUser = userRepository.findById(post.getUserId()).orElse(null);

            return new PostResponse(
                    post.getId().toHexString(),
                    post.getContent(),
                    post.getImageUrl(),
                    post.getCreatedAt(),
                    postUser != null ? postUser.getUsername() : "Unknown",
                    postUser != null ? postUser.getProfilePicUrl() : null,
                    post.getLikes().size(),
                    true // likedByCurrentUser is always true here
            );
        }).toList();
    }

    public PostResponse getPostById(String id) {
        Post post = postRepository.findById(new ObjectId(id))
                .orElseThrow(() -> new RuntimeException("Post not found"));

        User user = userRepository.findById(post.getUserId()).orElse(null);

        return new PostResponse(
                post.getId().toHexString(),
                post.getContent(),
                post.getImageUrl(),
                post.getCreatedAt(),
                user != null ? user.getUsername() : "Unknown",
                user != null ? user.getProfilePicUrl() : null,
                post.getLikes().size(),
                false
        );
    }

    public List<PostResponse> getAllPosts(String currentUserId) {
        List<Post> posts = postRepository.findAll();

        return posts.stream().map(post -> {
            User user = userRepository.findById(post.getUserId()).orElse(null);

            boolean likedByCurrentUser = false;
            if(currentUserId != null) {
                likedByCurrentUser = post.getLikes().stream().anyMatch(like -> like.getUserId().equals(currentUserId));
            }
            System.out.println(currentUserId);
            System.out.println("liked: " + likedByCurrentUser);

            return new PostResponse(
                    post.getId().toHexString(),
                    post.getContent(),
                    post.getImageUrl(),
                    post.getCreatedAt(),
                    user != null ? user.getUsername() : "Unknown",
                    user != null ? user.getProfilePicUrl() : null,
                    post.getLikes().size(),
                    likedByCurrentUser
            );
        }).toList();
    }

    public Post likePost(String userId, String postId) {
        System.out.println("Trying to like post: " + postId + " by user: " + userId);
        ObjectId objectId = new ObjectId(postId);

        Post post = postRepository.findById(objectId)
                .orElseThrow(() -> new RuntimeException("Post not found"));

        boolean alreadyLiked = post.getLikes().stream()
                .anyMatch(like -> like.getUserId().equals(userId));

        if (!alreadyLiked) {
            Like like = new Like();
            like.setUserId(userId);
            post.getLikes().add(like);
            return postRepository.save(post);
        }

        return post;
    }

    public Post unlikePost(String userId, String postId) {
        ObjectId objectId = new ObjectId(postId);

        Post post = postRepository.findById(objectId)
                .orElseThrow(() -> new RuntimeException("Post not found"));

        post.getLikes().removeIf(like -> like.getUserId().equals(userId));

        return postRepository.save(post);
    }

    public Post addComment(String postId, String userId, String content) {
        Post post = postRepository.findById(new ObjectId(postId))
                .orElseThrow(() -> new RuntimeException("Post not found"));

        Comment comment = new Comment(
                new ObjectId(),
                userId,
                content,
                LocalDateTime.now()
        );

        post.getComments().add(comment);
        return postRepository.save(post);
    }
}
