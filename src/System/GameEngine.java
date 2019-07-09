package System;

import Data.Communication.*;
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

    private boolean b_gameloop;
    private Vector<GameScript> v_gs_scriptQueue; //a queue to store all scripts waiting to be executed

    public GameEngine() {
        //cstr

        //initialize all member vars
        this.pe_physEngine = new PhysicsEngine();
        this.re_renderer = new RenderEngine();
        this.ie_inputProcessor = new InputEngine();
        this.ioe_fileCommunicator = new IOEngine();

        this.b_gameloop = true;
        this.v_gs_scriptQueue = new Vector<GameScript>();
    }

    //dstr would go here, but java is a thing so..........

    /*
    TODO description
     */
    public int startEngine(String str_fileLoc) {
        this.v_gs_scriptQueue.add(new LogRequest("Game Engine Initiallizing...")); //starts the engine

        return 0;
    }

    //TODO dis is temp home for linker sort of method??
    public void processScript(GameScript gs_script) {
        switch (gs_script.getCmd()) {
            case GameScript.LOG_DATA:
                this.ioe_fileCommunicator.processRequest(gs_script);
                break;
        }
    }

    /*

    TODO descrription...

     */
    public void run() {
        while (this.b_gameloop) {
            this.v_gs_scriptQueue.addAll(this.ie_inputProcessor.run()); //runs the input processor

            for (GameScript gs_temp : this.v_gs_scriptQueue) {
                this.processScript(gs_temp);

                //temp code
                if (gs_temp.getData().equals("end")) {
                    return; //temp exit code
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
