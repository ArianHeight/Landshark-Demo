/*

Entry point for the program
Main will probably call Tester
Main will definitely call GameEngine to start the game execution

 */

import System.GameEngine;

public class Main {
    //entry method, args is cmd line arguments
    public static void main(String args[]) {
        System.out.println("hello there, program starting..."); //placeholder line

        GameEngine e = new GameEngine();
        e.startEngine();

        System.out.println("Engine finished running, ending program..."); //placeholder line
    }
}
