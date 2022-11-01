package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

//Test media sort exe
class MediaSortExeTest {
    MediaSortExe exe;
    Movie testMovie;
    Series testSeries;


    @BeforeEach
    public void setUp() {
        exe = new MediaSortExe();
        exe.newList("Test");

        testMovie = new Movie("MovieName", "MovieGenre");
        exe.addMovie("MovieName", "MovieGenre");

        testSeries = new Series("SeriesName", "SeriesGenre", 10);
        exe.addSeries("SeriesName", "SeriesGenre", 10);
    }

    @Test
    public void testAddSeries() {
        assertEquals("SeriesName", exe.getCurrentList().get(1).name);
        assertEquals("SeriesGenre", exe.getCurrentList().get(1).genre);
        assertEquals("series", exe.getCurrentList().get(1).mediaType);
        assertEquals(10, exe.getCurrentList().get(1).length);
    }

    @Test
    public void testAddMovie() {
        assertEquals("MovieName", exe.getCurrentList().get(0).name);
        assertEquals("MovieGenre", exe.getCurrentList().get(0).genre);
        assertEquals("movie", exe.getCurrentList().get(0).mediaType);
        assertEquals(0, exe.getCurrentList().get(0).length);
    }

    @Test
    public void testRemoveMedia() {
       assertEquals(2,  exe.getCurrentList().size());
       exe.removeMedia(1);
        assertEquals(1,  exe.getCurrentList().size());
    }

    @Test
    public void testNewList() {
        exe.newList("Please Work");
        assertEquals("Please Work", exe.getCurrentList().getName());
    }

    @Test
    public void testSelectList() {
        exe.newList("Please Work");
        exe.selectList(1);
        assertEquals("Please Work", exe.getCurrentList().getName());
        exe.selectList(0);
        assertEquals("Test", exe.getCurrentList().getName());
    }

    @Test
    public void testSaveCurrentList() {
        exe.saveCurrentList();
        exe.loadLists();
        assertEquals("Test",exe.getCurrentList().getName() );
    }

    @Test
    public void testLoadLists() {
        exe.saveCurrentList();
        exe.loadLists();
        assertEquals("Test",exe.getCurrentList().getName() );
    }

    @Test
    public void testUpdateList() {

    }

    @Test
    public void testSetCurrentList() {
        MediaList ml = new MediaList("Work 2");
        exe.setCurrentList(ml);
        assertEquals("Work 2", exe.getCurrentList().getName());
    }

    @Test
    public void testGetListNames() {
        exe.newList("Please Work");
        ArrayList testArray = exe.getListNames();
        assertEquals("Test", testArray.get(0));
        assertEquals("Please Work", testArray.get(1));
    }

    @Test
    public void testGetListFromName() {
        exe.newList("Please Work");
        assertEquals(1, exe.getListFromName("Please Work"));
        assertEquals(0, exe.getListFromName("Test"));
    }

    @Test
    public void testGetCurrentList() {
        assertEquals("Test", exe.getCurrentList().getName());

        assertEquals("MovieName", exe.getCurrentList().get(0).name);
        assertEquals("MovieGenre", exe.getCurrentList().get(0).genre);
        assertEquals("movie", exe.getCurrentList().get(0).mediaType);
        assertEquals(0, exe.getCurrentList().get(0).length);

        assertEquals("SeriesName", exe.getCurrentList().get(1).name);
        assertEquals("SeriesGenre", exe.getCurrentList().get(1).genre);
        assertEquals("series", exe.getCurrentList().get(1).mediaType);
        assertEquals(10, exe.getCurrentList().get(1).length);
    }

    @Test
    public void testGetMainList() {
        assertEquals(1, exe.getMainList().size());
        exe.newList("Please Work");
        assertEquals(2, exe.getMainList().size());
    }
}