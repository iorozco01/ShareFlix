package model;

import org.json.JSONObject;

//Media class
public abstract class Media {

    protected String name;
    protected String genre;
    protected String mediaType;
    protected int length;

    /*
        REQUIRES: valid String for name, mediaType and genre
        MODIFIES: this
        EFFECTS: creates object of type Media with specified name, mediaType and genre
     */
    public Media(String name, String mediaType, String genre) {
        this.name = name;
        this.mediaType = mediaType;
        this.genre = genre;
    }

    public String getName() {
        return name;
    }

    public String getMediaType() {
        return mediaType;
    }

    public String getGenre() {
        return genre;
    }

    public int getLength() {
        return length;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setMediaType(String mediaType) {
        this.mediaType = mediaType;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public void setLength(int length) {
        this.length = length;
    }

    /*
    EFFECTS: returns this object as a Json object
     */
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("name", name);
        json.put("media type", mediaType);
        json.put("genre", genre);
        if (mediaType.equals("series")) {
            json.put("length", length);
        }
        return json;
    }
}
