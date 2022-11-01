package persistence;

import org.json.JSONObject;

//Obtained from Paul Carter JsonSerializationDemo

public interface Writable {
    // EFFECTS: returns this as JSON object
    JSONObject toJson();
}
