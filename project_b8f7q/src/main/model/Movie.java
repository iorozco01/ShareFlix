package model;

import org.json.JSONObject;

//Movie class containing name and genre
public class Movie extends Media {

    /*
        REQUIRES: valid String for name and genre
        MODIFIES: this
        EFFECTS: creates object of type Movie with specified name and genre
     */
    public Movie(String name, String genre) {
        super(name, "movie", genre);
        length = 0;
    }

    /*
    EFFECTS: returns this object as a Json object
     */
    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("name", name);
        json.put("media type", "movie");
        json.put("genre", genre);
        return json;
    }

}
