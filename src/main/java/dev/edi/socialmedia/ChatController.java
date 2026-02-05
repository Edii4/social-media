package dev.edi.socialmedia;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/chats")
public class ChatController {

    @Autowired
    private ChatService chatService;

    // Start or get a chat between two users
    @PostMapping("/start")
    public ResponseEntity<ChatRoom> startChat(@RequestParam String userId1, @RequestParam String userId2) {
        ChatRoom chatRoom = chatService.getOrCreateChat(userId1, userId2);
        return ResponseEntity.ok(chatRoom);
    }

    // Send a message in a chat
    @PostMapping("/{chatId}/message")
    public ResponseEntity<Message> sendMessage(
            @PathVariable String chatId,
            @RequestParam String senderId,
            @RequestParam String content
    ) {
        Message message = chatService.sendMessage(chatId, senderId, content);
        return ResponseEntity.ok(message);
    }

    // Get all messages in a chat
    @GetMapping("/{chatId}")
    public ResponseEntity<List<Message>> getMessages(@PathVariable String chatId) {
        List<Message> messages = chatService.getMessages(chatId);
        return ResponseEntity.ok(messages);
    }
}