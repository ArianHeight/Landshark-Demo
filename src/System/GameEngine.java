package System;

import Render.RenderEngine;
import Physics.PhysicsEngine;
import UserInput.InputEngine;
import IO.IOEngine;

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

    public GameEngine() {
        //cstr

        //initialize all member vars
        this.pe_physEngine = new PhysicsEngine();
        this.re_renderer = new RenderEngine();
        this.ie_inputProcessor = new InputEngine();
    }

    //dstr would go here, but java is a thing so..........

    /*

     */
    public int startEngine(String str_fileLoc) {
        return 0;
    }
}
