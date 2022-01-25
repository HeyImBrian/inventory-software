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
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;


/**
 * RUNTIME ERROR: I was getting errors trying to run my program because FormMain.fxml wasn't correctly set to use this file as the controller.
 */
public class MainController {

    private Parent root;
    private Scene scene;
    private Stage stage;


    @FXML
    private TextField searchParts;
    @FXML
    private TextField searchProducts;
    @FXML
    private Button buttonExit;
    @FXML
    private AnchorPane mainPane;
    @FXML
    private Label labelErrorProductDelete;


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
    private TableView tableProducts;
    @FXML
    private TableColumn columnProductID;
    @FXML
    private TableColumn columnProductName;
    @FXML
    private TableColumn columnProductInv;
    @FXML
    private TableColumn columnProductPrice;


    /**
     * Initialize runs when the class object is created.
     * So every time the main page opens, this code is executed.
     * This is needed to populate the tables.
     */
    public void initialize(){

        tableParts.setItems(PartList.getPartList());
        tableProducts.setItems(ProductList.getProductList());

        columnPartID.setCellValueFactory(new PropertyValueFactory<>("id"));
        columnPartName.setCellValueFactory(new PropertyValueFactory<>("name"));
        columnPartInv.setCellValueFactory(new PropertyValueFactory<>("stock"));
        columnPartPrice.setCellValueFactory(new PropertyValueFactory<>("price"));

        columnProductID.setCellValueFactory(new PropertyValueFactory<>("id"));
        columnProductName.setCellValueFactory(new PropertyValueFactory<>("name"));
        columnProductInv.setCellValueFactory(new PropertyValueFactory<>("stock"));
        columnProductPrice.setCellValueFactory(new PropertyValueFactory<>("price"));
    }


    /**
     * FUTURE ENHANCEMENT: The whole set of methods used to searching could be put in its own class because it's used several times.
     * This method tries to search using the name, if that fails, it searches using the ID.
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
     * Used by the searchPart method to search for names.
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
     * Used by the searchPart method to search for IDs.
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
     * Essentially the same as the searchPart method, except that it's searching for products.
     * Searches by name, if that fails, then it searches using ID.
     * @param event
     */
    public void searchProduct(ActionEvent event){
        String query = searchProducts.getText();

        ObservableList<Product> products = searchProductNameResults(query);

        if (products.size() == 0){ // If searchProductNameResults() doesn't return any products
            try{
                int productNumFromQuery = Integer.parseInt(query);
                Product productByID = getProductByProductID(productNumFromQuery);

                if (productByID != null){
                    products.add(productByID);
                }

            } catch (NumberFormatException e){

            }
        }
        tableProducts.setItems(products);
    }


    /**
     * Searches products by using the name.
     * @param insertion
     * @return
     */
    private ObservableList<Product> searchProductNameResults(String insertion){
        ObservableList<Product> resultingProducts = FXCollections.observableArrayList();
        ObservableList<Product> allProducts = ProductList.getProductList();

        for (int i = 0; i < allProducts.size(); i++){
            if (allProducts.get(i).getName().contains(insertion)){
                resultingProducts.add(allProducts.get(i));
            }
        }
        return resultingProducts;
    }


    /**
     * Searches products by using the ID.
     * @param IDInput
     * @return
     */
    private Product getProductByProductID(int IDInput){
        ObservableList<Product> allProducts = ProductList.getProductList();

        for (int i = 0; i < allProducts.size(); i++){
            if (allProducts.get(i).getId() == IDInput){
                return allProducts.get(i);
            }
        }
        return null;
    }


    /**
     * When this method is called, the stage will show the AddPart scene.
     * @param event
     * @throws IOException
     */
    public void switchToFormAddPart(ActionEvent event) throws IOException {
        // root -> scene -> stage
        root = FXMLLoader.load(getClass().getResource("FormAddPart.fxml"));
        scene = new Scene(root);
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }


    /**
     * When this method is called, the stage will show the ModifyPart scene.
     * Also passes the current selection to a variable in the target class.
     * @param event
     * @throws IOException
     */
    public void switchToFormModifyPart(ActionEvent event) throws IOException {
        Part selected = (Part)tableParts.getSelectionModel().getSelectedItem();

        if (selected == null){
            return;
        } else {
            ModifyPartController.currentSelectionModifyPart = selected;
        }

        root = FXMLLoader.load(getClass().getResource("FormModifyPart.fxml"));
        scene = new Scene(root);
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }


    /**
     * RUNTIME ERROR: Errors kept occurring when using ButtonType.Yes, so I had to switch to ButtonType.OK
     * Alerts the user to confirm that they would like to delete the part, if OK, deletes part.
     * @param event
     */
    public void deletePart(ActionEvent event){
        Part selected = (Part)tableParts.getSelectionModel().getSelectedItem();

        if (selected == null){
            return;
        }

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Delete Item");
        alert.setHeaderText("Are you sure?");

        if (alert.showAndWait().get() == ButtonType.OK){
            PartList.removePart(selected);
            tableParts.setItems(PartList.getPartList());
        } else{
            return;
        }
    }


    /**
     * When this method is called, the stage will show the AddProduct scene.
     * @param event
     * @throws IOException
     */
    public void switchToFormAddProduct(ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("FormAddProduct.fxml"));
        scene = new Scene(root);
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }


    /**
     * When this method is called, the stage will show the ModifyProduct scene.
     * Also passes the current selection to a variable in the target class.
     * @param event
     * @throws IOException
     */
    public void switchToFormModifyProduct(ActionEvent event) throws IOException {
        Product selected = (Product)tableProducts.getSelectionModel().getSelectedItem();

        if (selected == null) {
            return;
        } else {
            ModifyProductController.currentSelectionModifyProduct = selected;
        }

        root = FXMLLoader.load(getClass().getResource("FormModifyProduct.fxml"));
        scene = new Scene(root);
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }


    /**
     * Deletes a product if the user accepts the alert prompt and if the product has to associated parts.
     * @param event
     */
    public void deleteProduct(ActionEvent event){
        Product selected = (Product)tableProducts.getSelectionModel().getSelectedItem();

        if (selected == null){
            return;
        }

        if (selected.getAllAssociatedParts().size() > 0){
            labelErrorProductDelete.setText("Can not delete a product with parts");
            return;
        }
        labelErrorProductDelete.setText(""); // To reset the label

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Delete Item");
        alert.setHeaderText("Are you sure?");

        if (alert.showAndWait().get() == ButtonType.OK){
            ProductList.removeProduct(selected);
            tableProducts.setItems(ProductList.getProductList());
        } else{
            return;
        }
    }


    /**
     * RUNTIME ERROR: Was getting an error because @FXML wasn't added above the button and pane variables.
     * Exits the program after alerting the user to ask for confirmation.
     * @param event
     */
    public void exitProgram(ActionEvent event){

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Logout");
        alert.setHeaderText("Are you sure?");

        if (alert.showAndWait().get() == ButtonType.OK){
            stage = (Stage) mainPane.getScene().getWindow();
            stage.close();
        }
    }
}