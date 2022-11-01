package model;

import java.io.File;
import java.util.ArrayList;

import persistence.JsonReader;
import persistence.JsonWriter;

//Media Sorting exe
public class MediaSortExe {
    private static final String JSON_STORE = "./data";

    private final ArrayList<MediaList<Media>> mainList = new ArrayList<MediaList<Media>>();
    private int currentListIndex;
    private MediaList<Media> test;
    private MediaList<Media> currentList;

    /*
        EFFECTS: starts mediaSort app and jsonReader
     */
    public MediaSortExe() {
        JsonReader jsonReader = new JsonReader(JSON_STORE);
    }

    /*
        REQUIRES: user inputs valid name, genre and length
        MODIFIES: currentList, mainList
        EFFECTS: creates series with input name, genre and length and adds it to currentList
     */
    public void addSeries(String name, String genre, int length) {
        Series series;
        series = new Series(name, genre, length);
        mainList.get(currentListIndex).add(series);
        EventLog.getInstance().logEvent(new Event("Added series to " + currentList.getName()));
    }

    /*
        REQUIRES: user inputs valid name and genre
        MODIFIES: currentList, mainList
        EFFECTS: creates series with input name and genre and adds it to currentList
     */
    public void addMovie(String name, String genre) {
        Movie movie;
        movie = new Movie(name, genre);
        mainList.get(currentListIndex).add(movie);
        EventLog.getInstance().logEvent(new Event("Added movie to " + currentList.getName()));
    }

    /*
    REQUIRES: index is a valid int
    MODIFIES: currentList
    EFFECTS: removes object at index from list
     */
    public void removeMedia(int index) {
        EventLog.getInstance().logEvent(new Event("Removed "
                + currentList.get(index).getMediaType() + " from " + currentList.getName()));
        currentList.remove(index);
    }

    /*
        REQUIRES: user inputs a valid name
        MODIFIES: mainList
        EFFECTS: creates a new list with the specified name and adds it to mainList
     */
    public void newList(String listName) {
        MediaList<Media> list = new MediaList<>(listName);
        mainList.add(list);

        currentList = mainList.get(mainList.size() - 1);
        currentListIndex = mainList.size() - 1;
        EventLog.getInstance().logEvent(new Event(currentList.getName() + " was created"));
    }

    /*
        REQUIRES: user inputs a valid index
        MODIFIES: currentListIndex
        EFFECTS: changes currentListIndex to user input int
     */
    public void selectList(int index) {
        currentListIndex = index;
        currentList = mainList.get(index);
        EventLog.getInstance().logEvent(new Event(currentList.getName() + " was selected"));
    }

    // EFFECTS: saves the currentList to file
    public void saveCurrentList() {
        try {
            String path = JSON_STORE + "/" + currentList.getName() + ".json";
            JsonWriter fileWriter = new JsonWriter(path);
            fileWriter.open();
            fileWriter.write(currentList);
            fileWriter.close();
            System.out.println("Saved " + currentList.getName() + " to " + JSON_STORE);
            EventLog.getInstance().logEvent(new Event("Saved " + currentList.getName() + " to " + JSON_STORE));
        } catch (Exception e) {
            System.out.println("Unable to write to file: " + JSON_STORE);
        }
    }

    // MODIFIES: this
    // EFFECTS: loads lists from file
    public void loadLists() {
        File data = new File(JSON_STORE);

        for (final File file : data.listFiles()) {
            if (file.isFile()) {
                String fileName = file.getName();
                if (fileName.contains(".json")) {
                    String listName = fileName.substring(0, fileName.indexOf(".json"));
                    JsonReader reader = new JsonReader(file.getAbsolutePath());
                    MediaList<Media> medias = null;

                    try {
                        medias = reader.read();
                        mainList.add(medias);
                        EventLog.getInstance().logEvent(new Event(listName + " loaded from file"));
                    } catch (Exception e) {
                        System.out.println("Failed to load list");
                    }
                }
            }
        }
    }

    /*
    EFFECTS: prints events in EventLog to terminal
     */
    public void printLog() {
        for (Event next : EventLog.getInstance()) {
            System.out.println(next);
        }
    }

    /*
    REQUIRES: ml is a valid mediaList
    MODIFIES: currentList
    EFFECTS: sets currentList to ml
     */
    public void setCurrentList(MediaList<Media> ml) {
        this.currentList = ml;
    }

    //EFFECTS: returns the name of all lists loaded
    public ArrayList<String> getListNames() {
        ArrayList<String> listNames = new ArrayList<>();
        for (MediaList n : mainList) {
            listNames.add(n.getName());
        }
        return listNames;
    }

    /*
    REQUIRES: name is a valid string
    EFFECTS: returns index of list with the name inputted
     */
    public int getListFromName(String name) {
        int n = 0;
        for (MediaList ml : mainList) {
            if (ml.getName().equals(name)) {
                n = mainList.indexOf(ml);
            }
        }
        return n;
    }

    /*
    EFFECTS: returns currentList
     */
    public MediaList<Media> getCurrentList() {
        return currentList;
    }

    /*
    EFFECTS: returns mainList
     */
    public ArrayList<MediaList<Media>> getMainList() {
        return mainList;
    }
}
