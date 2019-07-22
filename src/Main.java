/*

Entry point for the program
Main will probably call Tester
Main will definitely call GameEngine to start the game execution

 */

import main.System.*;

public class Main {
    //entry method, args is cmd line arguments
    public static void main(String args[]) {
        Tester.run();

        TimeProcessor t = new TimeProcessor();
        System.out.println(t.tagMsg("I say hi"));

        GameEngine e = new GameEngine();
        e.startEngine("./Game/System/Save/game_active.sav");

        e.run();

        e.endEngine();
        System.out.println("Engine finished running, ending program..."); //placeholder line

    }
}
