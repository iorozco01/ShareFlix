package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

//Test MediaList and methods
public class MediaListTest {
    public MediaList testList;
    public Movie movie;

    @BeforeEach
    public void runBefore() {
        testList = new MediaList("name");
        movie = new Movie("name", "genre");
        testList.add(movie);
    }

    @Test
    public void testSetName() {
        testList.setName("testName");
        assertEquals("testName", testList.getName());
    }
}
