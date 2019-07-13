package GameLogic;

import Data.Communication.GameScript;
import Data.ControlInterface;
import Data.GameObject;

import java.util.Vector;

/*
this class is responsible for the actual game itself
handling everything from what happens to an object on collision
to what happens to a player when their hp hits 0
 */
public class LogicEngine {
    ControlInterface ci_player;

    //cstr
    public LogicEngine() {
        //idk what to put
    }

    /*
    this method takes an initialized empty GameObject and populates it at the start of the game

    also takes a vector of GameScripts to dump any log requests and whatnot
     */
    public void startGame(GameObject go_scene, Vector<GameScript> v_gs_scripts) {
        //TODO write the actual game now
        LandSharkPlayer lsp_player = new LandSharkPlayer();
        this.ci_player = lsp_player;
        go_scene.addGameObject(lsp_player);
    }

    /*
    This method is called once for every GameEventScript

    most of the code for main updates to the game will be run from here
     */
    public void runScript(GameScript gs_script, GameObject go_scene) {
        if (gs_script.getData().endsWith("Player")) {
            this.ci_player.inputResponse(gs_script.getData());
        }
    }
}
