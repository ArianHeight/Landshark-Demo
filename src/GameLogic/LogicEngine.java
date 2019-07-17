package GameLogic;

import Data.Communication.CollisionDetectedRequest;
import Data.Communication.CollisionResponseRequest;
import Data.Communication.GameEventRequest;
import Data.Communication.GameScript;
import Data.ControlInterface;
import Data.GameObject;
import Data.Structure.PhysicsComponent;
import Data.Structure.VisualTextureComponent;
import Utility.RandomNumberGenerator;

import javax.swing.*;
import java.awt.*;
import java.util.Vector;

/*
this class is responsible for the actual game itself
handling everything from what happens to an object on collision
to what happens to a player when their hp hits 0
 */
public class LogicEngine {
    ControlInterface ci_player;
    Vector<GameScript> v_gs_collisionResponseRequests;
    double d_acceleration;
    double d_vel;
    double d_distance;
    boolean b_paused;
    double d_timeSinceLastGen;
    double d_timeSinceLastSecond;

    //cstr
    public LogicEngine() {
        //idk what to put
        this.v_gs_collisionResponseRequests = new Vector<GameScript>();
        this.d_acceleration = 0.05;
        this.d_vel = -5.0;
        this.d_distance = 0.0;
        this.b_paused = false;
        this.d_timeSinceLastGen = 0.0;
        this.d_timeSinceLastSecond = 0.0;
    }

    /*
    this method takes an initialized empty GameObject and populates it at the start of the game

    also takes a vector of GameScripts to dump any log requests and whatnot
     */
    public void startGame(GameObject go_scene, Vector<GameScript> v_gs_scripts) {
        //TODO write the actual game now
        this.d_acceleration = 0.05;
        this.d_vel = -10.0;
        this.d_distance = 0.0;
        this.b_paused = false;

        go_scene.addComponent(new VisualTextureComponent(new ImageIcon("./Game/Assets/Textures/blank.png").getImage(), new Rectangle(0, 0, 1280, 720), null, 2));
        LandSharkPlayer lsp_player = new LandSharkPlayer();
        this.ci_player = lsp_player;
        go_scene.addGameObject(lsp_player);
        go_scene.addGameObject(new LandSharkMap());
        lsp_player.addGameObject(new SpiderEnemy(this.d_vel)); //TODO temp code
    }

    /*
    This method is called once for every GameEventScript

    most of the code for main updates to the game will be run from here
     */
    public void runScript(GameScript gs_script, GameObject go_scene) {
        if (gs_script.getData().endsWith("Player")) {
            this.ci_player.inputResponse(gs_script.getData());
        }
        else if (gs_script.getData() == "Collision") { //todo maybe move somewhere else
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
    this method is called once per frame
    updates all game logic related stuff
     */
    public void logicUpdate(GameObject go_scene, double d_timeElapsed) {
        this.v_gs_collisionResponseRequests.clear();

        if (!this.b_paused) {
            //TODO may move enemy spawning code to another place
            double d_randomNum = RandomNumberGenerator.randomBetween(0, 100);
            d_randomNum *= Math.atan(2.0 * (this.d_timeSinceLastGen - 1 - 2.5 * this.d_vel));
            if (d_randomNum > 90 && this.d_timeSinceLastSecond >= 1.0) {
                ((LandSharkPlayer)this.ci_player).addGameObject(new SpiderEnemy(this.d_vel));
                this.d_timeSinceLastGen = 0.0;
            }
            else if (this.d_timeSinceLastSecond >= 1.0) {
                this.d_timeSinceLastSecond = 0.0;
            }
            else {
                this.d_timeSinceLastGen += d_timeElapsed;
                this.d_timeSinceLastSecond += d_timeElapsed;
            }

            this.d_vel -= this.d_acceleration * d_timeElapsed;
            this.d_distance -= this.d_vel * d_timeElapsed;

            go_scene.updateObj();
        }

        if (!this.ci_player.isAlive()) {
            this.b_paused = true;
            //TODO call endgame
        }
    }

    /*
    this method returns a reference to this.v_gs_collisionResponseRequest
     */
    public Vector<GameScript> getCollisionRequestQueue() {
        //debug code
        return this.v_gs_collisionResponseRequests;
    }
}
