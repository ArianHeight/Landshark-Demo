package model.system;

import model.data.GameObject;
import model.data.GameScene;
import model.data.communication.CollisionResponseRequest;
import model.data.communication.GameScript;
import model.data.communication.LogRequest;
import model.game0logic.LogicEngine;
import model.io.IoEngine;
import model.physics.PhysicsEngine;
import ui.render.RenderEngine;
import ui.user0input.InputEngine;

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
    private IoEngine fileCommunicator;
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
        this.fileCommunicator = new IoEngine();
        this.timer = new TimeProcessor();
        this.logicProcessor = new LogicEngine();
        this.scriptProcessor = new ScriptProcessor();

        this.gameloop = true;
        this.scriptQueue = new Vector<GameScript>();
        this.sceneGraph = new GameScene();
    }

    //dstr would go here, but java is a thing so..........

    /*

    starting the engine
    does all the initializing stuff to do with the game

     */
    public int startEngine(String fileLoc) {
        this.scriptQueue.add(new LogRequest("Game Engine Initializing...")); //starts the engine

        this.scriptQueue.add(new LogRequest("Creating Window Context...")); //opens the game window
        String tempLog = this.renderer.openWindow();
        if (!tempLog.equals("")) {
            this.scriptQueue.add(new LogRequest(tempLog)); //log if there is error
        }

        this.scriptQueue.add(new LogRequest("Attempting to link keyboard handler to window context..."));
        this.renderer.addKeyListenerToWindow(this.inputProcessor.getKeyHandler());
        this.scriptQueue.add(new LogRequest("Attempting to link mouse handler to window context..."));
        this.renderer.addMouseListenerToWindow(this.inputProcessor.getMouseHandler());

        this.scriptQueue.add(new LogRequest("Initializing game..."));
        this.logicProcessor.startGame(this.sceneGraph, this.scriptQueue, this.fileCommunicator);

        return 0;
    }


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
            default:
                return this.processScriptOne(script);
        }

        return true;
    }

    //follow up for the processScript method to pass checkstyle
    private boolean processScriptOne(GameScript script) {
        switch (script.getCmd()) {
            case GameScript.COLLISION_RESPONSE:
                this.physEngine.doCollisionResponse(((CollisionResponseRequest)script).getOne(),
                        ((CollisionResponseRequest)script).getTwo());
                break;
            case GameScript.MOUSE_LOCATION:
                this.physEngine.addMouesLocToQueue(script);
                break;
            default:
                return false;
        }

        return true;
    }

    //process all scripts in queue and clear the queue
    public void processAllScripts() {
        for (GameScript gsTemp : this.scriptQueue) {
            this.processScript(gsTemp);
        }
        this.scriptQueue.clear();
    }

    /*

    the main running method that is called every single game tickS
    updates everything and calls render

     */
    public void run() {
        while (this.gameloop) {
            this.scriptQueue.addAll(this.inputProcessor.run()); //runs the input processor
            this.timer.tick();
            this.logicProcessor.logicUpdate(sceneGraph, this.timer.getTimeElapsed());
            this.physEngine.doScenePhysics(this.sceneGraph, this.scriptQueue, this.timer.getTimeElapsed()); //do physics

            this.processAllScripts();
            this.scriptProcessor.processScriptsInQueue(this.scriptQueue);

            //grab the stuff that needs to have collision responses computed
            this.scriptQueue.addAll(this.logicProcessor.getCollisionRequestQueue());
            this.processAllScripts();

            //draws to window
            this.renderer.renderSceneToWindow(this.sceneGraph, this.scriptQueue, this.timer.getTimeElapsed());

            //log
            this.fileCommunicator.logAll();
        }

        this.renderer.closeWindow(); //exit code
    }

    /*

    does all the cleanup for the engine

     */
    public void endEngine() {
        this.processScript(new LogRequest("Closing system..."));
        this.fileCommunicator.closeSystem(); //closes the system
        this.scriptQueue.clear();
    }

    //adds a script to queue
    public void addScript(GameScript script) {
        this.scriptQueue.add(script);
    }
}
