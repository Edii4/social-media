package dev.edi.socialmedia;

import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.Optional;

public interface ChatRoomRepository extends MongoRepository<ChatRoom, String> {
    // Find chat with exact participants
    Optional<ChatRoom> findByParticipants(List<String> participants);
}