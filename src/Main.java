/*

Entry point for the program
Main will probably call Tester
Main will definitely call GameEngine to start the game execution

 */

import System.GameEngine;

public class Main {
    //entry method, args is cmd line arguments
    public static void main(String args[]) {
        GameEngine e = new GameEngine();
        e.startEngine("GameData/game.sav");

        e.run();

        e.endEngine();;
        System.out.println("Engine finished running, ending program..."); //placeholder line
    }
}
