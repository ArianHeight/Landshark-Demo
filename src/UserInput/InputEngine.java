package UserInput;

import Data.Communication.GameScript;
import Utility.DataProcessor;

import java.util.Observable;
import java.util.Observer;
import java.util.Vector;

/*

TODO insert description of what class does here

 */
public class InputEngine implements Observer{
    //member vars
    private ConsoleWatch cw_listener;
    private Vector<GameScript> v_gs_scripts;

    //cstr
    public InputEngine() {
        this.cw_listener = new ConsoleWatch(this); //makes a linked cw obj
        this.v_gs_scripts = new Vector<GameScript>();
    }

    /*

    main update method that is called, will return a ref to the vector of game scripts to the calling class

     */
    public Vector<GameScript> run() {
        this.v_gs_scripts.clear(); //clears the scripts
        this.cw_listener.run(); //tells the console watch to listen

        return this.v_gs_scripts; //outputs the scripts
    }

    /*
    update method to input engine for updating it when receiving notification from linked Observables
     */
    @Override
    public void update(Observable o, Object arg) {
        if (o == this.cw_listener) {
            //only for updates from cw_listener
            switch((int) arg) {
                case(GameScript.LOG_DATA): //gets error data and puts it in queue
                    DataProcessor.processErrorData(this.v_gs_scripts, this.cw_listener.getErrors());
                    break;
                case(GameScript.PROCESS_DATA): //gets process requests and puts it in queue
                    DataProcessor.processStringData(this.v_gs_scripts, this.cw_listener.getInputs());
                    break;
            }
        }
    }
}
