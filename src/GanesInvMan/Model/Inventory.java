package GanesInvMan.Model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Inventory {
    
    private static int countPartID;
    private static int countProductID;
    
    public static ObservableList<Part> partList = FXCollections.observableArrayList();
    public static ObservableList<Product> productList = FXCollections.observableArrayList();
    
    public Inventory() {
        
    }
    
    public static void addPart(Part part) {
        partList.add(part);
    }       
    
    public static ObservableList<Part> partInfo() {
        return partList;
    }
    
    public static int partIDCount() {
        countPartID++;
        return countPartID;
    }
    
    public static int partLookUp(String partSearchString) {
        int partIndex;
        if (isInteger(partSearchString)) {
            for (int i = 0; i < partList.size(); i++) {
                if (Integer.parseInt(partSearchString) == partList.get(i).getPartID()) {
                    return partIndex = i;
                }
            }
        } 
        else {
            for (int i = 0; i < partList.size(); i++) {
                if (partSearchString.equals(partList.get(i).getPartName())) {
                    return partIndex = i;
                }
            }
        }
        return -1;
    }
    
    public static void updatePart(int partIndex, Part part) {
        partList.set(partIndex, part);
    }

    public void removePart(Part part) {
        partList.remove(part);
    }
    
    public static void addProd(Product product) {
        productList.add(product);
    }    

    public static ObservableList<Product> prodInfo() {
        return productList;
    }
    
    public static int prodIDCount() {
        countProductID++;
        return countProductID;
    }    

    public int prodLookUp(String productSearchString) {
        int index;
        if (isInteger(productSearchString)) {
            for (int i = 0; i < productList.size(); i++) {
                if (Integer.parseInt(productSearchString) == productList.get(i).getProductID()) {
                    index = i;
                }
            }
        } 
        else {
            for (int i = 0; i < productList.size(); i++) {
                if (productSearchString.equals(productList.get(i).getProductName())) {
                    index = i;
                }
            }
        }
        return -1;
    }
    
    public static void updateProd(int ID, Product prod) {
        productList.set(ID, prod);
    }    
    
    public void removeProd(Product product) {
        productList.remove(product);
    }
    
    public static boolean isInteger(String input) {
        try {
            
            Integer.parseInt(input);
            return true;
            
        } 
        catch (Exception e) {
            
            return false;
        
        }
    }
   
}
