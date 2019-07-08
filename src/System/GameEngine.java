package System;

import Data.Communication.GameScript;
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

    private Vector<GameScript> v_gs_scriptQueue; //a queue to store all scripts waiting to be executed

    public GameEngine() {
        //cstr

        //initialize all member vars
        this.pe_physEngine = new PhysicsEngine();
        this.re_renderer = new RenderEngine();
        this.ie_inputProcessor = new InputEngine();
        this.ioe_fileCommunicator = new IOEngine();
    }

    //dstr would go here, but java is a thing so..........

    /*

     */
    public int startEngine(String str_fileLoc) {
        return 0;
    }

    /*

    TODO descrription...

     */
    public void run() {
        while (true) {
            this.v_gs_scriptQueue.addAll(this.ie_inputProcessor.run()); //runs the input processor
        }
    }
}
