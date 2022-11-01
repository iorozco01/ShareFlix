package persistence;

import model.MediaList;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

//Test JSON reader
class JsonReaderTest extends JsonTest {
    //Code from Paul Carter


    @Test
    void testReaderNonExistentFile() {
        JsonReader reader = new JsonReader("./data/noSuchFile.json");
        try {
            MediaList ml = reader.read();
            fail("IOException expected");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    void testReaderEmptyMediaList() {
        JsonReader reader = new JsonReader("./data/testReaderEmptyMediaList.json");
        try {
            MediaList ml = reader.read();
            assertEquals("My Media List", ml.getName());
            assertEquals(0, ml.size());
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }

    @Test
    void testReaderGeneralMediaList() {
        JsonReader reader = new JsonReader("./data/testReaderGeneralMediaList.json");
        try {
            MediaList ml = reader.read();
            assertEquals("My Media List", ml.getName());
            assertEquals(2, ml.size());
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }
}