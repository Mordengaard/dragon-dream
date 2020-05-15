package com.shadowhall.dragondream;

import java.util.Random;

// SPOILER Warning: Spoilers at end of this file.

public class Game {  

    final static String SAVE_DIR = "world/";
    final static int MAX_X = 5;
    final static int MAX_Y = 5;
    
    protected static Adventurer adventurer;
    protected static Location[][] world;
    protected static Boar boar;
    protected static Thing elemental1,elemental2,elemental3;
  
    protected static Random rnjesus;
    
    public static void main(String[] _args) {

        initialiseWorld();
        initialiseGame();
    }
       
    private static void initialiseGame() {
        
        rnjesus = new Random();
        
        // instantiate boar
        boar = new Boar();
        
        // instantiate elementals FOR NOW
        elemental1 = new Thing();
        elemental2 = new Thing();
        elemental3 = new Thing();
        
        // instantiate adventurer last, spawn somewhere on the north beach
        adventurer = new Adventurer(rnjesus.nextInt(MAX_X), 0);
    }

    protected static void endGame() {

        boar.die();
        System.out.println("Thanks for playing!");
    }
    
/* SPOILERS * SPOILERS * SPOILERS * SPOILERS * SPOILERS * SPOILERS * SPOILERS *\

The player has arrived on a volcanic island, although the volcano is long dormant.
At the base of the volcano is a cold lava lake, in which sits the egg of a dragon.
Sadly with the volcano's death, the egg has no chance to hatch, and the parent has
long since departed, as dragons are mostly solitary creatures.  Fortunately for
both the dragon within the egg, and the player, a number of juvenile Fire 
Elementals have recently taken up residence on the island.

A lighthouse standing on the island's northwestern tip, used to warn ships of
the razor sharp reefs around the island, contains a spare bulb, large and unwieldy,
but somewhere a Fire Elemental might be persuaded to briefly call home.

Collect the elementals in the bulb and deliver them to the lava lake.  In return
they will heat the lake up enough to hatch the egg, only to cause the volcano to
erupt!  Fortunately the dragon saves the player and they fly off together.
   
    /* 
      N
      + 0 1 2 3 4 X
      0 ! . . ~ .   ! = abandoned lighthouse        . = beach
      1 % % % ~ ^   ^ = ruined fisherman's hut      ~ = stream
      2 % % & ~ %   & = clearing                    % = forest
      3 # % % ~ %   # = tower                       = = cliff
      4 = @ = ~ =   @ = dormant lava pool
      Y
      
\* SPOILERS * SPOILERS * SPOILERS * SPOILERS * SPOILERS * SPOILERS * SPOILERS */
        
    // rough and ready initialisation
    private static void initialiseWorld() {
        String tempName = "";
        String tempDesc = "";

        world = new Location[MAX_X][MAX_Y];        
        
        // instantiate the world
        for(int x=0; x<MAX_X; ++x) {
            for(int y=0; y<MAX_Y; ++y) {
                world[x][y] = new Location(x, y);
                if(y==0) { // y = 0
                    tempName = "beach";
                    tempDesc = "You are standing on a beach of beautiful but monotonous black sand stretching across the north of the island.  To the south is a forest, and rising above it the smoking cone of a volcano.";
                    if(x>0) tempDesc += "  At the western end of the beach you can see a tall lighthouse jutting upwards.";
                    else tempDesc += "  A lighthouse stands here, built atop the broken basalt to ward off ships.";
                } else if(y>0 && y<MAX_Y-1) { // y = 1 -> 3
                    if(x==2) {
                        tempName = "clearing";
                        tempDesc = "A sudden clearing emerges from the trees.  Scattered spots of snouted soil suggest some porcine presence somewhat recently.";
                    } else {
                        tempName = "forest";
                        tempDesc = "The interior of the island is choked with a surprisingly dense and steamy forest. In the canopy above, birds sing descant to the drone of the biting insects that suffuse the air.";
                    }                    
                } else if(y==MAX_Y-1) { // y = 4
                    if(x==1) {
                        tempName = "lava pool";
                        tempDesc = "A small volcanic vent has at one time reached the surface here, creating a small lake of mostly solidified lava.  A curiously regular protusion erupts from the very center.";
                    } else {
                        tempName = "cliff";
                        tempDesc = "An ominous escarpment of fractured basalt looms suddenly from behind the trees, blocking further exploration south.";
                    }
                }
                
                // put a stream running north/south
                if(x==3) {
                    tempName += " and stream";
                    tempDesc += "  A slightly steaming stream of heated water gambolls northward toward the sea, here, sending slender tendrils of mist betwixt the trees.";
                }
                world[x][y].shortName = tempName;
                world[x][y].longDesc = tempDesc;
                //world[x][y].saveLocation();
            }
        }
    }   
    
/* SPOILERS * SPOILERS * SPOILERS * SPOILERS * SPOILERS * SPOILERS * SPOILERS */
    
}