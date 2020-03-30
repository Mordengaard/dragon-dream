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

    /* test data produced this layout
      N
      + 0 1 2 3 4 X
      0 ! . . . .
      1 % % % % ^
      2 % % & % %
      3 % % % % %
      4 = # = @ =
      Y
    */
       
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

    /* this was used for initial testing and setting up the data files
                // rough and ready initialisation
                if(x==0 || y==0 || x==MAX_X-1 || y==MAX_Y-1) {
                    map[x][y].setName("beach");
                    map[x][y].setDescription("You are standing on a beach of beautiful but monotonous golden sand that surrounds the island on all sides."); // surprisingly square island
                } else
                if(x==MAX_X/2 && y==MAX_Y/2) {
                    map[x][y].setName("lava lake");
                    map[x][y].setDescription("At the center of the island sits a lava lake, but it is nearly dormant.\nA cradle of impossibly thin metal holds a huge DRAGON EGG suspended over the lake.");
                } else                
                if(x>0 && x<MAX_X-1 && y>0 && y<MAX_X-1) {
                    map[x][y].setName("forest");
                    map[x][y].setDescription("The interior of the island is choked with a surprisingly dense and steamy forest.");
                }
                world[x][y].saveLocation();                   
    */
