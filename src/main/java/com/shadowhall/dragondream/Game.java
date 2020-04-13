package com.shadowhall.dragondream;

import java.util.Random;

public class Game {  

    final static int MAX_X = 5;
    final static int MAX_Y = 5;
    
    private static Adventurer adventurer;
    protected static Location[][] world;
    private static Item item1, item2, item3;
  
    protected static Random rnjesus;
    
    public static void main(String[] _args) {
        initialiseGame();
    }
       
    private static void initialiseGame() {
        rnjesus = new Random();
        world = new Location[MAX_X][MAX_Y];
        for(int x=0;x<MAX_X;++x) {
            for(int y=0;y<MAX_Y;++y) {
                world[x][y] = new Location(x,y);
            } // for y
        } // for x
        item1 = new Item();
        item2 = new Item();
        item3 = new Item();
        // initialise adventurer last, spawn somewhere on the north beach
        adventurer = new Adventurer(rnjesus.nextInt(MAX_X),0);
    } // initialiseGaame
    
}