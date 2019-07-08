package UserInput;

import Data.Communication.GameScript;

import java.util.Vector;
import java.util.Observable;

/*

Watches the console for any user input, notifies the InputEngine if there is

 */
public class ConsoleWatch extends Observable {
    //member vars
    private Vector<String> v_str_inputs; //an arraylist used to store user inputs
    private Vector<String> v_str_errors; //an arraylist used to store any errors from the console

    //cstr, ie_linkedEngine is the engine which will call the run method in the ConsoleWatch obj
    public ConsoleWatch(InputEngine ie_linkedEngine) {
        //creates the instances for the member vars and ensures the arraylists have at least a capacity of 1
        this.v_str_inputs = new Vector<String>();
        this.v_str_inputs.ensureCapacity(1);
        this.v_str_errors = new Vector<String>();
        this.v_str_errors.ensureCapacity(1);

        this.addObserver(ie_linkedEngine); //adds the linked Engine to it's list of observers
    }

    //checks the Console for user input and if there is such an input, notify the input engine to grab the new data
    public void run() {
        if (Console.getConsoleOutput(this.v_str_inputs, this.v_str_errors)) {
            if (this.v_str_errors.size() != 0) {
                this.notifyObservers(GameScript.LOG_DATA);
            }
            if (this.v_str_inputs.size() != 0) {
                this.notifyObservers(GameScript.PROCESS_DATA);
            }
        }
    }

    //accessors
    public Vector<String> getInputs() {
        return this.v_str_inputs;
    }

    public Vector<String> getErrors() {
        return this.v_str_errors;
    }
}
