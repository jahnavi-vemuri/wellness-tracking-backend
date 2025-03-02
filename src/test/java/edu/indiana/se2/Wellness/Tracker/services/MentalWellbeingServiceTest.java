package edu.indiana.se2.Wellness.Tracker.services;

import edu.indiana.se2.Wellness.Tracker.entity.MentalWellbeingEntry;
import edu.indiana.se2.Wellness.Tracker.repository.MentalWellbeingRepository;
import edu.indiana.se2.Wellness.Tracker.services.activity.MentalWellbeingService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class MentalWellbeingServiceTest {
    private MentalWellbeingRepository repository;
    private MentalWellbeingService service;

    @BeforeEach // creates a mock repository and instantiates the service
    public void setUp() {
        repository = Mockito.mock(MentalWellbeingRepository.class);
        service = new MentalWellbeingService(repository);
    }

    @Test
    public void testLogEntrySuccess() { // Ensures that a valid entry is saved
        MentalWellbeingEntry entry = new MentalWellbeingEntry(1L, LocalDate.now(), 4, "Feeling good");
        when(repository.findByUserIdAndDate(entry.getUserId(), entry.getDate()))
                .thenReturn(Optional.empty());
        when(repository.save(any(MentalWellbeingEntry.class))).thenReturn(entry);

        MentalWellbeingEntry savedEntry = service.logEntry(entry);
        assertEquals(4, savedEntry.getMoodRating());
        verify(repository, times(1)).save(entry);
    }

    @Test
    public void testLogEntryDuplicate() { // Ensures that if an entry already exists for the same day, the service throws an exception
        MentalWellbeingEntry entry = new MentalWellbeingEntry(1L, LocalDate.now(), 3, "Okay day");
        when(repository.findByUserIdAndDate(entry.getUserId(), entry.getDate()))
                .thenReturn(Optional.of(entry));

        Exception exception = assertThrows(IllegalArgumentException.class, () -> service.logEntry(entry));
        assertEquals("Entry for this day already exists.", exception.getMessage());
    }
}
