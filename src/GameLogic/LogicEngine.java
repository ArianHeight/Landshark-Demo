package GameLogic;

import Data.Communication.CollisionDetectedRequest;
import Data.Communication.CollisionResponseRequest;
import Data.Communication.GameEventRequest;
import Data.Communication.GameScript;
import Data.ControlInterface;
import Data.GameObject;
import Data.Structure.PhysicsComponent;

import java.util.Vector;

/*
this class is responsible for the actual game itself
handling everything from what happens to an object on collision
to what happens to a player when their hp hits 0
 */
public class LogicEngine {
    ControlInterface ci_player;
    Vector<GameScript> v_gs_collisionResponseRequests;

    //cstr
    public LogicEngine() {
        //idk what to put
        this.v_gs_collisionResponseRequests = new Vector<GameScript>();
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
        if (gs_script.getData() == "Collision") {
            PhysicsComponent pc_one = ((CollisionDetectedRequest)gs_script).getOne();
            PhysicsComponent pc_two = ((CollisionDetectedRequest)gs_script).getTwo();

            int map = 0;
            int enemy = 0;
            int player = 0;

            String str_tagOne = pc_one.getTag();
            String str_tagTwo = pc_two.getTag();
            if (str_tagOne.equals("Player")) {
                player++;
            }
            else if (str_tagOne.equals("Enemy")) {
                enemy++;
            }
            else if (str_tagOne.equals("Map")) {
                map++;
            }

            if (str_tagTwo.equals("Player")) {
                player++;
            }
            else if (str_tagTwo.equals("Enemy")) {
                enemy++;
            }
            else if (str_tagTwo.equals("Map")) {
                map++;
            }

            if ((enemy == 1 || player == 1) && map == 1) {
                //queue for collisionResponse
                this.v_gs_collisionResponseRequests.add(new CollisionResponseRequest(pc_one, pc_two));
            }
            else if (player == 1 && enemy == 1) { //kill player
                this.runScript(new GameEventRequest("KillPlayer"), go_scene);
            }
        }
    }

    /*
    this method returns a reference to this.v_gs_collisionResponseRequest
     */
    public Vector<GameScript> getCollisionRequestQueue() {
        return this.v_gs_collisionResponseRequests;
    }
}
