package com.shadowhall.dragondream;

import java.io.*;
import java.util.*;

// some Containers are actually Locations
public class Location extends Container implements Serializable {

    // properties
    private final transient String FILENAME; // transient (non-serialized) constant
    private Map details; // storing room "detail" as keyword and description pairs
    
    // constructor
    protected Location(int _x, int _y) {
        
        FILENAME = Game.SAVE_DIR + String.format("%dx%d.loc",_x,_y);               
        shortName = "a featureless void";
        longDesc = "A featureless void.";
        details = new HashMap();
        //loadLocation();
    }
        
    // serialise/deserialise methods
    private void loadLocation() {
        
        try (FileInputStream file = new FileInputStream(FILENAME); 
             ObjectInputStream in = new ObjectInputStream(file)) {
            
            Location data;
            data = (Location)in.readObject();
            //System.out.println("Location loaded from " + filename);           
            this.shortName = data.shortName;
            this.longDesc = data.longDesc;
            this.details = data.details;
        } 
        
        catch (IOException|ClassNotFoundException ex) {
            
            System.out.println("Load error: " + ex.getMessage());
        } 
    }
    
    protected void saveLocation() {
        
        try (FileOutputStream file = new FileOutputStream(FILENAME); 
             ObjectOutputStream out = new ObjectOutputStream(file)) {          
            
            out.writeObject(this);
            out.close();
            file.close();
            System.out.println("Location saved as " + FILENAME);
        }
        
        catch (IOException ex) {
            
            System.out.println("Save error: " + ex.getMessage());
        }
    }
              
    protected void addDetail(String _keyword, String _text) {

        details.put(_keyword, _text);
        //saveLocation();
    }
    
    protected String getDetail(String _keyword) {
        
        return details.get(_keyword).toString();
    }
    
    protected void removeDetail(String _keyword) {

        details.remove(_keyword);
        //saveLocation();
    }
}
