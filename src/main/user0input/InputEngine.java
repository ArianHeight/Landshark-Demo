package main.user0input;

import main.data.communication.GameScript;
import main.game0logic.KeyBindings;
import main.utility.DataProcessor;

import java.util.Observable;
import java.util.Observer;
import java.util.Vector;

/*

TODO insert description of what class does here

 */
public class InputEngine implements Observer {
    //member vars
    private ConsoleWatch cwListener;
    private Vector<GameScript> scripts;
    private GameKeyInput gkiListener;

    //cstr
    public InputEngine() {
        this.cwListener = new ConsoleWatch(this); //makes a linked cw obj
        this.scripts = new Vector<GameScript>();
        gkiListener = new GameKeyInput();

        KeyBindings.createKeyBindings(); //make the key bindings
    }

    /*

    main update method that is called, will return a ref to the vector of game scripts to the calling class

     */
    public Vector<GameScript> run() {
        this.scripts.clear(); //clears the scripts
        this.cwListener.run(); //tells the console watch to listen
        this.gkiListener.refresh(this.scripts); //gives this.scripts as an output to the listener's refresh method

        return this.scripts; //outputs the scripts
    }

    /*
    update method to input engine for updating it when receiving notification from linked Observables
     */
    @Override
    public void update(Observable o, Object arg) {
        if (o == this.cwListener) {
            //only for updates from cwListener
            switch ((int) arg) {
                case(GameScript.LOG_DATA): //gets error data and puts it in queue
                    DataProcessor.processErrorData(this.scripts, this.cwListener.getErrors());
                    break;
                case(GameScript.PROCESS_DATA): //gets process requests and puts it in queue
                    DataProcessor.processStringData(this.scripts, this.cwListener.getInputs());
                    break;
            }
        }
    }

    /*
    this method returns a reference to the gkiListener
     */
    public GameKeyInput getKeyHandler() {
        return gkiListener;
    }
}