package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Calendar;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

//Test Events created in media
class EventTest {
    Event testEvent;
    Date currentTime;

    @BeforeEach
    public void setUp() {
        testEvent = new Event("Test Event");
        currentTime = Calendar.getInstance().getTime();
    }

    @Test
    void testGetDate() {
        assertEquals(currentTime.toString(), testEvent.getDate().toString());
    }

    @Test
    void testGetDescription() {
        assertEquals("Test Event", testEvent.getDescription());
    }

    @Test
    void testToString() {
        assertEquals(currentTime + "\n" +"Test Event", testEvent.toString());
    }
}