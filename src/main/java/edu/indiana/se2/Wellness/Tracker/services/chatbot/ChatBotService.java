package edu.indiana.se2.Wellness.Tracker.services.chatbot;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ChatBotService {

    @Autowired
    private LocalChatBotService localChatBotService;

    public String getSmartResponse(String message) {
        return localChatBotService.getResponse(message);
    }
}