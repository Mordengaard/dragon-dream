package com.shadowhall.dragondream;

// some things are Living Things
public class Living extends Thing {
    
    // properties
    protected int xPos, yPos; // zPos, tPos etc.
    
    // like health and mood etc
    
    // constructor
    public Living() {
        
        shortName = "living thing";
        longDesc = "This is a Living Thing.";
    }
    
    // methods
    /**
     * call this function once things are initialised I guess
     */
    protected void wake() {
        
    }
    
    /**
     * this function gets called every 'heartbeat' interval
     */
    protected void heartBeat() {
        
    }
    /**
     * cleanup timers etc
     */
    protected void die() {
        
    }
    
}
