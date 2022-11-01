package model;

import org.json.JSONArray;
import org.json.JSONObject;
import persistence.Writable;

import java.util.LinkedList;

//MediaList to hold Media
public class MediaList<M> extends LinkedList<Media> implements Writable {
    private String name;

    /*
        REQUIRES: name is not empty
        MODIFIES: this
        EFFECTS: creates new media list with specified name
     */
    public MediaList(String name) {
        super();
        this.name = name;
    }

    /*
     MODIFIES: this
     EFFECTS: creates new media list
     */
    public MediaList() {
        super();
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    /*
    EFFECTS: returns this object as a Json object
     */
    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("name", name);
        json.put("media", mediaToJson());
        return json;
    }

    // EFFECTS: returns media in this MediaList as a JSON array
    private JSONArray mediaToJson() {
        JSONArray jsonArray = new JSONArray();
        for (Media t : this) {
            jsonArray.put(t.toJson());
        }

        return jsonArray;
    }
}
