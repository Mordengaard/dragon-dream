package com.shadowhall.dragondream;

// everything is a Thing
public class Thing {

    // properties
    public String shortName; // every Thing needs a short name and a long desc(ription), which might as well 
    public String longDesc;  // be public since they're purely descriptive, so no need to encapsulate them

    // other properties of Things that could be added later (and encapsulated); VOLUME, MASS
   
    // constructor
    public Thing() {
        shortName = "thing";
        longDesc = "This thing is a Thing.";
    } 
} 
