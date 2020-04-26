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
  
    protected static Random rnjesus;
    
    public static void main(String[] _args) {
        initialiseGame();
    }
       
    private static void initialiseGame() {
        rnjesus = new Random();
        world = new Location[MAX_X][MAX_Y];        
        // instantiate the world
        for(int x=0; x<MAX_X; ++x) {
            for(int y=0; y<MAX_Y; ++y) {
                world[x][y] = new Location(x, y);
            } // for y
        } // for x
        // instantiate boar
        boar = new Boar();
        if(boar==null) System.out.println("Error: Boar did not initialise");
        // instantiate adventurer last, spawn somewhere on the north beach
        adventurer = new Adventurer(rnjesus.nextInt(MAX_X), 0);
    } // initialiseGaaaaame

    protected static void endGame() {
        adventurer.die();
        boar.die();
        System.out.println("Thanks for playing!");
    }
    
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
   
\* SPOILERS * SPOILERS * SPOILERS * SPOILERS * SPOILERS * SPOILERS * SPOILERS */