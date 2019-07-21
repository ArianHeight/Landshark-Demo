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
    ControlInterface player;
    Vector<GameScript> collisionResponseRequests;
    double acceleration;
    double vel;
    double distance;
    boolean paused;
    double timeSinceLastGen;
    double timeSinceLastSecond;

    //cstr
    public LogicEngine() {
        //idk what to put
        this.collisionResponseRequests = new Vector<GameScript>();
        this.acceleration = 0.05;
        this.vel = -5.0;
        this.distance = 0.0;
        this.paused = false;
        this.timeSinceLastGen = 0.0;
        this.timeSinceLastSecond = 0.0;
    }

    /*
    this method takes an initialized empty GameObject and populates it at the start of the game

    also takes a vector of GameScripts to dump any log requests and whatnot
     */
    public void startGame(GameObject scene, Vector<GameScript> scripts) {
        //TODO write the actual game now
        this.acceleration = 0.05;
        this.vel = -10.0;
        this.distance = 0.0;
        this.paused = false;

        scene.addComponent(new VisualTextureComponent(new ImageIcon("./Game/Assets/Textures/backDrop.png").getImage(), new Rectangle(0, 0, 1280, 720), null, 2));
        LandSharkPlayer lspPlayer = new LandSharkPlayer();
        this.player = lspPlayer;
        scene.addGameObject(lspPlayer);
        scene.addGameObject(new LandSharkMap());
        lspPlayer.addGameObject(new SpiderEnemy(this.vel)); //TODO temp code
    }

    /*
    This method is called once for every GameEventScript

    most of the code for main updates to the game will be run from here
     */
    public void runScript(GameScript script, GameObject scene) {
        if (script.getData().endsWith("Player")) {
            this.player.inputResponse(script.getData());
        } else if (script.getData() == "Collision") { //todo maybe move somewhere else
            PhysicsComponent pcOne = ((CollisionDetectedRequest)script).getOne();
            PhysicsComponent pcTwo = ((CollisionDetectedRequest)script).getTwo();

            int map = 0;
            int enemy = 0;
            int player = 0;

            String tagOne = pcOne.getTag();
            String tagTwo = pcTwo.getTag();
            if (tagOne.equals("Player")) {
                player++;
            } else if (tagOne.equals("Enemy")) {
                enemy++;
            } else if (tagOne.equals("Map")) {
                map++;
            }

            if (tagTwo.equals("Player")) {
                player++;
            } else if (tagTwo.equals("Enemy")) {
                enemy++;
            } else if (tagTwo.equals("Map")) {
                map++;
            }

            if ((enemy == 1 || player == 1) && map == 1) {
                //queue for collisionResponse
                this.collisionResponseRequests.add(new CollisionResponseRequest(pcOne, pcTwo));
                if (player == 1 && map == 1) {
                    ((LandSharkPlayer)this.player).setTouchingGroundTrue();
                }
            } else if (player == 1 && enemy == 1) { //kill player
                this.runScript(new GameEventRequest("KillPlayer"), scene);
            }
        }
    }

    /*
    this method is called once per frame
    updates all game logic related stuff
     */
    public void logicUpdate(GameObject scene, double timeElapsed) {
        this.collisionResponseRequests.clear();

        if (!this.paused) {
            //TODO may move enemy spawning code to another place
            double randomNum = RandomNumberGenerator.randomBetween(0, 100);
            randomNum *= Math.atan(2.0 * (this.timeSinceLastGen - 1 - 2.5 * this.vel));
            if (randomNum > 90 && this.timeSinceLastSecond >= 1.0) {
                if (RandomNumberGenerator.randomBool()) {
                    ((LandSharkPlayer) this.player).addGameObject(new SpiderEnemy(this.vel));
                } else {
                    ((LandSharkPlayer) this.player).addGameObject(new DroneEnemy(this.vel));
                }
                this.timeSinceLastGen = 0.0;
            } else if (this.timeSinceLastSecond >= 1.0) {
                this.timeSinceLastSecond = 0.0;
            } else {
                this.timeSinceLastGen += timeElapsed;
                this.timeSinceLastSecond += timeElapsed;
            }

            this.vel -= this.acceleration * timeElapsed;
            this.distance -= this.vel * timeElapsed;

            scene.updateObj();
        }

        if (!this.player.isAlive()) {
            this.paused = true;
            //TODO call endgame
        }
    }

    /*
    this method returns a reference to this.collisionResponseRequest
     */
    public Vector<GameScript> getCollisionRequestQueue() {
        //debug code
        return this.collisionResponseRequests;
    }
}
