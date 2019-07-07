/*

Entry point for the program
Main will probably call Tester
Main will definitely call GameEngine to start the game execution

 */

import System.GameEngine;
import UserInput.Console;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

public class Main {
    //entry method, args is cmd line arguments
    public static void main(String args[]) {
        System.out.println("hello there, program starting..."); //placeholder line

        GameEngine e = new GameEngine();
        e.startEngine("GameData/game.sav");

        System.out.println("Engine finished running, ending program..."); //placeholder line

        ArrayList<String> inputs = new ArrayList<String>();
        ArrayList<String> errors = new ArrayList<String>();

        while (true) {

            if (Console.getConsoleOutput(inputs, errors)) {
                for (String str : inputs) {
                    if (str.equals("end")) {
                        break;
                    } else {
                        System.out.println(str);
                    }
                }
            }
        }
    }
}
