package persistence;

import model.MediaList;
import model.Media;
import model.Movie;
import model.Series;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;


//Test JSON writer
class JsonWriterTest extends JsonTest {
    //Code from Paul Carter

    //NOTE TO CPSC 210 STUDENTS: the strategy in designing tests for the JsonWriter is to
    //write data to a file and then use the reader to read it back in and check that we
    //read in a copy of what was written out.

    @Test
    void testWriterInvalidFile() {
        try {
            MediaList wr = new MediaList("My media list");
            JsonWriter writer = new JsonWriter("./data/my\0illegal:fileName.json");
            writer.open();
            fail("IOException was expected");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    void testWriterEmptyMediaList() {
        try {
            MediaList wr = new MediaList("My work room");
            JsonWriter writer = new JsonWriter("./data/testWriterEmptyMediaList.json");
            writer.open();
            writer.write(wr);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterEmptyMediaList.json");
            wr = reader.read();
            assertEquals("My work room", wr.getName());
            assertEquals(0, wr.size());
        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }

    @Test
    void testWriterGeneralMediaList() {
        try {
            MediaList wr = new MediaList("My work room");
            wr.add(new Movie("Saw", "Horror"));
            wr.add(new Series("Wandavision", "SuperHeroes", 10));
            JsonWriter writer = new JsonWriter("./data/testWriterGeneralMediaList.json");
            writer.open();
            writer.write(wr);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterGeneralMediaList.json");
            wr = reader.read();
            assertEquals("My work room", wr.getName());
            assertEquals(2, wr.size());

        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }
}