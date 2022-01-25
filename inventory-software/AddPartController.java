package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.scene.control.TextField;

import java.io.IOException;

public class AddPartController {

    private Parent root;
    private Scene scene;
    private Stage stage;
    private boolean outsourced = true;


    @FXML
    private Text inOrOutText;
    @FXML
    private RadioButton radioInHouse;
    @FXML
    private RadioButton radioOutSourced;
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
    private TextField fieldCompanyMachine;

    @FXML
    private Label errorLabel;


    /**
     * RUNTIME ERROR: Caused by: java.lang.IllegalArgumentException: Can not set java.awt.Label field sample.AddPartController.inOrOutText to javafx.scene.text.Text
     * FIX: Changed inOrOutText from Label to Text.
     * Changes the text between either Machine ID or Company Name depending on the radio button selected.
     */
    public void radioButtonChangeText(ActionEvent event){
        if (radioInHouse.isSelected()){
            inOrOutText.setText("Machine ID");
            outsourced = false;
        } else {
            inOrOutText.setText("Company Name");
            outsourced = true;
        }
    }


    /**
     * RUNTIME ERROR: Caused by: java.lang.IllegalArgumentException: Can not set java.awt.TextField field sample.AddPartController.fieldName to javafx.scene.control.TextField
     * FIX: Accidentally imported awt instead of javafx. Removed awt import and replaced with javafx.
     * Checks if the part is outsourced or not, then makes either an outsourced part or an in-house part.
     * Adds the in-house or outsourced part to a list of parts.
     * Returns to the main page afterwards.
     */
    public void save(ActionEvent event) throws IOException {

        if (catchErrorsAndDisplay()) {
            return;
        }

        if (outsourced){
            PartOutsourced partOutsourced = new PartOutsourced(Main.getAndIncrementUniqueID(), fieldName.getText() ,Double.parseDouble(fieldPrice.getText()), Integer.parseInt(fieldInv.getText()), Integer.parseInt(fieldMin.getText()), Integer.parseInt(fieldMax.getText()), fieldCompanyMachine.getText());
            PartList.addPart(partOutsourced);
        } else {
            PartInHouse partInHouse = new PartInHouse(Main.getAndIncrementUniqueID(), fieldName.getText(), Double.parseDouble(fieldPrice.getText()), Integer.parseInt(fieldInv.getText()), Integer.parseInt(fieldMin.getText()), Integer.parseInt(fieldMax.getText()), Integer.parseInt(fieldCompanyMachine.getText()));
            PartList.addPart(partInHouse);
        }


        // root -> scene -> stage
        root = FXMLLoader.load(getClass().getResource("FormMain.fxml"));
        scene = new Scene(root);
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }


    /**
     * Returns to the main screen without saving any information.
     * @param event
     * @throws IOException
     */
    public void cancel(ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("FormMain.fxml"));
        scene = new Scene(root);
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }


    /**
     * FUTURE ENHANCEMENT: This could be added into it's own class. It gets reused in several classes.
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


        if (outsourced){
            try{
                String.valueOf(fieldCompanyMachine.getText());
            } catch (Exception e){
                errorCaught = true;
                errorText += "Company Name required. \n";
            }
        } else {
            try{
                Integer.valueOf(fieldCompanyMachine.getText());
            } catch (Exception e){
                errorCaught = true;
                errorText += "Machine ID must be an integer \n";
            }
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