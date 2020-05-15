package com.shadowhall.dragondream;

import java.util.List;
import java.util.ArrayList;
import org.apache.commons.lang.StringUtils;

// some things Contain other Things
public class Container extends Thing {
    
    // properties
    private List<Thing> contents;
    // some kind of configurable list of types of Things the container will accept
    // or deny & methods to allow container.allows(Thing) perhaps argh rabbit hole
    
    // constructor
    public Container() {
        
        shortName = "container";
        longDesc = "A Container for Things.";
        contents = new ArrayList<>();
    }   
    
    // Things can be removed from Containers
    protected void removeThing(Thing _thing) {
        if(contents.contains(_thing))
            contents.remove(_thing);
        else
            System.out.println("Error attempting to remove " + _thing);
        // adjust container mass
    }
    
    // Things can be received by Containers
    protected void receiveThing(Thing _thing) {

        if(!contents.contains(_thing))
            contents.add(_thing);
        else
            System.out.println("Error; " + _thing + " already exists in " + this);
        // adjust container mass 
    }

    // Containers know if they have a Thing in them    
    protected boolean containsThing(Thing _thing) {

        return contents.contains(_thing);
    }
    
    // Containers know how much space they have left inside
    /* protected float hasSpaceFor(Thing _thing) {
        // this rabbit hole needed to be capped
    } */
    
    // Containers can return their whole contents list
    protected List<Thing> getContents() {

        return contents;
    }
    
    // Containers can generate a description of their contents
    protected String contentsDescription() {

        String tmpString = "";

        // foreach Thing in contents
        for (Thing thing : contents)
            tmpString += thing.shortName + ", ";
        
        // StringUtils version of substring handles null and empty strings
        tmpString = StringUtils.substring(tmpString, 0, tmpString.length()-2);
        return tmpString;
    }       
}
