package model.game0logic;

import model.data.ControlInterface;
import model.data.GameObject;
import model.data.GameScene;
import model.data.communication.CollisionDetectedRequest;
import model.data.communication.CollisionResponseRequest;
import model.data.communication.GameEventRequest;
import model.data.communication.GameScript;
import model.data.structure.PhysicsComponent;
import model.data.structure.VisualTextureComponent;
import model.io.IoEngine;
import model.utility.Misc;
import model.utility.RandomNumberGenerator;

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
    LandSharkText scoreDisplay;
    GameScene activeGame;
    GameScene pauseMenu;
    ReplayButton button;
    int currentScore;

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
        this.currentScore = 0;
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
    public void startGame(GameObject scene, Vector<GameScript> scripts, IoEngine fileEngine) {
        this.acceleration = 0.05;
        this.vel = -10.0;
        this.distance = 0.0;
        this.paused = false;

        SpiderEnemy.setDefaultAnimation(fileEngine.loadAnimation(
                "./Game/Assets/Animations/walkingSpider.anim", scripts));
        DroneEnemy.setDefaultAnimation(fileEngine.loadAnimation(
                "./Game/Assets/Animations/walkingDrone.anim", scripts));
        LandSharkPlayer.setDefaultAnimation(fileEngine.loadAnimation(
                "./Game/Assets/Animations/walkingShark.anim", scripts));
        LandSharkPlayer.setCrouchAnimation(fileEngine.loadAnimation(
                "./Game/Assets/Animations/crouchShark.anim", scripts));
        ReplayButton.setDefaultTexture(fileEngine.loadTexture(
                "./Game/Assets/Textures/replay.png", scripts));
        LandSharkMap.setDefaultTexture(fileEngine.loadTexture(
                "./Game/Assets/Textures/floor.png", scripts));

        this.makeGameObjs(scene);
    }

    //this method makes all the game objs necessary to play the game
    private void makeGameObjs(GameObject scene) {
        this.activeGame = new GameScene();
        this.pauseMenu = new GameScene();
        scene.addGameObject(this.activeGame);
        scene.addGameObject(this.pauseMenu);
        this.activeGame.addComponent(new VisualTextureComponent(
                new ImageIcon("./Game/Assets/Textures/backDrop.png").getImage(),
                new Rectangle(0, 0, 1280, 720), null, 2));
        LandSharkPlayer lspPlayer = new LandSharkPlayer();
        this.player = lspPlayer;
        this.activeGame.addGameObject(lspPlayer);
        this.activeGame.addGameObject(new LandSharkMap());
        lspPlayer.addGameObject(new SpiderEnemy(this.vel));
        this.scoreDisplay = new LandSharkText();
        lspPlayer.addGameObject(this.scoreDisplay);
        this.button = new ReplayButton();
        this.pauseMenu.addGameObject(this.button);
        this.pauseMenu.freeze();
    }

    /*
    This method is called once for every GameEventScript

    most of the code for main updates to the game will be run from here
     */
    public void runScript(GameScript script, GameObject scene) {
        if (script.getData().endsWith("Player")) {
            this.player.inputResponse(script.getData());
        } else if (script.getData().equals("Collision")) {
            PhysicsComponent pcOne = ((CollisionDetectedRequest)script).getOne();
            PhysicsComponent pcTwo = ((CollisionDetectedRequest)script).getTwo();

            String tagOne = pcOne.getTag();
            String tagTwo = pcTwo.getTag();

            int map = Misc.numEquals(tagOne, tagTwo, "Map");
            int enemy = Misc.numEquals(tagOne, tagTwo, "Enemy");
            int player = Misc.numEquals(tagOne, tagTwo, "Player");

            this.runProperCollisionResponse(map, enemy, player, scene, pcOne, pcTwo);
        } else if (script.getData().equals("TogglePause") && this.player.isAlive()) {
            this.togglePause();
        }
    }

    private void runProperCollisionResponse(int map, int enemy, int player, GameObject scene,
                                            PhysicsComponent pcOne, PhysicsComponent pcTwo) {
        if ((enemy == 1 || player == 1) && map == 1) { //queue for collisionResponse
            this.collisionResponseRequests.add(new CollisionResponseRequest(pcOne, pcTwo));
            if (player == 1 && map == 1) {
                ((LandSharkPlayer)this.player).setTouchingGroundTrue();
            }
        } else if (player == 1 && enemy == 1) { //kill player
            this.runScript(new GameEventRequest("KillPlayer"), scene);
        }
    }

    /*
    this method is called once per frame
    updates all game logic related stuff
     */
    public void logicUpdate(GameObject scene, double timeElapsed) {
        this.collisionResponseRequests.clear();

        if (!this.paused) {
            this.spawnEnemies(scene, timeElapsed);

            this.vel -= this.acceleration * timeElapsed;
            this.distance -= this.vel * timeElapsed;
            this.currentScore = (int) this.distance;
            this.scoreDisplay.setText("Score: " + this.currentScore);

            scene.updateObj();

            if (!this.player.isAlive()) {
                this.paused = false;
                this.togglePause(); //toggle to true
            }
        } else {
            this.checkReplayButton(scene);
        }
    }

    //this method will call restartGame() if the replay button has been pressed
    public void checkReplayButton(GameObject scene) {
        if (this.button.isPressed()) {
            this.restartGame(scene, new Vector<GameScript>()); 
        }
    }

    //method used for spawning enemies
    public void spawnEnemies(GameObject scene, double timeElapsed) {
        double randomNum = RandomNumberGenerator.randomBetween(0, 100);
        randomNum *= Math.atan(2.0 * (this.timeSinceLastGen - 1 - 2.5 * this.vel));

        if (randomNum > 90 && this.timeSinceLastSecond >= 1.0) { //spawn an enemy
            if (RandomNumberGenerator.randomBetween(0, 100) < 40) {
                ((LandSharkPlayer) this.player).addGameObject(new SpiderEnemy(this.vel));
            } else if (RandomNumberGenerator.randomBetween(0, 100) < 60) {
                ((LandSharkPlayer) this.player).addGameObject(new DroneEnemy(this.vel, false));
            } else {
                ((LandSharkPlayer) this.player).addGameObject(new DroneEnemy(this.vel, true));
            }
        }
        if (this.timeSinceLastSecond >= 1.0) { //add time to time since second
            this.timeSinceLastSecond = 0.0;
        }
        //add time to time since last generation/spawn
        this.timeSinceLastGen += timeElapsed;
        this.timeSinceLastSecond += timeElapsed;
    }

    /*
    this method returns a reference to this.collisionResponseRequest
     */
    public Vector<GameScript> getCollisionRequestQueue() {
        //debug code
        return this.collisionResponseRequests;
    }

    //toggles the pause
    public void togglePause() {
        if (this.paused) {
            this.paused = false;
            this.activeGame.unfreeze();
            this.pauseMenu.freeze();
        } else {
            this.paused = true;
            this.activeGame.freeze();
            this.pauseMenu.unfreeze();
        }
    }

    /*
    this method takes the scenegraph and resets it, restarting the game from scratch
     */
    private void restartGame(GameObject scene, Vector<GameScript> scripts) {
        this.activeGame.setForDelete();
        this.pauseMenu.setForDelete();
        scene.updateObj(); //deletes the game

        this.acceleration = 0.05;
        this.vel = -10.0;
        this.distance = 0.0;
        this.paused = false;

        this.makeGameObjs(scene); //remakes everything
    }
}
