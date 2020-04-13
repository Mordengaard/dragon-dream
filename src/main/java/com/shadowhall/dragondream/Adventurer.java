package com.shadowhall.dragondream;

import static com.shadowhall.dragondream.Game.MAX_X;
import static com.shadowhall.dragondream.Game.MAX_Y;
import static com.shadowhall.dragondream.Game.world;
import java.util.Arrays;
import java.util.Scanner;
import org.apache.commons.lang.WordUtils;    

public class Adventurer {

    private int xPos, yPos, zPos = 0;
    
    private Item carriedItem;
    private String name;
    private boolean isPlaying;
    private boolean confirmEndGame;
    
    protected Adventurer(int _xPos, int _yPos) {
        this.xPos = _xPos;
        this.yPos = _yPos;
        isPlaying = true;
        confirmEndGame = false;
        inputAdventurerName();
        look();
        do {
            parseCommand(handleInput());
        } while(isPlaying);
    }
    
    protected void setName(String _name) {
        name = _name;
    }
    
    protected String getName() {
        return name;
    }
    
    protected boolean isCarrying() {
        return carriedItem!=null;
    }
    
    protected Item getCarriedItem() {
        return carriedItem;
    }
    
    protected void setCarriedItem(Item _item) {
        carriedItem = _item;
    }   
    
    private void inputAdventurerName() {
        String input = handleInput("What is thy name, adventurer?\n> ");
        if(input.equals("")) input = "Adventurer";
        this.setName(input);
    }    
    
    private String handleInput() {
        return handleInput(": ");
    }
    
    // i feel like this could be in adventurer... or be its own command object
    // let's do a <command> <context> pattern
    // not happy with the skip boolean, splitting out the command parser
    private String handleInput(String _text) {
        Scanner scanner = new Scanner(System.in);
        System.out.print(WordUtils.wrap(_text,80));
        String input = scanner.nextLine();
        return input;
    }
    
    
    private void parseCommand(String _input) {
        String[] splitInput = _input.split(" ",2);
        String command = splitInput[0].toLowerCase();
        String argument;
        if(splitInput.length==2) argument = splitInput[1];
        else argument = "";
        switch(command) {
            case "north"  :
            case "south"  :
            case "east"   : 
            case "west"   : 
            case "n"      : 
            case "s"      :
            case "e"      :
            case "w"      : tryMove(command); break;
            case "help"   :
            case "?"      :
            case "hint"   : showHelp(); break;
            case "exit"   : // should probably fire a confirmation somewhere, type it twice easiest
            case "quit"   : if(confirmEndGame) endGame();
                            else {
                                System.out.println("Type '"+command+"' again to confirm.");
                                confirmEndGame=true;
                            }
                            break;                          
            case "examine": // examine <detail>
                            examine(argument);
                            break;
            case "look"   : // need support for 'look at' at some point
                            look(argument); 
                            break;
            case "name"   : editLocationName(argument);
                            break;
            case "desc"   : editLocationDescription(argument);
                            break;
            case "add"    : addDetail(argument);
                            break;
            case "remove" : removeDetail(argument);
                            break;
            case "get"    : // pickUpItem;
            case "drop"   : // dropItem
                            System.out.println("Command not implemented.");
                            break;
            //case "initialise" : initialiseWorld(); break;
            default :
                System.out.println("Sorry, I don't understand.");
        }
        if(!command.equals("exit") && !command.equals("quit")) confirmEndGame = false;
    }
    
    private void tryMove(String _direction) {
        // check to see if there's an exit in _direction
        // world.location... realised I don't need a world object      
        // map[adventurer.xPos][adventurer.yPos].hasExit(_direction)
        // or exits at this point
        
        // first pass at "movement"
        boolean hasMoved = false;
        switch (_direction.substring(0,1)) {
            case "n" : 
                if(yPos>0) {
                    --yPos;
                    hasMoved = true;
                } 
                break;
            case "e" :
                if(xPos<Game.MAX_X-1) {
                    ++xPos;
                    hasMoved = true;
                }
                break;
            case "s" :
                if(yPos<Game.MAX_Y-1) {
                    ++yPos;
                    hasMoved = true;
                }
                break;
            case "w" :
                if(xPos>0) {
                    --xPos;
                    hasMoved = true;
                }
                break;
            default :
                System.out.println("Invalid direction '" + _direction + "'.");
        }
        if(!hasMoved) System.out.println("You cannot go that way.");
        else look();
    }
    
    private void examine(String _arg) {
        if(_arg.equals("")) {
            System.out.println("Examine what?");
            return;
        }
        System.out.println(WordUtils.wrap(Game.world[xPos][yPos].getDetail(_arg),80));
    }
    
    private void look() {
        look("");
    }
    
    private void look(String _arg) {
        if(_arg.equals("")) {
            // display the current location description
            // System.out.print("["+xPos+","+yPos+"] ");
            // System.out.println(Game.world[xPos][yPos].getName());
            System.out.println(WordUtils.wrap(Game.world[xPos][yPos].getDescription(),80));
        }
        if(_arg.startsWith("at ")) {
            examine(_arg.substring(3));
        }
    }
    
    // ngl these commands are only for me so I'm not gonna error check it
    private void editLocationName(String _arg) {
        Game.world[xPos][yPos].setName(_arg);
    }
    
    private void editLocationDescription(String _arg) {
        Game.world[xPos][yPos].setDescription(_arg);
    }
    
    private void addDetail(String _arg) {
        String keyword = _arg.split(" ",2)[0];
        String text = _arg.split(" ",2)[1];
        Game.world[xPos][yPos].addDetail(keyword,text);
    }
    
    private void removeDetail(String _arg) {
        Game.world[xPos][yPos].removeDetail(_arg);
    }
    
    private void showHelp() {
        System.out.println("Sorry, " + this.getName() + ", but you have ventured beyond the borders of assistance.");
    }

    private void endGame() { // wasn't as good as infinity war, come@me
        System.out.println("Goodbye, " + this.getName() + ".");
        isPlaying = false;
    }

   /* 
      N
      + 0 1 2 3 4 X
      0 ! . . ~ .   ! = abandoned lighthouse        . = beach
      1 % % % ~ ^   ^ = ruined fisherman's hut      ~ = stream
      2 % % & ~ %   & = clearing                    % = forest
      3 # % % ~ %   # = tower                       = = cliff
      4 = @ = ~ =   @ = dormant lava pool
      Y
    */ 
    
    // rough and ready initialisation
    private static void initialiseWorld() {
        String tempName = "";
        String tempDesc = "";
        for(int x=0;x<MAX_X;++x) {
            for(int y=0;y<MAX_Y;++y) {
                if(y==0) {
                    tempName = "beach";
                    tempDesc = "You are standing on a beach of beautiful but monotonous golden sand that seems to stretch across the north of the island.  To the south is a forest, and rising above it the smoking cone of a volcano.  At the western end of the beach you can see a tall lighthouse jutting upwards.";
                } else
                if(y>0 && y<MAX_Y-2) {
                    tempName = "forest";
                    tempDesc = "The interior of the island is choked with a surprisingly dense and steamy forest. A myriad of biting insects suffuse the air.  In the canopy above birds sing in ";
                } else                
                if(y==MAX_Y-1) {
                    tempName = "cliff";
                    tempDesc = "An ominous cliff of fractured basalt looms suddenly out of the trees, blocking further exploration south.";
                }
                
            if(x==3) {
                    tempName += " and stream";
                    tempDesc += "  A steaming stream of heated water runs north toward the ocean, sending tendrils of mist through the trees.";
                }
                world[x][y].setName(tempName);
                world[x][y].setDescription(tempDesc);
                world[x][y].saveLocation();
                //At the center of the island sits a lava lake, but it is nearly dormant.\nA cradle of impossibly thin metal holds a huge DRAGON EGG suspended over the lake.
            }
        }
    }        
}

