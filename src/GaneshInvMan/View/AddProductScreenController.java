package GaneshInvMan.View;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import GanesInvMan.Model.Inhouse;
import GanesInvMan.Model.Inventory;
import GanesInvMan.Model.Outsourced;
import GanesInvMan.Model.Part;
import GanesInvMan.Model.Product;

public class AddProductScreenController implements Initializable {

    @FXML
    private Button SEARCH_PRODUCT;
    @FXML
    private Button ADD_PRODUCT;
    @FXML
    private Button DELETE_PRODUCT;
    @FXML
    private Button CANCEL;
    @FXML
    private Button SAVE;
    @FXML
    private TableView<Part> PRODUCT;
    @FXML
    private TableColumn<Part, Integer> PRODUCT_PART_ID;
    @FXML
    private TableColumn<Part, String> PRODUCT_PART_NAME;
    @FXML
    private TableColumn<Part, Integer> PRODUCT_INVENTORY_LEVEL;
    @FXML
    private TableColumn<Part, Double> PRODUCT_PRICE_PER_UNIT;
    @FXML
    private TableView<Part> ASSOCIATED_PARTS;
    @FXML
    private TableColumn<Part, Integer> ASSOCIATED_PARTS_PART_ID;
    @FXML
    private TableColumn<Part, String> ASSOCIATED_PARTS_PART_NAME;
    @FXML
    private TableColumn<Part, Integer> ASSOCIATED_PARTS_INVENTORY_LEVEL;
    @FXML
    private TableColumn<Part, Double> ASSOCIATED_PARTS_PRICE_PER_UNIT;
    @FXML
    private TextField PARTS_SEARCH_TEXT;
    @FXML
    private Label PROD_ID;
    @FXML
    private TextField PROD_ID_TEXT;
    @FXML
    private Label PROD_NAME;
    @FXML
    private TextField PROD_NAME_TEXT;
    @FXML
    private Label PROD_INVENTORY;
    @FXML
    private TextField PROD_INVENTORY_TEXT;
    @FXML
    private Label PROD_PRICE;
    @FXML
    private TextField PROD_PRICE_TEXT;
    @FXML
    private Label PROD_MAX;
    @FXML
    private TextField PROD_MAX_TEXT;
    @FXML
    private Label PROD_MIN;
    @FXML
    private TextField PROD_MIN_TEXT;

    
    private ObservableList<Part> partsOfTheProduct = FXCollections.observableArrayList();
    private int productID;
    
    private String errMessage = "";
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        productID = Inventory.prodIDCount();
        PROD_ID_TEXT.setText("Auto Gen: " + productID);   
        
        PRODUCT_PART_ID.setCellValueFactory(cellData -> cellData.getValue().partIDProp().asObject());
        PRODUCT_PART_NAME.setCellValueFactory(cellData -> cellData.getValue().partNameProp());
        PRODUCT_INVENTORY_LEVEL.setCellValueFactory(cellData -> cellData.getValue().partInStockProp().asObject());
        PRODUCT_PRICE_PER_UNIT.setCellValueFactory(cellData -> cellData.getValue().partPriceProp().asObject());
        PRODUCT.setItems(Inventory.partList);
        
        ASSOCIATED_PARTS_PART_ID.setCellValueFactory(cellData -> cellData.getValue().partIDProp().asObject());
        ASSOCIATED_PARTS_PART_NAME.setCellValueFactory(cellData -> cellData.getValue().partNameProp());
        ASSOCIATED_PARTS_INVENTORY_LEVEL.setCellValueFactory(cellData -> cellData.getValue().partInStockProp().asObject());
        ASSOCIATED_PARTS_PRICE_PER_UNIT.setCellValueFactory(cellData -> cellData.getValue().partPriceProp().asObject());
                
    }    

    @FXML
    private void ADD_HANDLER(ActionEvent event) {      
        Part selectedPart = PRODUCT.getSelectionModel().getSelectedItem();
        partsOfTheProduct.add(selectedPart);
        ASSOCIATED_PARTS.setItems(partsOfTheProduct);
    }

    @FXML
    private void SAVE_HANDLER(ActionEvent event) throws IOException {
        String prodName = PROD_NAME_TEXT.getText();
        String prodPrice = PROD_PRICE_TEXT.getText();
        String prodInv = PROD_INVENTORY_TEXT.getText();
        String prodMin = PROD_MIN_TEXT.getText();
        String prodMax = PROD_MAX_TEXT.getText();
        
        try {
            errMessage = Product.ProductValidification(prodName, Integer.parseInt(prodMin), Integer.parseInt(prodMax), Integer.parseInt(prodInv), Double.parseDouble(prodPrice), partsOfTheProduct, errMessage);
            if (errMessage.length() != 0) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setContentText(errMessage);
                alert.showAndWait();
                errMessage = "";
            } 
            else {       
                Product newProd = new Product();

                newProd.setProductID(productID);
                newProd.setProductName(prodName);
                newProd.setProductCost(Double.parseDouble(prodPrice));
                newProd.setProductInStock(Integer.parseInt(prodInv));
                newProd.setProductMin(Integer.parseInt(prodMin));
                newProd.setProductMax(Integer.parseInt(prodMax));
                newProd.setProductAssociatedParts(partsOfTheProduct);
                Inventory.addProd(newProd);

                Parent addProducts = FXMLLoader.load(getClass().getResource("MainScreen.fxml"));
                Scene scene = new Scene(addProducts);
                Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
                window.setScene(scene);
                window.show();
            }
        } catch (NumberFormatException e) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setContentText("Please fill out correct details. Inv, Price, Max and Min must contain numbers only");
            alert.showAndWait();
        }
    }
    
    @FXML
    private void SEARCH_HANDLER(ActionEvent event) {
        ObservableList<Part> searchedPartList = FXCollections.observableArrayList();         
        String search = PARTS_SEARCH_TEXT.getText().toLowerCase();
        try{
            int itemNumber = Integer.parseInt(search);
            for(Part part : Inventory.partList){
                if(part.getPartID() == itemNumber){
                    searchedPartList.add(part);
                    PRODUCT.setItems(searchedPartList);
                }
            }
        }
        catch(NumberFormatException e){
            for(Part part: Inventory.partList){
                if(part.getPartName().toLowerCase().equals(search)){
                    searchedPartList.add(part);
                    PRODUCT.setItems(searchedPartList);
                }
                else if (search.equals("")) {
                    PARTS_SEARCH_TEXT.setText("");
                    PRODUCT.setItems(Inventory.partInfo()); 
                    return;
                }                

            }
        }
        if (searchedPartList.isEmpty()){
             Alert alert = new Alert(Alert.AlertType.INFORMATION);
             alert.setContentText("Please type out the exact Part ID or Part Name.");
             alert.showAndWait();
            }
    }

    @FXML
    private void DELETE_HANDLER(ActionEvent event) {
        Part selectedPart = ASSOCIATED_PARTS.getSelectionModel().getSelectedItem();
        if (selectedPart != null) {

            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setContentText("Are you sure you want to delete " + selectedPart.getPartName()+"?");        
            alert.showAndWait()
            
                .filter(response -> response == ButtonType.OK)
                .ifPresent(response -> partsOfTheProduct.remove(selectedPart));

            ASSOCIATED_PARTS.setItems(partsOfTheProduct);
        
        } 
        else {
            
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setContentText("Please select a part from the table.");

            alert.showAndWait();
        }
    }
    
    @FXML
    private void CANCEL_HANDLER(ActionEvent event) throws IOException {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setContentText("Are you sure you want to cancel the addition of the product " + PROD_NAME_TEXT.getText() + "?");
        Optional<ButtonType> result = alert.showAndWait();
        
        if (result.get() == ButtonType.OK) {
            Parent addParts = FXMLLoader.load(getClass().getResource("MainScreen.fxml"));
            Scene scene = new Scene(addParts);
            Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
            window.setScene(scene);
            window.show();
        }
    }   
}
