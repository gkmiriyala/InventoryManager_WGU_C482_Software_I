package GanesInvMan.Model;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public abstract class Part {
    
    private final IntegerProperty partID = new SimpleIntegerProperty(0);
    private final StringProperty partName = new SimpleStringProperty("");
    private final DoubleProperty partCost = new SimpleDoubleProperty(0.0);
    private final IntegerProperty partInStock = new SimpleIntegerProperty(0);
    private final IntegerProperty partMin = new SimpleIntegerProperty(0);
    private final IntegerProperty partMax = new SimpleIntegerProperty(0);
    
    // CONSTRUCTORS
    public Part(){
        this(0, "", 0.0, 0, 0, 0);
    }
    
    public Part(int partID, String name, double cost, int inStock, int min, int max) {
        setPartID(partID);
        setPartName(name);
        setPartCost(cost);
        setPartInStock(inStock);
        setPartMin(min);
        setPartMax(max);
    }

    
    public void setPartID(int partID) {
        this.partID.set(partID);
    }
    
    public void setPartName(String name) {
        this.partName.set(name);
    }
    
    public void setPartCost(double price) {
        this.partCost.set(price);
    }
    
    public void setPartInStock(int inStock) {
        this.partInStock.set(inStock);
    }
    
    public void setPartMin(int min) {
        this.partMin.set(min);
    }
    
    public void setPartMax(int max) {
        this.partMax.set(max);
    }

    public int getPartID() {
        return partID.get();
    }

    public String getPartName() {
        return partName.get();
    }
    
    public double getPartCost() {
        return partCost.get();
    }
    
    public int getPartInStock() {
        return partInStock.get();
    }
    
    public int getPartMin() {
        return partMin.get();
    }
    
    public int getPartMax() {
        return partMax.get();
    }
            
    public IntegerProperty partIDProp() {
        return partID;
    }
    
    public StringProperty partNameProp() {
        return partName;
    }
    
    public DoubleProperty partPriceProp() {
        return partCost;
    }
    
    public IntegerProperty partInStockProp() {
        return partInStock;
    }
    
    public IntegerProperty partMinProp() {
        return partMin;
    }
    
    public IntegerProperty partMaxProp() {
        return partMax;
    }
    
    public static String partValidification(String name, int min, int max, int inv, double price, String message) {
        if (name.length() == 0) {
            message = "Part name cannot be empty.\n";
        }        
        else if (inv > max || inv < min) {
            message = "Inventory level of the part must be between the minimum and maximum values.\n";
        }
        else if (max < min) {
            message = "Maximum inventory value must be greater than the minimum value.\n";
        }
        else if (inv < 0) {
            message = "Inventory level of the part must be greater than 0.\n";
        }
        else if (price <= 0) {
            message = "Part cost must be greater than 0.\n";
        }
        return message;
    }
    
}
