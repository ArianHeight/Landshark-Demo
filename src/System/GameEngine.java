package System;

import Data.Communication.*;
import Data.GameObject;
import Data.GameScene;
import GameLogic.LogicEngine;
import Render.RenderEngine;
import Physics.PhysicsEngine;
import UserInput.InputEngine;
import IO.IOEngine;

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

        return 0;
    }

    //TODO dis is temp home for linker sort of method??
    public void processScript(GameScript gs_script) {
        switch (gs_script.getCmd()) {
            case GameScript.LOG_DATA:
                this.tp_timer.tagScript(gs_script);
                this.ioe_fileCommunicator.processRequest(gs_script);
                break;
            case GameScript.END_PROGRAM:
                this.b_gameloop = false; //end program here
                break;
        }
    }

    /*

    TODO description...

     */
    public void run() {
        while (this.b_gameloop) {
            this.v_gs_scriptQueue.addAll(this.ie_inputProcessor.run()); //runs the input processor
            //Game Logic
            this.pe_physEngine.doSceneCollisionDetection(this.go_scene, this.v_gs_scriptQueue); //do physics
            //more game logic
            this.re_renderer.renderSceneToWindow(this.go_scene, this.v_gs_scriptQueue); //draws to window

            for (GameScript gs_temp : this.v_gs_scriptQueue) {
                this.processScript(gs_temp);

                //TODO remove temp code
                if (gs_temp.getData().equals("end")) {
                    this.re_renderer.closeWindow();
                    return; //temp exit code
                }
                if (gs_temp.getData().equals("Jump")) {
                    System.out.println("jump");
                }
            }
            this.v_gs_scriptQueue.clear();
        }
    }

    /*
    TODO description and the actual functions
     */
    public void endEngine() {
        this.ioe_fileCommunicator.closeSystem(); //closes the system
    }
}
