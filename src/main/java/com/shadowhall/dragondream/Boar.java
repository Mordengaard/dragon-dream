package com.shadowhall.dragondream;

public class Boar extends Living {
    
    // properties
    private int angerLevel; // start with zero anger
        
    // constructor   
    protected Boar() {
        xPos = 2;
        yPos = 2;
        angerLevel = 0;
        //System.out.println("Boar initialised.");
    }
    
    @Override
    protected void heartBeat() {
        // if we're in the same place as the adventurer, increase our anger each beat
        if(Game.adventurer==null) return;
        if(this.xPos==Game.adventurer.xPos && this.yPos==Game.adventurer.yPos) {
            increaseAnger();
        } else {
            decreaseAnger();
            tryMove();
        }
        System.out.println("Boar is at " + xPos + "," + yPos);
    }
       
    private void increaseAnger() {
        if(angerLevel<3)
            ++angerLevel;
        switch(angerLevel) {
            case 0 : System.out.println("The boar is snuffling around looking for food."); break; // snuffle about
            case 1 : System.out.println("The boar senses you and it stops and stares fixedly at you."); break; // spot adventurer
            case 2 : System.out.println("The boar suddenly surges into a charge toward you, squealing furiously!"); break; // charge begins
            case 3 : System.out.println("The boar crashes into you!  In a blind panic you stumble away trying to avoid the slashing tusks!"); 
                    Game.adventurer.bump();
                    
                    break; // impact - bump player in a random direction
        }
    }
    
    private void decreaseAnger() {
        if(angerLevel>0)
            --angerLevel;
    }
    
    private void tryMove() {
        boolean hasMoved = false;
        System.out.println("boar is trying to move");
        switch (Game.rnjesus.nextInt(4)) {
            // limit boar to the forest locations (y1-3)
            case 0 : if(yPos>1) { --yPos; hasMoved=true; } break; 
            case 1 : if(xPos<4) { ++xPos; hasMoved=true; } break;
            case 2 : if(yPos<3) { ++yPos; hasMoved=true; } break;
            case 3 : if(xPos>0) { --xPos; hasMoved=true; } break;
        }
        if(this.xPos==Game.adventurer.xPos && this.yPos==Game.adventurer.yPos) {
            System.out.println("A large wild boar arrives.");
        } 
    }
}