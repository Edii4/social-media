package dev.edi.socialmedia;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "follows")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Follow {
    private String followerId;
    private String followingId;
}
