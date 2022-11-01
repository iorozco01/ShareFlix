package model;

import org.json.JSONObject;

//Series class containing name, length and genre
public class Series extends Media {
    /*
        REQUIRES: valid String for name and genre and valid double for episodes
        MODIFIES: this
        EFFECTS: creates object of type Series with specified name, genre, and episodes
     */
    public Series(String name, String genre, int length) {
        super(name, "series", genre);
        this.length = length;
    }

    /*
    EFFECTS: returns this object as a Json object
     */
    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("name", name);
        json.put("media type", "series");
        json.put("genre", genre);
        json.put("length", length);
        return json;
    }
}
