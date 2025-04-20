package edu.indiana.se2.Wellness.Tracker.controller;

import jakarta.annotation.PostConstruct;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.client.RestTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Map;

@Component
@RestController
@RequestMapping("/api")
public class GroqController {

    private static final Logger logger = LoggerFactory.getLogger(GroqController.class);

    // Groq API endpoint and API key from application.properties
    @Value("${groq.api.endpoint}")
    private String groqApiEndpoint;

    @Value("${groq.api.key}")
    private String groqApiKey;

    @PostConstruct
    public void logProperties() {
        // Log the Groq API properties for debugging (avoid logging sensitive data in production)
        logger.info("Groq API Endpoint: {}", groqApiEndpoint);
        logger.info("Groq API Key: {}", groqApiKey);
    }

    @PostMapping("/groq-response")
    public ResponseEntity<Map<String, Object>> getGroqResponse(@RequestBody Map<String, Object> userRequest) {
        try {
            // Create a RestTemplate instance
            RestTemplate restTemplate = new RestTemplate();

            // Define the prompt that instructs the assistant's behavior
            String prompt = "You are a certified wellness assistant specializing in physical fitness, mental health, and balanced meal diets. " +
                    "You always provide simple, relevant, and helpful answers. Do not respond with vague or unrelated information. " +
                    "Wrap your meaningful response in less than 50 words and try to provide answers as points. " +
                    "Give more details of previous questions if the user asks for it. " +
                    "Strictly do not respond with other questions apart from physical, mental, and meal diets.";

            // Get the user message, it should be a string
            String userMessage = (String) ((List<Map<String, Object>>) userRequest.get("messages")).get(0).get("content");

            // Construct the API request body, including the prompt as the first message
            Map<String, Object> requestBody = Map.of(
                    "model", "llama3-70b-8192", // Model name or ID
                    "messages", List.of(
                            Map.of("role", "system", "content", prompt), // Add system prompt message first
                            Map.of("role", "user", "content", userMessage) // User's message passed as a string
                    ),
                    "temperature", 0.7 // Temperature setting (controls response randomness)
            );

            // Set Groq API headers with Authorization
            HttpHeaders headers = new HttpHeaders();
            headers.set("Authorization", "Bearer " + groqApiKey);
            HttpEntity<Map<String, Object>> entity = new HttpEntity<>(requestBody, headers);

            // Make the API request to Groq
            ResponseEntity<Map> response = restTemplate.exchange(groqApiEndpoint, HttpMethod.POST, entity, Map.class);

            // Log the entire response body for debugging
            logger.info("📨 Response body: {}", response.getBody());

            // Check if the response body contains the expected structure
            Map<String, Object> body = response.getBody();
            if (body == null || !body.containsKey("choices")) {
                throw new Exception("Invalid response structure, 'choices' not found.");
            }

            // Extract the assistant's response from the API response
            List<Map<String, Object>> choices = (List<Map<String, Object>>) body.get("choices");
            Map<String, Object> choice = choices.get(0); // Access the first choice
            Map<String, Object> message = (Map<String, Object>) choice.get("message"); // Get the message map
            String reply = (String) message.get("content"); // Extract the content field

            // Return only the assistant's response
            return ResponseEntity.ok(Map.of("reply", reply));

        } catch (Exception e) {
            logger.error("Error while getting response from Groq API", e);
            return ResponseEntity.status(500).body(Map.of("error", "Error while processing your request"));
        }
    }

}
