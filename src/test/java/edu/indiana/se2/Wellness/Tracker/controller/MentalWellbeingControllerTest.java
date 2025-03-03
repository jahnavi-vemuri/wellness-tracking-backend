package edu.indiana.se2.Wellness.Tracker.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class MentalWellbeingControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Test
    public void testLogEntry() throws Exception {
        String json = "{" +
                "\"userId\": 1," +
                "\"date\": \"2025-03-02\"," +
                "\"moodRating\": 4," +
                "\"notes\": \"Feeling great today\"" +
                "}";

        mockMvc.perform(post("/api/mental-wellbeing/log")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.moodRating").value(4));
    }

    @Test
    public void testGetUserEntries() throws Exception {
        // This test assumes there's test data in the database or that a previous test has created an entry.
        mockMvc.perform(get("/api/mental-wellbeing/logs/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }
}
