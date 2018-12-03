package GaneshInvMan.View;

import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.stage.Modality;
import javafx.stage.Stage;
import java.io.IOException;
import GaneshInvMan.GanInvManMainApp;
import GanesInvMan.Model.Inhouse;
import GanesInvMan.Model.Inventory;
import GanesInvMan.Model.Outsourced;
import GanesInvMan.Model.Part;
import GanesInvMan.Model.Product;
import static GanesInvMan.Model.Inventory.prodInfo;
import static GanesInvMan.Model.Inventory.partInfo;

public class MainScreenController implements Initializable {

    private GanInvManMainApp mainApp;    
    private static int modifyPartIndex;
    private static Part modifyPart;
    private static int modifyProductIndex;
    private static Product modifyProduct;
    static boolean exists;

    public void setMainApp(GanInvManMainApp mainApp) {
        this.mainApp = mainApp;

        partsTable.setItems(Inventory.partInfo());
        productsTable.setItems(Inventory.prodInfo());
    }
    
    public static int partChosenToModify() {
        return modifyPartIndex;
    }   

    public static int productChosenToModify() {
        return modifyProductIndex;
    }
    
    @FXML
    private TableView<Part> partsTable;
    @FXML
    private TableColumn<Part, Integer> partID;
    @FXML
    private TableColumn<Part, String> partName;
    @FXML
    private TableColumn<Part, Integer> InventoryLevel;
    @FXML
    private TableColumn<Part, Double> pricePerUnit;
    
    
    @FXML
    private Button addParts;
    @FXML
    private Button searchParts;
    @FXML
    private TextField searchPartsText;        
    @FXML
    private Button modifyParts;
    @FXML
    private Button deleteParts;
    
    
   
    @FXML
    private TableView<Product> productsTable;
    @FXML
    private TableColumn<Product, Integer> productID;
    @FXML
    private TableColumn<Product, String> productName;
    @FXML
    private TableColumn<Product, Integer> productsInventoryLevel;
    @FXML
    private TableColumn<Product, Double> productsPricePerUnit;
    @FXML
    private Button addProducts;
    @FXML
    private Button searchProducts;
    @FXML
    private TextField searchProductsText;     
    @FXML
    private Button modifyProducts;
    @FXML
    private Button deleteProducts;

    
    @FXML
    private Button exit;

    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        if(!exists) {
            Inventory.partList.add(new Inhouse(0, "Steering Wheel", 232.34, 9, 1, 93, 7463));
            Inventory.partList.add(new Inhouse(9, "Steering Wheel", 232.34, 9, 1, 93, 7463));  
            Inventory.partList.add(new Outsourced(1, "Brakes", 77.78, 8, 1, 20, "Honda"));
            Inventory.partList.add(new Outsourced(2, "Seat Belt", 3.14, 5, 1, 10, "Toyota"));                      
            Inventory.partList.add(new Inhouse(4, "Engine", 5000.24, 5, 2, 18, 777));
            Inventory.productList.add(new Product(0, "Honda Civic", 22345, 15, 5, 30, Inventory.partList.get(0)));
            Inventory.productList.add(new Product(1, "Toyota Camry", 15000, 25, 5, 40, Inventory.partList.get(1)));
            Inventory.partList.add(new Inhouse(3, "Tires", 10.65, 8, 1, 15, 22)); 
            Inventory.productList.add(new Product(2, "Honda Accord", 18000.0, 5, 2, 20, Inventory.partList.get(2)));
            Inventory.productList.add(new Product(3, "Toyota Corrolla", 8940.0, 10, 3, 20, Inventory.partList.get(3)));
            Inventory.partList.add(new Outsourced(7, "Windshield Wiper", 34.65, 16, 1, 76, "Honda"));    
            Inventory.partList.add(new Inhouse(8, "Car Seat", 34.65, 16, 1, 76, 228));  
            Inventory.productList.add(new Product(4, "Toyota Spider", 9360.45, 10, 3, 20, Inventory.partList.get(4)));    
            Inventory.productList.add(new Product(5, "Honda Odessey", 27085, 10, 3, 20, Inventory.partList.get(5)));  
            Inventory.partList.add(new Outsourced(7, "Windshield Wiper", 34.65, 16, 1, 76, "Honda"));             
            exists = true;
        }
        
        partID.setCellValueFactory(cellData -> cellData.getValue().partIDProp().asObject());
        partName.setCellValueFactory(cellData -> cellData.getValue().partNameProp());
        InventoryLevel.setCellValueFactory(cellData -> cellData.getValue().partInStockProp().asObject());
        pricePerUnit.setCellValueFactory(cellData -> cellData.getValue().partPriceProp().asObject());
        partsTable.setItems(Inventory.partList);
        productID.setCellValueFactory(cellData -> cellData.getValue().productIDProp().asObject());
        productName.setCellValueFactory(cellData -> cellData.getValue().productNameProp());
        productsInventoryLevel.setCellValueFactory(cellData -> cellData.getValue().productInStockProp().asObject());
        productsPricePerUnit.setCellValueFactory(cellData -> cellData.getValue().productPriceProp().asObject());
        productsTable.setItems(Inventory.productList);
    }    

    @FXML
    private void addPartsHandler(ActionEvent event) throws IOException {
        Parent addParts = FXMLLoader.load(getClass().getResource("AddPartScreen.fxml"));
        Scene scene = new Scene(addParts);
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setScene(scene);
        window.show();
    }
    
    @FXML
    private void searchPartsHandler(ActionEvent event) {
        ObservableList<Part> searchedPartList = FXCollections.observableArrayList(); 
        String search = searchPartsText.getText().toLowerCase();
        try{
            int itemNum = Integer.parseInt(search);
            for(Part part : Inventory.partList){
                if(part.getPartID() == itemNum){
                    searchedPartList.add(part);
                    partsTable.setItems(searchedPartList);
                }
            }
        }
        catch(NumberFormatException e){
            for(Part part: Inventory.partList){
                if(part.getPartName().toLowerCase().equals(search)){
                    searchedPartList.add(part);
                    partsTable.setItems(searchedPartList);
                }
                else if (search.equals("")) {
                    searchPartsText.setText("");
                    partsTable.setItems(Inventory.partInfo()); 
                    return;
                }
            }         
        }
        if (searchedPartList.isEmpty()) {
               Alert alert = new Alert(Alert.AlertType.INFORMATION);
               alert.setContentText("Please type out the exact Part ID or Part Name.");                    
               alert.showAndWait();
        }        
    }

    @FXML
    private void modifyPartsHandler(ActionEvent event) throws IOException {
        modifyPart = partsTable.getSelectionModel().getSelectedItem();
        if (modifyPart != null) {
            modifyPartIndex = partInfo().indexOf(modifyPart);
            Stage stage; 
            Parent root;
            if(event.getSource()== modifyParts){      
                stage=(Stage) modifyParts.getScene().getWindow();
                root = FXMLLoader.load(getClass().getResource("ModifyPartScreen.fxml"));
            } 
            else{
                stage=(Stage) modifyParts.getScene().getWindow();
                root = FXMLLoader.load(getClass().getResource("FXMLDocument.fxml"));
            }

            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        }
        else {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setContentText("Please choose a part to Modify");
            alert.showAndWait();   
        }        
    }

    @FXML
    private void deletePartsHandler(ActionEvent event) {
        Part selectedPart = partsTable.getSelectionModel().getSelectedItem();
        if (selectedPart != null) {
        
        //Confirm delete
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setContentText("Are you sure you want to delete " + selectedPart.getPartName()+"?");        
        alert.showAndWait()
            
            .filter(response -> response == ButtonType.OK)
            .ifPresent(response -> Inventory.partInfo().remove(selectedPart));

           
        //Update parts table
        partsTable.setItems(Inventory.partInfo());
        
        } 
        else {
            
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setContentText("Please select a part from the table.");

        alert.showAndWait();
        }
    }

    @FXML
    private void addProductsHandler(ActionEvent event) throws IOException {
        Parent addParts = FXMLLoader.load(getClass().getResource("AddProductScreen.fxml"));
        Scene scene = new Scene(addParts);
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setScene(scene);
        window.show();
    }
    
    @FXML
    private void searchProductsHandler(ActionEvent event) {
        ObservableList<Product> searchedProdList = FXCollections.observableArrayList(); 
        String search = searchProductsText.getText().toLowerCase();
        try{
            int itemNum = Integer.parseInt(search);
            for(Product prod : Inventory.productList){
                if(prod.getProductID() == itemNum){
                    searchedProdList.add(prod);
                    productsTable.setItems(searchedProdList);
                }
            }
        }
        catch(NumberFormatException e){
            for(Product prod: Inventory.productList){
                if(prod.getProductName().toLowerCase().equals(search)){
                    searchedProdList.add(prod);
                    productsTable.setItems(searchedProdList);
                }
                else if (search.equals("")) {
                    searchProductsText.setText("");
                    productsTable.setItems(Inventory.prodInfo()); 
                    return;
                }
            }
        }
        if (searchedProdList.isEmpty()){
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setContentText("Please type out the exact Product ID or Product Name.");
            alert.showAndWait();
        }        
    }

    @FXML
    private void modifyProductsHandler(ActionEvent event) throws IOException {
        modifyProduct = productsTable.getSelectionModel().getSelectedItem();
        if (modifyProduct != null) {
            modifyProductIndex = prodInfo().indexOf(modifyProduct);
            Stage stage; 
            Parent root;
            if(event.getSource()== modifyProducts){      
                stage=(Stage) modifyProducts.getScene().getWindow();
                root = FXMLLoader.load(getClass().getResource("ModifyProductScreen.fxml"));
            } 
            else{
                stage=(Stage) modifyProducts.getScene().getWindow();
                root = FXMLLoader.load(getClass().getResource("FXMLDocument.fxml"));
            }
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        }
        else {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setContentText("Please choose a product to Modify");
            alert.showAndWait();   
        }  
    }

    @FXML
    private void deleteProductsHandler(ActionEvent event) {
        Product selectedProduct = productsTable.getSelectionModel().getSelectedItem();
        
        if (selectedProduct != null) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setContentText("Are you sure you want to delete " + selectedProduct.getProductName()+"?");        
            alert.showAndWait()

                        .filter(response -> response == ButtonType.OK)
                        .ifPresent(response -> Inventory.prodInfo().remove(selectedProduct));

            productsTable.setItems(Inventory.prodInfo());

            } 
        else {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setContentText("Please select a product from the table.");

            alert.showAndWait();
        }
    }

    @FXML
    private void exitHandler(ActionEvent event) {
        
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.initModality(Modality.NONE);
        alert.setContentText("Are you sure you want to exit?");
        Optional<ButtonType> result = alert.showAndWait();

        if (result.get() == ButtonType.OK) {
            System.exit(0);
        } 
        
    }    
}
