package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;

public class ModifyProductController {

    private Parent root;
    private Scene scene;
    private Stage stage;

    public static Product currentSelectionModifyProduct;
    private static ObservableList<Part> table1PartList = FXCollections.observableArrayList();
    private int partsStartingSize;
    private int partsCounter;




    @FXML
    private TableView tableParts;
    @FXML
    private TableColumn columnPartID;
    @FXML
    private TableColumn columnPartName;
    @FXML
    private TableColumn columnPartInv;
    @FXML
    private TableColumn columnPartPrice;
    @FXML
    private TextField searchParts;

    @FXML
    private TableView tableParts1;
    @FXML
    private TableColumn columnPartID1;
    @FXML
    private TableColumn columnPartName1;
    @FXML
    private TableColumn columnPartInv1;
    @FXML
    private TableColumn columnPartPrice1;

    @FXML
    private TextField fieldID;
    @FXML
    private TextField fieldName;
    @FXML
    private TextField fieldInv;
    @FXML
    private TextField fieldPrice;
    @FXML
    private TextField fieldMax;
    @FXML
    private TextField fieldMin;

    @FXML
    private Label errorLabel;



    /**
     * RUNTIME ERROR: the different field values threw exceptions because they were being passed the wrong value types.
     * FIX: Cast the values to strings to be accepted into the text fields.
     *
     * Initialize runs when the class object is called.
     * So every time the ModifyProduct page opens, this code is executed.
     * This is needed to populate the tables and text fields with the product's data.
     */
    public void initialize(){

        tableParts.setItems(PartList.getPartList());

        columnPartID.setCellValueFactory(new PropertyValueFactory<>("id"));
        columnPartName.setCellValueFactory(new PropertyValueFactory<>("name"));
        columnPartInv.setCellValueFactory(new PropertyValueFactory<>("stock"));
        columnPartPrice.setCellValueFactory(new PropertyValueFactory<>("price"));

        columnPartID1.setCellValueFactory(new PropertyValueFactory<>("id"));
        columnPartName1.setCellValueFactory(new PropertyValueFactory<>("name"));
        columnPartInv1.setCellValueFactory(new PropertyValueFactory<>("stock"));
        columnPartPrice1.setCellValueFactory(new PropertyValueFactory<>("price"));


        fieldID.setText(String.valueOf(currentSelectionModifyProduct.getId()));
        fieldName.setText(currentSelectionModifyProduct.getName());
        fieldInv.setText(String.valueOf(currentSelectionModifyProduct.getStock()));
        fieldPrice.setText(String.valueOf(currentSelectionModifyProduct.getPrice()));
        fieldMax.setText(String.valueOf(currentSelectionModifyProduct.getMax()));
        fieldMin.setText(String.valueOf(currentSelectionModifyProduct.getMin()));


        partsStartingSize = currentSelectionModifyProduct.getAllAssociatedParts().size();
        partsCounter = 0;
        table1PartList = currentSelectionModifyProduct.getAllAssociatedParts();
        tableParts1.setItems(currentSelectionModifyProduct.getAllAssociatedParts());
    }

    /**
     * Adds a new product to the product list with the same ID, then deletes the old product.
     * Then returns to the main menu.
     * @param event
     * @throws IOException
     */
    public void save(ActionEvent event) throws IOException {

        if (catchErrorsAndDisplay()){
            return;
        }

        Product product = new Product(currentSelectionModifyProduct.getId(), fieldName.getText() ,Double.parseDouble(fieldPrice.getText()), Integer.parseInt(fieldInv.getText()), Integer.parseInt(fieldMin.getText()), Integer.parseInt(fieldMax.getText()), table1PartList);
        ProductList.removeProduct(currentSelectionModifyProduct);
        ProductList.addProduct(product);


        root = FXMLLoader.load(getClass().getResource("FormMain.fxml"));
        scene = new Scene(root);
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }

    /**
     * Returns to the main menu without saving a product.
     * @param event
     * @throws IOException
     */
    public void cancel(ActionEvent event) throws IOException {

        table1PartList.remove(partsStartingSize, partsStartingSize + partsCounter);
        root = FXMLLoader.load(getClass().getResource("FormMain.fxml"));
        scene = new Scene(root);
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }

    /**
     * Moves a part from the top table to the bottom table.
     * @param event
     */
    public void add(ActionEvent event){

        Part selected = (Part)tableParts.getSelectionModel().getSelectedItem();

        if (selected == null){
            return;
        }

        partsCounter += 1;
        table1PartList.add(selected);
        tableParts1.setItems(table1PartList);
    }

    /**
     * Allows the user to remove an item from the bottom table if they confirm the alert.
     * @param event
     */
    public void remove(ActionEvent event){

        Part selected = (Part)tableParts1.getSelectionModel().getSelectedItem();

        if (selected == null){
            return;
        }


        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Delete Item");
        alert.setHeaderText("Are you sure?");

        if (alert.showAndWait().get() == ButtonType.OK){
            table1PartList.remove(selected);
            tableParts1.setItems(table1PartList);
            partsCounter -= 1;
        } else{
            return;
        }
    }

    /**
     * This is the same method used in the main page.
     * Searches for parts via name, if not found, then searches via ID.
     * @param event
     */
    public void searchPart(ActionEvent event){

        String query = searchParts.getText();
        ObservableList<Part> parts = searchPartNameResults(query);

        if (parts.size() == 0){ // If searchPartNameResults() doesn't return any parts
            try{
                int partNumFromQuery = Integer.parseInt(query);
                Part partByID = getPartByPartID(partNumFromQuery);

                if (partByID != null){
                    parts.add(partByID);
                }

            } catch (NumberFormatException e){

            }
        }
        tableParts.setItems(parts);
    }

    /**
     * This is the same method used in the main page.
     * Searches for a part using a name.
     * @param insertion
     * @return
     */
    private ObservableList<Part> searchPartNameResults(String insertion){

        ObservableList<Part> resultingParts = FXCollections.observableArrayList();
        ObservableList<Part> allParts = PartList.getPartList();

        for (int i = 0; i < allParts.size(); i++){
            if (allParts.get(i).getName().contains(insertion)){
                resultingParts.add(allParts.get(i));
            }
        }
        return resultingParts;
    }

    /**
     * This is the same method used in the main page.
     * Searches for a part using an ID.
     * @param IDInput
     * @return
     */
    private Part getPartByPartID(int IDInput){

        ObservableList<Part> allParts = PartList.getPartList();

        for (int i = 0; i < allParts.size(); i++){
            if (allParts.get(i).getId() == IDInput){
                return allParts.get(i);
            }
        }
        return null;
    }


    /**
     * @return true if there is an error with inserted values.
     */
    public boolean catchErrorsAndDisplay(){
        boolean errorCaught = false;
        boolean minMaxGood = true;
        String errorText = "Exception:\n";


        if (fieldName.getLength() <= 0){
            errorCaught = true;
            errorText += "Name required\n";
        }

        try{
            Integer.valueOf(fieldInv.getText());
        } catch (Exception e){
            errorCaught = true;
            minMaxGood = false;
            errorText += "Inventory must be an integer \n";
        }

        try{
            Double.valueOf(fieldPrice.getText());
        } catch (Exception e){
            errorCaught = true;
            errorText += "Price must be a double \n";
        }

        try{
            Integer.valueOf(fieldMax.getText());
        } catch (Exception e){
            errorCaught = true;
            minMaxGood = false;
            errorText += "Max must be an integer \n";
        }

        try{
            Integer.valueOf(fieldMin.getText());
        } catch (Exception e){
            errorCaught = true;
            minMaxGood = false;
            errorText += "Min must be an integer \n";
        }


        // Check to see if inventory values make sense
        if (minMaxGood) {
            if (Integer.parseInt(fieldMin.getText()) > Integer.parseInt(fieldMax.getText())) {
                errorCaught = true;
                errorText += "Min must be smaller than Max\n";
            }
            if ((Integer.parseInt(fieldInv.getText()) > Integer.parseInt(fieldMax.getText())) || (Integer.parseInt(fieldInv.getText()) < Integer.parseInt(fieldMin.getText()))) {
                errorCaught = true;
                errorText += "Inv must be between Min and Max\n";
            }
        }

        if (errorCaught){
            errorLabel.setText(errorText);
            return true;
        } else {
            return false;
        }
    }
}