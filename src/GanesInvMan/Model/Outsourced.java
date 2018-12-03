package GanesInvMan.Model;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Outsourced extends Part {
    
    private final StringProperty compName = new SimpleStringProperty("");
    
    public Outsourced() {
        this(0, "", 0.0, 0, 0, 0, "");
    }
    
    public Outsourced(int partID, String name, double cost, int inStock, int min, int max, String compName) {
        super(partID, name, cost, inStock, min, max);
        setCompName(compName);
    }
    
    public void setCompName(String compName) {
        this.compName.set(compName);
    }
    
    public String getCompName() {
        return compName.get();
    }
      
    public StringProperty compNameProp() {
        return compName;
    }
    
}
