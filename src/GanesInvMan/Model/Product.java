package GanesInvMan.Model;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Product {
    
    private final IntegerProperty productID = new SimpleIntegerProperty(0);
    private final StringProperty productName = new SimpleStringProperty("");
    private final DoubleProperty productPrice = new SimpleDoubleProperty(0.0);
    private final IntegerProperty productInStock = new SimpleIntegerProperty(0);
    private final IntegerProperty productMin = new SimpleIntegerProperty(0);
    private final IntegerProperty productMax = new SimpleIntegerProperty(0);
    private ObservableList<Part> productAssociatedParts = FXCollections.observableArrayList();
    
    public Product(){
        this(0, "", 0.0, 0, 0, 0, null);
    }
    
    public Product(int productID, String productName, double productCost, int productInStock, int productMin, int productMax, Part productFirstPart) {
        setProductID(productID);
        setProductName(productName);
        setProductCost(productCost);
        setProductInStock(productInStock);
        setProductMin(productMin);
        setProductMax(productMax);
        productAssociatedParts.add(productFirstPart);
    }
 
    public void setProductID(int productID) {
        this.productID.set(productID);
    }
    
    public void setProductName(String name) {
        this.productName.set(name);
    }
    
    public void setProductCost(double price) {
        this.productPrice.set(price);
    }
    
    public void setProductInStock(int inStock) {
        this.productInStock.set(inStock);
    }
    
    public void setProductMin(int min) {
        this.productMin.set(min);
    }
    
    public void setProductMax(int max) {
        this.productMax.set(max);
    }
    
    public void setProductAssociatedParts(ObservableList<Part> associatedParts) {
        this.productAssociatedParts = associatedParts;
    }
    
    public int getProductID() {
        return productID.get();
    }

    public String getProductName() {
        return productName.get();
    }
    
    public double getProductPrice() {
        return productPrice.get();
    }
    
    public int getProductInStock() {
        return productInStock.get();
    }
    
    public int getProductMin() {
        return productMin.get();
    }
    
    public int getProductMax() {
        return productMax.get();
    }
    
    public ObservableList getProductAssociatedParts() {
        return productAssociatedParts;
    }
    
    public IntegerProperty productIDProp() {
        return productID;
    }
    
    public StringProperty productNameProp() {
        return productName;
    }
    
    public DoubleProperty productPriceProp() {
        return productPrice;
    }
    
    public IntegerProperty productInStockProp() {
        return productInStock;
    }
    
    public IntegerProperty productMinProp() {
        return productMin;
    }
    
    public IntegerProperty productMaxProp() {
        return productMax;
    }
    
    public void addProductAssociatedPart(Part part) {
        productAssociatedParts.add(part);
    }

    public int associatedPartLookUp(String partSearchString) {
        int partIndex;
        if (isInteger(partSearchString)) {
            for (int i = 0; i < productAssociatedParts.size(); i++) {
                if (Integer.parseInt(partSearchString) == productAssociatedParts.get(i).getPartID()) {
                    return partIndex = i;
                }
            }
        } else {
            for (int i = 0; i < productAssociatedParts.size(); i++) {
                if (partSearchString.equals(productAssociatedParts.get(i).getPartName())) {
                    return partIndex = i;
                }
            }
        }
        return -1;
    }
    
    public void removeAssociatedPart(Part part) {
        productAssociatedParts.remove(part);
    }
    
    public boolean isInteger(String input) {
        try {
            
            Integer.parseInt(input);
            return true;
            
        } 
        catch (Exception e) {
            
            return false;
        
        }
    }

    public static String ProductValidification(String name, int min, int max, int inv, double price, ObservableList<Part> associatedParts, String message) {
        
        double totalPriceOfParts = 0.0;
        
        if (name.length() == 0) {
            message = "Product name cannot be empty.\n";
        }
        
        if (inv > max || inv < min) {
            message = "Inventory level of the part must be between the minimum and maximum values.\n";
        }
        
        if (max < min) {
            message = "Maximum inventory value must be greater than the minimum inventory level value.\n";
        }
        
        if (associatedParts.size() < 1) {
            message = "The product must have at least one part.\n";
        }
        
        for (int i = 0; i < associatedParts.size(); i++) {
            totalPriceOfParts += associatedParts.get(i).getPartCost();
        }
        if (price < totalPriceOfParts) {
            message = "Product cost must be greater than or equal to the cost of the parts.\n";
        }
        
        if (inv <= 0) {
            message = "Product Inventory level of the product must be greater than zero.\n";
        }
        
        if (price <= 0) {
            message = "Product cost must be greater than zero.\n";
        }

        return message;
    }
}
