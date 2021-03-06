package com.shadowhall.dragondream;

// SPOILER Warning: Spoilers at end of this file.

import static com.shadowhall.dragondream.Game.MAX_X;
import static com.shadowhall.dragondream.Game.MAX_Y;
import static com.shadowhall.dragondream.Game.world;
import java.util.Scanner;
import org.apache.commons.lang.WordUtils;    

// some living things are Adventurers
// nothing should inherit Adventurer, so it's final; this is our player object
final class Adventurer extends Living {

    // properties
    private boolean isPlaying;
    private boolean confirmEndGame;
    private int moveCounter;
    private Container inventory;
    private Thing carriedThing;
    private Location currentLoc;
    
    // constructor
    protected Adventurer(int _xPos, int _yPos) {
        
        xPos = _xPos;
        yPos = _yPos;
        isPlaying = true;
        confirmEndGame = false;
        
        moveCounter = 0;
        
        currentLoc = Game.world[xPos][yPos];
        currentLoc.receiveThing(this);

        inputAdventurerName();
        look();
        do {
            parseCommand(handleInput());
        } while(isPlaying);
        Game.endGame();
    }
    
    // methods
    private void inputAdventurerName() {
        
        String input = handleInput("What is thy name, adventurer\n? ");
        if(input.equals("")) input = "Adventurer";
        this.shortName = input;
    }    
    
    // method to handle non-custom prompted input
    private String handleInput() {
        
        return handleInput(": ");
    }
    
    // "i feel like this could be in adventurer... or be its own command object"
    // let's do a <command> <context> pattern
    // overloaded method to handle custom prompts
    private String handleInput(String _text) {
        
        Scanner scanner = new Scanner(System.in);
        System.out.print(WordUtils.wrap(_text,80));
        String input = scanner.nextLine();
        return input;
    }
    
    // "not happy with the skip boolean, splitting out the command parser"
    private void parseCommand(String _input) {
        
        String[] splitInput = _input.split(" ",2);
        String command = splitInput[0].toLowerCase();
        String argument;

        if(splitInput.length==2) 
            argument = splitInput[1];
        else 
            argument = "";

        ++moveCounter;

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
            case "exit"   : // "should probably fire a confirmation somewhere, type it twice easiest"
            case "quit"   : /*if(confirmEndGame)*/ endGame(); // man this gets annoying in testing
                            /*else {
                                System.out.println("Type '" + command + "' again to confirm.");
                                confirmEndGame=true;
                            }*/
                            break;                          
            case "examine": // "examine <detail>"
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
            case "blink"  : blink();
                            break;
            case "get"    : // pickUpItem;
            case "drop"   : // dropItem
                            System.out.println("Command not implemented.");
                            break;
//            case "initialise" : initialiseWorld(); break;
            default :
                System.out.println("Sorry, I don't understand.");
                --moveCounter;
        }
        // reset confirmendgame flag if it's set and not confirmed
        if(confirmEndGame && !command.equals("exit") && !command.equals("quit")) 
            confirmEndGame = false;
    }
    
    private void moveTo(Location _newLoc) {
        currentLoc.removeThing(this);
        _newLoc.receiveThing(this);
        currentLoc = _newLoc;
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
        else {
            moveTo(Game.world[xPos][yPos]);
            look();
            Game.boar.heartBeat();
        }
    }
    
    protected void bump() {
        // just pick a random direction and move in it
    }
    
    private void examine(String _arg) {
        if(_arg.equals("")) {
            System.out.println("Examine what?");
            return;
        }
        System.out.println(WordUtils.wrap(Game.world[xPos][yPos].getDetail(_arg), 80));
    }
    
    private void look() {
        look("");
    }
    
    // jack-knifed brain//I'm getting nowhere again
    
    private void look(String _arg) {
        if(_arg.equals("")) {
            // display the current location description
            // System.out.print("["+xPos+","+yPos+"] ");
            // System.out.println(Game.world[xPos][yPos].getName());
            
            System.out.println(WordUtils.wrap(currentLoc.longDesc, 80));
            
            String tmp = currentLoc.contentsDescription();
            if(!tmp.equals("")) System.out.println(WordUtils.wrap(tmp, 80));
        }
        if(_arg.startsWith("at ")) {
            examine(_arg.substring(3));
        }
    }
    
    // ngl these commands are only for me so I'm not gonna error check it
    private void editLocationName(String _arg) {
        Game.world[xPos][yPos].shortName = _arg;
    }
    
    private void editLocationDescription(String _arg) {
        Game.world[xPos][yPos].longDesc = _arg;
    }
    
    private void addDetail(String _arg) {
        String tmp[] = _arg.split(" ",2);
        String keyword = tmp[0];
        String text = tmp[1];
        Game.world[xPos][yPos].addDetail(keyword, text);
    }
    
    private void removeDetail(String _arg) {
        Game.world[xPos][yPos].removeDetail(_arg);
    }
    
    private void showHelp() {
        System.out.println("Sorry, " + shortName + ", but you have ventured beyond the borders of assistance.");
    }

    private void endGame() { // wasn't as good as infinity war, come@me
        System.out.println("Goodbye, " + shortName + "!  You made " + moveCounter + " moves!");
        isPlaying = false;
    }
    
/* SPOILERS * SPOILERS * SPOILERS * SPOILERS * SPOILERS * SPOILERS * SPOILERS */         
    
    private void blink() {
        System.out.println("///////(//((((((((((/(//((,");
        System.out.println("**//*////(//(((((((((######((/((((");                                              
        System.out.println(",****/*////(((((##(########(((((((/((");                                           
        System.out.println(".,,*****////(((((##########(((((((((((((");                                        
        System.out.println("....,,**///(/(#((####((#######((((((##(((((");                                     
        System.out.println(".,,,,****//((/(((((((((((######(##(######((((((,,,,,,,,,,,,,..");                  
        System.out.println(",,,,,,****//(((((((((###(##################((((###///*****,,,,..");                
        System.out.println(",,,,,*****/////((((((((((#######%###%#########(((###((/((*/*,,,,,...");            
        System.out.println("...,,,,***////(((((((((((########%##%%%##########(((##(((((((((////****,,,");      
        System.out.println("...,,,,****/////(/(((((((#########%%#%%###########(#((((((((#(((((((((///*,");     
        System.out.println("..,,,,,,****/////((/((((((((#####(#%%##%%%################((((((/(((((////**");    
        System.out.println("..,,,,,,,,******////(((//((((##########%%%##%#%%%%###########(((((((//**////*");   
        System.out.println(" ...,,,,,,,,,****////((/((((((((########%#%#%%%#%#%##%#######(((###(///*/////*");  
        System.out.println("........,,,,,,,******///((((((((((##(##########%######%#####((##(#####(**///**");  
        System.out.println("   ...,,,.,,,,,,,****///////((((#(((#############%%##%###############%#(((/***");  
        System.out.println("   ........,,,,,,,*****//////////((((((((/(((#(##(((######%######(/########(/*."); 
        System.out.println("..  ......,,,,,,,**********//////((/(////((//((((((((##########(((/##########((,");
        System.out.println("       .....,,,,,,,,,,,,*******////////////////////((((((((((###((((((((######((");
        System.out.println("           ....,,,,,,,,,,,,,,***************,,,,,**//*///((((/(((((/////////////");
        System.out.println("              ......,,,,,,,,,,,,,**,,,,....... ....,,**/**(((((((((/,*////////*.");
        System.out.println("                  .....,,,,,,,,,,,,,,,.      ....   ....,*////((#((( ..****///*"); 
        System.out.println("                    ....,,,,,,,,,,,,,,,.              ...,*/((###(((.,///////*");  
        System.out.println("                      ......,,,,,.,,,,,*               .,,*//(((((((/,*/////*");
        System.out.println("                             ...,,,******             .,,***//((((((/**,*/*.");
        System.out.println("                             ...,***,*,**,             .,,,**///(((/**///*");
        System.out.println("                               ..,,*,,****         ,,.. ..,,,***////****");
        System.out.println("                              ..,,********.,,****,,******,..,*******..");
        System.out.println("                              ..,,,,,,,***,****//*.,********,,,,,");
        System.out.println("                              ..,,,,,****,****/////,,***,,,..");
        System.out.println("                               ..,,,***/,*,*******,,,,,.");
        System.out.println("                                 ,,,,,");
        System.out.println("You blinked, and a Weeping Angel takes your remaining Time.  Don't blink.");
        endGame();
    }    
}

