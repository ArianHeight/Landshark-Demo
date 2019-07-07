/*

Entry point for the program
Main will probably call Tester
Main will definitely call GameEngine to start the game execution

 */

import System.GameEngine;

import java.io.IOException;

public class Main {
    //entry method, args is cmd line arguments
    public static void main(String args[]) {
        System.out.println("hello there, program starting..."); //placeholder line

        GameEngine e = new GameEngine();
        e.startEngine("GameData/game.sav");

        System.out.println("Engine finished running, ending program..."); //placeholder line

        //temp lines
        java.io.BufferedReader br = new java.io.BufferedReader(new java.io.InputStreamReader(System.in));
        String line;
        try {
            while (true) {
                line = br.readLine();
                if (line != null) {
                    if (line.equals("end")) {
                        break; //break out of the while loop
                    }
                    System.out.println(line);
                }
            }
        }
        catch(IOException error) {
            System.out.println("IO error occurred");
        }
    }
}
