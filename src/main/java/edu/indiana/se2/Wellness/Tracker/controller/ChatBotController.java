package edu.indiana.se2.Wellness.Tracker.controller;

import edu.indiana.se2.Wellness.Tracker.dto.ChatMessageDTO;
import edu.indiana.se2.Wellness.Tracker.services.chatbot.ChatBotService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/chatbot")
@RequiredArgsConstructor
public class ChatBotController {

    @Autowired
    private ChatBotService chatBotService;

    @PostMapping
    public ResponseEntity<Map<String, String>> chat(@RequestBody ChatMessageDTO input) {
        String reply = chatBotService.getSmartResponse(input.getMessage());
        return ResponseEntity.ok(Map.of("reply", reply));
    }
}
