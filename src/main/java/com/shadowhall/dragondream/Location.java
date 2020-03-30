package com.shadowhall.dragondream;

import java.io.*;
import java.util.*;

public class Location implements Serializable {
    
    final static String SAVE_DIR = "world/";
    
    private String name;
    private String description;
    private Map details;
    private static Item[] inventory; // static so location inventory isn't serialized
    private final transient String filename; // transient
    
    protected Location(int _x, int _y) {
        filename = SAVE_DIR + String.format("%dx%d.loc",_x,_y);       
        inventory = new Item[0];
        name = "a featureless void";
        description = "A featureless void.";
        details = new HashMap();
        loadLocation();
    }
    
    private void loadLocation() {
        try (FileInputStream file = new FileInputStream(filename); ObjectInputStream in = new ObjectInputStream(file)) {
            Location data;
            data = (Location)in.readObject();
            //System.out.println("Location loaded from " + filename);           
            this.name = data.name;
            this.description = data.description;
            this.details = data.details;
        } catch(IOException|ClassNotFoundException ex) {
            System.out.println("Load error: " + ex.getMessage());
        } 
    }
    
    protected void saveLocation() {
        try (FileOutputStream file = new FileOutputStream(filename); ObjectOutputStream out = new ObjectOutputStream(file)) {            
            out.writeObject(this);
            out.close();
            file.close();
            System.out.println("Location saved as " + filename);
        }
        catch(IOException ex) {
            System.out.println("Save error: " + ex.getMessage());
        }
    }
        
    protected void setName(String _name) {
        name = _name;
        saveLocation();
    }
    
    protected String getName() {
        return name;
        // return name + " (" + filename + ")";
    }
    
    protected void setDescription(String _description) {
        description = _description;
        saveLocation();
    }

    protected String getDescription() {
        return description;
    }
    
    protected void addDetail(String _keyword, String _text) {
        details.put(_keyword, _text);
        saveLocation();
    }
    
    protected String getDetail(String _keyword) {
        return details.get(_keyword).toString();
    }
    
    protected void removeDetail(String _keyword) {
        details.remove(_keyword);
        saveLocation();
    }
      
    // itemDropped event handler
    // itemPickedUp event handler
}
