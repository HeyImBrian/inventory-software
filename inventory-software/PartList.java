package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class PartList {

    private static ObservableList<Part> partList = FXCollections.observableArrayList();


    /**
     * Adds a part to the part list.
     * @param part
     */
    public static void addPart(Part part){
        partList.add(part);
    }


    /**
     * Gets a list of all the parts from the part list.
     * @return
     */
    public static ObservableList<Part> getPartList(){
        return partList;
    }


    /**
     * Removes a part from the part list.
     * @param part
     */
    public static void removePart(Part part) {
        partList.remove(part);
    }
}