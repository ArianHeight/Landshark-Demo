/*

Entry point for the program
Main will probably call Tester
Main will definitely call GameEngine to start the game execution

 */

import model.system.*;

public class Main {
    //entry method, args is cmd line arguments
    public static void main(String[] args) {
        Tester.run();

        GameEngine e = new GameEngine();
        e.startEngine("./Game/system/Save/game_active.sav");

        e.run();

        e.endEngine();
        System.out.println("Engine finished running, ending program..."); //placeholder line

    }
}
