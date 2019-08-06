package main.ui.user0input;

import main.model.data.communication.GameScript;
import main.model.game0logic.KeyBindings;
import main.model.utility.DataProcessor;

import java.util.Observable;
import java.util.Observer;
import java.util.Vector;

/*

This class takes care of all forms of user inputs

 */
public class InputEngine implements Observer {
    //member vars
    private ConsoleWatch cwListener;
    private Vector<GameScript> scripts;
    private GameKeyInput gkiListener;
    private GameMouseInput gmiListener;

    //cstr
    public InputEngine() {
        this.cwListener = new ConsoleWatch(this); //makes a linked cw obj
        this.scripts = new Vector<GameScript>();
        this.gkiListener = new GameKeyInput();
        this.gmiListener = new GameMouseInput();

        KeyBindings.createKeyBindings(); //make the key bindings
    }

    /*

    main update method that is called, will return a ref to the vector of game scripts to the calling class

     */
    public Vector<GameScript> run() {
        this.scripts.clear(); //clears the scripts
        this.cwListener.run(); //tells the console watch to listen
        this.gkiListener.refresh(this.scripts); //gives this.scripts as an output to the listener's refresh method
        this.gmiListener.refresh(this.scripts); //gives this.scripts as output to listener's refresh

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
                default:
                    return;
            }
        }
    }

    //TODO a method that only exists to pass the demo
    public void resetCW() {
        this.cwListener.deleteObserver(this);
        this.cwListener = new ConsoleWatch(this);
    }

    /*
    this method returns a reference to the gkiListener
     */
    public GameKeyInput getKeyHandler() {
        return gkiListener;
    }

    /*
    this method returns a reference to the gmiListener
     */
    public GameMouseInput getMouseHandler() {
        return gmiListener;
    }
}
