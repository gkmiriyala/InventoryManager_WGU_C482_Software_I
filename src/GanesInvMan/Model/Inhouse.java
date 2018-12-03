package GanesInvMan.Model;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;

public class Inhouse extends Part {
    
    private final IntegerProperty machID = new SimpleIntegerProperty(0);
    
    public Inhouse() {
        this(0, "", 0.0, 0, 0, 0, 0);
    }
      
    public Inhouse(int partID, String name, double cost, int inStock, int min, int max, int machID) {
        super(partID, name, cost, inStock, min, max);
        setMachID(machID);
    }
    
    public void setMachID(int machineID) {
        this.machID.set(machineID);
    }
    
    public int getMachID() {
        return machID.get();
    }
      
    public IntegerProperty machIDProp() {
        return machID;
    }
    
}
