package dev.edi.socialmedia;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "likes")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Like {
    @Id
    private ObjectId id;
    private String postId;
    private String userId;
}
