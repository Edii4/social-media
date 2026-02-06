package dev.edi.socialmedia;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ChatService {

    @Autowired
    private ChatRoomRepository chatRoomRepository;

    // Start or get existing chat between two users
    public ChatRoom getOrCreateChat(String userId1, String userId2) {
        List<String> participants = List.of(userId1, userId2);

        return chatRoomRepository.findByParticipants(participants)
                .orElseGet(() -> {
                    ChatRoom newChat = new ChatRoom();
                    newChat.setParticipants(participants);
                    return chatRoomRepository.save(newChat);
                });
    }

    // Send a message
    public Message sendMessage(String chatId, String senderId, String content) {
        ChatRoom chatRoom = chatRoomRepository.findById(chatId)
                .orElseThrow(() -> new RuntimeException("Chat not found"));

        Message message = new Message();
        message.setSenderId(senderId);
        message.setContent(content);
        message.setSentAt(LocalDateTime.now());

        chatRoom.getMessages().add(message);
        chatRoomRepository.save(chatRoom);

        return message;
    }

    // Get all messages in a chat
    public List<Message> getMessages(String chatId) {
        ChatRoom chatRoom = chatRoomRepository.findById(chatId)
                .orElseThrow(() -> new RuntimeException("Chat not found"));
        return chatRoom.getMessages();
    }
}