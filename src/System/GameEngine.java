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
    private PhysicsEngine pe_physEngine;
    private RenderEngine re_renderer;
    private InputEngine ie_inputProcessor;
    private IOEngine ioe_fileCommunicator;
    private TimeProcessor tp_timer;
    private LogicEngine le_logicProcessor;
    private ScriptProcessor sp_scriptProcessor;

    private boolean b_gameloop;
    private Vector<GameScript> v_gs_scriptQueue; //a queue to store all scripts waiting to be executed
    private GameObject go_scene; //the scene graph for the entire game

    public GameEngine() {
        //cstr

        //initialize all member vars TODO maybe move some to startEngine
        this.pe_physEngine = new PhysicsEngine();
        this.re_renderer = new RenderEngine();
        this.ie_inputProcessor = new InputEngine();
        this.ioe_fileCommunicator = new IOEngine();
        this.tp_timer = new TimeProcessor();
        this.le_logicProcessor = new LogicEngine();
        this.sp_scriptProcessor = new ScriptProcessor();

        this.b_gameloop = true;
        this.v_gs_scriptQueue = new Vector<GameScript>();
        this.go_scene = new GameScene();
    }

    //dstr would go here, but java is a thing so..........

    /*
    TODO description
     */
    public int startEngine(String str_fileLoc) {
        this.v_gs_scriptQueue.add(new LogRequest("Game Engine Initializing...")); //starts the engine

        this.v_gs_scriptQueue.add(new LogRequest("Creating Window Context...")); //opens the game window
        String str_tempLog = this.re_renderer.openWindow();
        if (!str_tempLog.equals("")) {
            this.v_gs_scriptQueue.add(new LogRequest(str_tempLog)); //log if there is error
        }

        this.v_gs_scriptQueue.add(new LogRequest("Attempting to link keyboard handler to window context..."));
        this.re_renderer.addKeyListenerToWindow(this.ie_inputProcessor.getKeyHandler());
        this.v_gs_scriptQueue.add(new LogRequest("Link established..."));

        //TODO flesh out this part
        this.le_logicProcessor.startGame(this.go_scene, this.v_gs_scriptQueue);

        //TODO temp test code
        Vector<GameScore> v_gsc_scores = new Vector<GameScore>();
        this.ioe_fileCommunicator.readGameScore(v_gsc_scores);
        for (GameScore score : v_gsc_scores) {
            System.out.println(score);
        }

        return 0;
    }

    //TODO dis is temp home for linker sort of method??
    public boolean processScript(GameScript gs_script) {
        switch (gs_script.getCmd()) {
            case GameScript.LOG_DATA:
                this.tp_timer.tagScript(gs_script);
                this.ioe_fileCommunicator.processRequest(gs_script);
                break;
            case GameScript.PROCESS_DATA:
                this.sp_scriptProcessor.addScriptToProcess(gs_script);
                break;
            case GameScript.GAME_EVENT:
                this.le_logicProcessor.runScript(gs_script, this.go_scene);
                break;
            case GameScript.END_PROGRAM:
                this.b_gameloop = false; //end program here
                break;
            case GameScript.COLLISION_RESPONSE:
                this.pe_physEngine.doCollisionResponse(((CollisionResponseRequest)gs_script).getOne(), ((CollisionResponseRequest)gs_script).getTwo());
            default:
                return false;
        }

        return true;
    }

    /*

    TODO description...

     */
    public void run() {
        while (this.b_gameloop) {
            this.v_gs_scriptQueue.addAll(this.ie_inputProcessor.run()); //runs the input processor
            this.tp_timer.tick();
            this.le_logicProcessor.logicUpdate(go_scene, this.tp_timer.getTimeElapsed());
            this.pe_physEngine.doScenePhysics(this.go_scene, this.v_gs_scriptQueue, this.tp_timer.getTimeElapsed()); //do physics

            for (GameScript gs_temp : this.v_gs_scriptQueue) {
                this.processScript(gs_temp);
            }
            this.v_gs_scriptQueue.clear();
            this.sp_scriptProcessor.processScriptsInQueue(this.v_gs_scriptQueue);

            this.v_gs_scriptQueue.addAll(this.le_logicProcessor.getCollisionRequestQueue()); //grab the stuff that needs to have collision responses computed
            for (GameScript gs_temp : this.v_gs_scriptQueue) {
                this.processScript(gs_temp);
            }
            this.v_gs_scriptQueue.clear();

            //TODO update position from physics component
            this.re_renderer.renderSceneToWindow(this.go_scene, this.v_gs_scriptQueue); //draws to window
        }

        this.re_renderer.closeWindow(); //exit code
    }

    /*
    TODO description and the actual functions
     */
    public void endEngine() {
        this.ioe_fileCommunicator.closeSystem(); //closes the system
    }
}
