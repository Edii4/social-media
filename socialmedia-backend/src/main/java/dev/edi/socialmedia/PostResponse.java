package dev.edi.socialmedia;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.bson.types.ObjectId;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class PostResponse {
    private String id;
    private String content;
    private String imageUrl;
    private LocalDateTime createdAt;

    private String username;
    private String profilePicUrl;

    private int likeCount;
    private boolean likedByCurrentUser;
    private List<CommentResponse> comments;

    public PostResponse(String id, String content, String imageUrl, LocalDateTime createdAt, String username, String profilePicUrl, int likeCount, boolean likedByCurrentUser, List<CommentResponse> comments) {
        this.id = id;
        this.content = content;
        this.imageUrl = imageUrl;
        this.createdAt = createdAt;
        this.username = username;
        this.profilePicUrl = profilePicUrl;
        this.likeCount = likeCount;
        this.likedByCurrentUser = likedByCurrentUser;
        this.comments = comments;
    }

    public String getId() {
        return id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getProfilePicUrl() {
        return profilePicUrl;
    }

    public void setProfilePicUrl(String profilePicUrl) {
        this.profilePicUrl = profilePicUrl;
    }
}
