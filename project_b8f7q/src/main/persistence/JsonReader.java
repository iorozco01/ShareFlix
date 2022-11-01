package persistence;

import model.MediaList;
import model.Movie;
import model.Series;
import org.json.*;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;

//Base code from Paul Carter

// Represents a reader that reads MediaList from JSON data stored in file
public class JsonReader {
    private final String source;

    // EFFECTS: constructs reader to read from source file
    public JsonReader(String source) {
        this.source = source;
    }

    // EFFECTS: reads MediaList from file and returns it;
    // throws IOException if an error occurs reading data from file
    public MediaList read() throws IOException {
        String jsonData = readFile(source);
        JSONObject jsonObject = new JSONObject(jsonData);
        return parseMediaList(jsonObject);
    }

    // EFFECTS: reads source file as string and returns it
    public String readFile(String source) throws IOException {
        StringBuilder contentBuilder = new StringBuilder();

        try (Stream<String> stream = Files.lines(Paths.get(source), StandardCharsets.UTF_8)) {
            stream.forEach(s -> contentBuilder.append(s));
        }

        return contentBuilder.toString();
    }

    // EFFECTS: parses MediaList from JSON object and returns it
    public MediaList parseMediaList(JSONObject jsonObject) {
        String name = jsonObject.getString("name");
        MediaList ml = new MediaList(name);
        addMedias(ml, jsonObject);
        return ml;
    }

    // MODIFIES: ml
    // EFFECTS: parses thingies from JSON object and adds them to MediaList
    public void addMedias(MediaList ml, JSONObject jsonObject) {
        JSONArray jsonArray = jsonObject.getJSONArray("media");
        for (Object json : jsonArray) {
            JSONObject nextMedia = (JSONObject) json;
            addMedia(ml, nextMedia);
        }
    }

    // MODIFIES: ml
    // EFFECTS: parses media from JSON object and adds it to MediaList
    public void addMedia(MediaList ml, JSONObject jsonObject) {
        String name = jsonObject.getString("name");
        String genre = jsonObject.getString("genre");
        String mediaType = jsonObject.getString("media type");

        if (mediaType.equals("movie")) {
            ml.add(new Movie(name, genre));
        } else if (mediaType.equals("series")) {
            ml.add(new Series(name, genre, jsonObject.getInt("length")));
        }

    }
}