package sample;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 *
 * @author Brian St. Germain
 */
public class Main extends Application {

    private static int uniqueID = 0;


    /**
     * FUTURE ENHANCEMENT: It'd be nice to have this in it's own class. That way, the main class would be less cluttered.
     * @return a unique int
     */
    public static int getAndIncrementUniqueID(){
        uniqueID += 1;
        return uniqueID;
    }


    /**
     * RUNTIME ERROR: Many of my errors throughout the program was solved by importing the correct libraries.
     * The root is loaded with the main scene, then the root is applied to the scene, then the scene is shown on the stage.
     * @param stage
     */
    @Override
    public void start(Stage stage) throws Exception {
        try {
            addData(); // Loads test data into tables

            Parent root = FXMLLoader.load(getClass().getResource("FormMain.fxml"));

            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch(Exception e){
            e.printStackTrace();
        }
    }


    /**
     * Launch calls the start method.
     * @param args
     */
    public static void main(String[] args) {
        launch(args); // Launch will call the start method
    }


    /**
     * FUTURE ENHANCEMENT: This would be nice to have in it's own class. It takes up a lot of room and clutters main.
     * This is test data to make the testing process much easier and faster.
     */
    private void addData(){
        PartOutsourced p = new PartOutsourced(getAndIncrementUniqueID(), "Engine", 699.99, 2, 1, 12, "brian's motors");
        PartList.addPart(p);
        PartInHouse p2 = new PartInHouse(getAndIncrementUniqueID(), "Battery", 49.99, 6, 2, 50, 23);
        PartList.addPart(p2);

        ObservableList<Part> testParts = FXCollections.observableArrayList();
        testParts.add(p2);
        testParts.add(p2);
        testParts.add(p2);

        ObservableList<Part> testParts2 = FXCollections.observableArrayList();
        testParts2.add(p);
        testParts2.add(p);
        testParts2.add(p);


        Product o = new Product(getAndIncrementUniqueID(), "Go-Kart", 1999.99, 5, 1, 10, testParts2);
        ProductList.addProduct(o);
        Product o2 = new Product(getAndIncrementUniqueID(), "Car", 19999.99, 6, 3, 11, testParts);
        ProductList.addProduct(o2);

        return;
    }
}












































