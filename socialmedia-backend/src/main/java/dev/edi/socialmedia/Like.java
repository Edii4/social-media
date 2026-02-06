package dev.edi.socialmedia;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Like {
    private String userId;
    private LocalDateTime likedAt = LocalDateTime.now();

    public String getUserId() {
        return userId;
    }

    public LocalDateTime getLikedAt() {
        return likedAt;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public void setLikedAt(LocalDateTime likedAt) {
        this.likedAt = likedAt;
    }
}
