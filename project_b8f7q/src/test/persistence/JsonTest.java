package persistence;

import model.Media;
import model.MediaList;

import static org.junit.jupiter.api.Assertions.assertEquals;

//Code from Paul Carter

//Test JSON
public class JsonTest {
    protected void checkMedia(String name, MediaList ml, Media media) {
        assertEquals(name, ml.getName());
    }
}
