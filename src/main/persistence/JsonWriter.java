package persistence;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;

import org.json.JSONObject;

import model.League;

// Modelled from JsonWriter in the JsonSerializationDemo example.
// Represents a writer that writes JSON representation of the BaseballApp
// to a destination file.
public class JsonWriter {

    private static final int TAB = 4;
    private String destination;
    private PrintWriter writer;

    // EFFECTS: constructs a writer to write data to destination file.
    public JsonWriter(String destination) {
        this.destination = destination;
    }

    // MODIFIES: this
    // EFFECTS: opens writer; throws FileNotFoundException if destination file
    //          cannot be opened for writing.
    public void open() throws FileNotFoundException {
        writer = new PrintWriter(new File(destination));
    }

    // MODIFIES: this
    // EFFECTS: writes JSON representation of league to file
    public void write(League league) {
        JSONObject json = league.toJson();
        saveToFile(json.toString(TAB));
    }

    // MODIFIES: this
    // EFFECTS: closes the writer
    public void close() {
        writer.close();
    }

    // MODIFIES: this
    // EFFECTS: writes string to file
    private void saveToFile(String json) {
        writer.print(json);
    }
}
