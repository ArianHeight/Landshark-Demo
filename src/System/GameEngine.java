package System;

import Data.Communication.CollisionResponseRequest;
import Data.Communication.GameScript;
import Data.Communication.LogRequest;
import Data.GameObject;
import Data.GameScene;
import GameLogic.GameScore;
import GameLogic.LogicEngine;
import IO.IOEngine;
import Physics.PhysicsEngine;
import Render.RenderEngine;
import UserInput.InputEngine;

import java.util.Vector;

/*

The GameEngine class creates all other engines and manages the internal data flow between all engines
starts the game and ends it
keeps track of all assets in use, and all GameObjects created

 */
public class GameEngine {
    //member vars
    private PhysicsEngine physEngine;
    private RenderEngine renderer;
    private InputEngine inputProcessor;
    private IOEngine fileCommunicator;
    private TimeProcessor timer;
    private LogicEngine logicProcessor;
    private ScriptProcessor scriptProcessor;

    private boolean gameloop;
    private Vector<GameScript> scriptQueue; //a queue to store all scripts waiting to be executed
    private GameObject sceneGraph; //the scene graph for the entire game

    public GameEngine() {
        //cstr

        //initialize all member vars TODO maybe move some to startEngine
        this.physEngine = new PhysicsEngine();
        this.renderer = new RenderEngine();
        this.inputProcessor = new InputEngine();
        this.fileCommunicator = new IOEngine();
        this.timer = new TimeProcessor();
        this.logicProcessor = new LogicEngine();
        this.scriptProcessor = new ScriptProcessor();

        this.gameloop = true;
        this.scriptQueue = new Vector<GameScript>();
        this.sceneGraph = new GameScene();
    }

    //dstr would go here, but java is a thing so..........

    /*
    TODO description
     */
    public int startEngine(String fileLoc) {
        this.scriptQueue.add(new LogRequest("Game Engine Initializing...")); //starts the engine

        this.scriptQueue.add(new LogRequest("Creating Window Context...")); //opens the game window
        String str_tempLog = this.renderer.openWindow();
        if (!str_tempLog.equals("")) {
            this.scriptQueue.add(new LogRequest(str_tempLog)); //log if there is error
        }

        this.scriptQueue.add(new LogRequest("Attempting to link keyboard handler to window context..."));
        this.renderer.addKeyListenerToWindow(this.inputProcessor.getKeyHandler());
        this.scriptQueue.add(new LogRequest("Link established..."));

        //TODO flesh out this part
        this.logicProcessor.startGame(this.sceneGraph, this.scriptQueue);

        //TODO temp test code
        Vector<GameScore> v_gsc_scores = new Vector<GameScore>();
        this.fileCommunicator.readGameScore(v_gsc_scores);
        for (GameScore score : v_gsc_scores) {
            System.out.println(score);
        }

        return 0;
    }

    //TODO dis is temp home for linker sort of method??
    public boolean processScript(GameScript script) {
        switch (script.getCmd()) {
            case GameScript.LOG_DATA:
                this.timer.tagScript(script);
                this.fileCommunicator.processRequest(script);
                break;
            case GameScript.PROCESS_DATA:
                this.scriptProcessor.addScriptToProcess(script);
                break;
            case GameScript.GAME_EVENT:
                this.logicProcessor.runScript(script, this.sceneGraph);
                break;
            case GameScript.END_PROGRAM:
                this.gameloop = false; //end program here
                break;
            case GameScript.COLLISION_RESPONSE:
                this.physEngine.doCollisionResponse(((CollisionResponseRequest)script).getOne(), ((CollisionResponseRequest)script).getTwo());
            default:
                return false;
        }

        return true;
    }

    /*

    TODO description...

     */
    public void run() {
        while (this.gameloop) {
            this.scriptQueue.addAll(this.inputProcessor.run()); //runs the input processor
            this.timer.tick();
            this.logicProcessor.logicUpdate(sceneGraph, this.timer.getTimeElapsed());
            this.physEngine.doScenePhysics(this.sceneGraph, this.scriptQueue, this.timer.getTimeElapsed()); //do physics

            for (GameScript gs_temp : this.scriptQueue) {
                this.processScript(gs_temp);
            }
            this.scriptQueue.clear();
            this.scriptProcessor.processScriptsInQueue(this.scriptQueue);

            this.scriptQueue.addAll(this.logicProcessor.getCollisionRequestQueue()); //grab the stuff that needs to have collision responses computed
            for (GameScript gs_temp : this.scriptQueue) {
                this.processScript(gs_temp);
            }
            this.scriptQueue.clear();

            //TODO update position from physics component
            this.renderer.renderSceneToWindow(this.sceneGraph, this.scriptQueue, this.timer.getTimeElapsed()); //draws to window
        }

        this.renderer.closeWindow(); //exit code
    }

    /*
    TODO description and the actual functions
     */
    public void endEngine() {
        this.fileCommunicator.closeSystem(); //closes the system
    }
}
