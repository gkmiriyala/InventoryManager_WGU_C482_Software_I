package GaneshInvMan.View;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.stage.Stage;
import GanesInvMan.Model.Inhouse;
import GanesInvMan.Model.Inventory;
import GanesInvMan.Model.Outsourced;
import GanesInvMan.Model.Part;
import static GaneshInvMan.View.MainScreenController.partChosenToModify;
import static GanesInvMan.Model.Inventory.partInfo;

public class ModifyPartScreenController implements Initializable {

    @FXML
    private ToggleGroup partLocation;    
    @FXML
    private RadioButton Radio_InHouse_Part;
    @FXML
    private RadioButton Radio_Outsourced_Part;
    @FXML
    private Label PART_ID;
    @FXML
    private TextField PART_ID_TEXT;
    @FXML
    private Label PART_NAME;
    @FXML
    private TextField PART_NAME_TEXT;
    @FXML
    private Label PART_INVENTORY;
    @FXML
    private TextField PART_INVENTORY_TEXT;
    @FXML
    private Label PART_PRICE;
    @FXML
    private TextField PART_PRICE_TEXT;
    @FXML
    private Label PART_MAX;
    @FXML
    private TextField PART_MAX_TEXT;
    @FXML
    private Label PART_LOCATION;
    @FXML
    private TextField PART_LOCATION_TEXT;
    @FXML
    private Label PART_COMPANY_NAME;
    @FXML
    private TextField PART_COMPANY_NAME_TEXT;
    @FXML
    private Label PART_MIN;
    @FXML
    private TextField PART_MIN_TEXT;
    @FXML
    private Button SAVE;
    @FXML
    private Button CANCEL;
    private final int partIndex = partChosenToModify();
    private int partID;
    private boolean isInhouse;
    
    private String errMessage = "";

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        Part part = partInfo().get(partIndex);
        partID = partInfo().get(partIndex).getPartID();
        PART_ID_TEXT.setText("Part ID: " + partID);
        PART_NAME_TEXT.setText(part.getPartName());
        PART_INVENTORY_TEXT.setText(Integer.toString(part.getPartInStock()));
        PART_PRICE_TEXT.setText(Double.toString(part.getPartCost()));
        PART_MIN_TEXT.setText(Integer.toString(part.getPartMin()));
        PART_MAX_TEXT.setText(Integer.toString(part.getPartMax()));
        if (part instanceof Inhouse) {
            PART_LOCATION_TEXT.setText(Integer.toString(((Inhouse) partInfo().get(partIndex)).getMachID()));
            PART_LOCATION.setText("Machine ID");
            Radio_InHouse_Part.setSelected(true);

        } else {
            PART_LOCATION_TEXT.setText(((Outsourced) partInfo().get(partIndex)).getCompName());
            PART_LOCATION.setText("Company Name");
            Radio_Outsourced_Part.setSelected(true);
        }
    }    

    @FXML
    private void Radio_InHouse_PartHandler(ActionEvent event) {
        isInhouse = true;
        PART_LOCATION.setText("Machine ID");
        PART_LOCATION_TEXT.setPromptText("Mach ID");
    }

    @FXML
    private void Radio_Outsourced_PartHandler(ActionEvent event) {
        isInhouse = false;
        PART_LOCATION.setText("Company Name");
        PART_LOCATION_TEXT.setPromptText("Comp Nm");
    }
    
    @FXML
    private void SAVE_HANDLER(ActionEvent event) throws IOException {
        String partName = PART_NAME_TEXT.getText();
        String partInv = PART_INVENTORY_TEXT.getText();
        String partPrice = PART_PRICE_TEXT.getText();
        String partMin = PART_MIN_TEXT.getText();
        String partMax = PART_MAX_TEXT.getText();
        String partLocation = PART_LOCATION_TEXT.getText();
        
        try {
            errMessage = Part.partValidification(partName, Integer.parseInt(partMin), Integer.parseInt(partMax), Integer.parseInt(partInv), Double.parseDouble(partPrice), errMessage);
            if (errMessage.length() != 0) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setContentText(errMessage);
                alert.showAndWait();
                errMessage = "";
            } else {
                if (isInhouse == true) {
                    Inhouse newInhousePart = new Inhouse();

                    newInhousePart.setPartID(partID);
                    newInhousePart.setPartName(partName);
                    newInhousePart.setPartCost(Double.parseDouble(partPrice));
                    newInhousePart.setPartInStock(Integer.parseInt(partInv));
                    newInhousePart.setPartMin(Integer.parseInt(partMin));
                    newInhousePart.setPartMax(Integer.parseInt(partMax));
                    newInhousePart.setMachID(Integer.parseInt(partLocation));
                    Inventory.updatePart(partIndex, newInhousePart);
                } else {
                    Outsourced newOutsourcedPart = new Outsourced();

                    newOutsourcedPart.setPartID(partID);
                    newOutsourcedPart.setPartName(partName);
                    newOutsourcedPart.setPartCost(Double.parseDouble(partPrice));
                    newOutsourcedPart.setPartInStock(Integer.parseInt(partInv));
                    newOutsourcedPart.setPartMin(Integer.parseInt(partMin));
                    newOutsourcedPart.setPartMax(Integer.parseInt(partMax));
                    newOutsourcedPart.setCompName(partLocation);
                    Inventory.updatePart(partIndex, newOutsourcedPart);
                }

                Parent addParts = FXMLLoader.load(getClass().getResource("MainScreen.fxml"));
                Scene scene = new Scene(addParts);
                Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
                window.setScene(scene);
                window.show();
            }
        } catch (NumberFormatException e) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setContentText("Please fill out correct details. Inv, Price/Cost, Max and Min must contain numbers only");
            alert.showAndWait();
        }
    }

    @FXML
    private void CANCEL_HANDLER(ActionEvent event) throws IOException {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setContentText("Are you sure you want to cancel the modification of the part " + PART_NAME_TEXT.getText() + "?");
        Optional<ButtonType> result = alert.showAndWait();
        
        if (result.get() == ButtonType.OK) {
            Parent modParts = FXMLLoader.load(getClass().getResource("MainScreen.fxml"));
            Scene scene = new Scene(modParts);
            Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
            window.setScene(scene);
            window.show();
        }
    }    
}
