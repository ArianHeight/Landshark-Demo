package IO;

import Data.Communication.GameScript;

/*

TODO insert description of what class does here

 */
public class IOEngine {
    private GameFileWriter gfw_logWriter; //responsible for writing game logs

    //cstr
    public IOEngine() {
        this.gfw_logWriter = new GameFileWriter("./Game/System/Logs/game_active.log");

    }

    public void outputToConsole(GameScript gs_data) {
        System.out.println(gs_data.getData());
    }
}
