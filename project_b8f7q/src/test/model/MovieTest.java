package model;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

//Test Movie and components
public class MovieTest {
    public Movie movie;

    @BeforeEach
    public void runBefore() {
        movie = new Movie("name", "genre");
    }

    @Test
    public void testMovie() {
        assertEquals("name", movie.getName());
        assertEquals("genre", movie.getGenre());
        assertEquals("movie", movie.getMediaType());
    }

    @Test
    public void testSetName() {
        movie.setName("testName");
        assertEquals("testName", movie.getName());
    }

    @Test
    public void testSetGenre() {
        movie.setGenre("testGenre");
        assertEquals("testGenre", movie.getGenre());
    }

    @Test
    public void testSetMediaType() {
        movie.setMediaType("testMedia");
        assertEquals("testMedia", movie.getMediaType());
    }
}
