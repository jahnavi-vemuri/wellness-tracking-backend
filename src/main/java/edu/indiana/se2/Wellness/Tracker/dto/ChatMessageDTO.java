package edu.indiana.se2.Wellness.Tracker.dto;

public class ChatMessageDTO {
    private String message;

    public ChatMessageDTO() {}

    public ChatMessageDTO(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
