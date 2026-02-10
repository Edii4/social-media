package dev.edi.socialmedia;

import java.time.LocalDateTime;

public class CommentResponse {

    private String id;
    private String userId;
    private String username;
    private String profilePicUrl;
    private String content;
    private LocalDateTime createdAt;

    public CommentResponse(
            String id,
            String userId,
            String username,
            String profilePicUrl,
            String content,
            LocalDateTime createdAt
    ) {
        this.id = id;
        this.userId = userId;
        this.username = username;
        this.profilePicUrl = profilePicUrl;
        this.content = content;
        this.createdAt = createdAt;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
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

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}
