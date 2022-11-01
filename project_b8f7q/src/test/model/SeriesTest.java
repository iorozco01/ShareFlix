package model;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

//Test Series and components
public class SeriesTest {
    public Series series;

    @BeforeEach
    public void runBefore() {
        series = new Series("name", "genre", 0);
    }

    @Test
    public void testSeries() {
        assertEquals("name", series.getName());
        assertEquals("genre", series.getGenre());
        assertEquals(0, series.getLength());
        assertEquals("series", series.getMediaType());
    }

    @Test
    public void testSetName() {
        series.setName("testName");
        assertEquals("testName", series.getName());
    }

    @Test
    public void testSetGenre() {
        series.setGenre("testGenre");
        assertEquals("testGenre", series.getGenre());
    }

    @Test
    public void testSetMediaType() {
        series.setMediaType("testMedia");
        assertEquals("testMedia", series.getMediaType());
    }

    @Test
    public void testSetEpisodes() {
        series.setLength(10);
        assertEquals(10, series.getLength());
    }
}
