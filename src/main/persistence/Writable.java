package persistence;

import org.json.JSONObject;

// Modelled from Writable in the JsonSerializationDemo example.
public interface Writable {

    // EFFECTS: returns this as a JSON object.
    JSONObject toJson();
}
