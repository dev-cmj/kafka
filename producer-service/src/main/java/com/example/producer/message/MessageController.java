package com.example.producer.message;

import com.example.common.dto.Message;
import com.example.common.dto.MessageRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/messages")
@RequiredArgsConstructor
public class MessageController {
    private final MessageProducer messageProducer;
    private final MessageRepository messageRepository;

    @PostMapping
    public ResponseEntity<Message> sendMessage(@Valid @RequestBody Message message) {
        return ResponseEntity.ok(messageProducer.sendMessage(message));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Message> getMessage(@PathVariable String id) {
        return messageRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping
    public ResponseEntity<List<Message>> getMessages(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        Page<Message> messages = messageRepository.findAll(PageRequest.of(page, size));
        return ResponseEntity.ok(messages.getContent());
    }
}