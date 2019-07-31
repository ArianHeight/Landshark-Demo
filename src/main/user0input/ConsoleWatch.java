package main.user0input;

import main.data.communication.GameScript;

import java.util.Vector;
import java.util.Observable;

/*

Watches the console for any user input, notifies the InputEngine if there is

 */
public class ConsoleWatch extends Observable {
    //member vars
    private Vector<String> inputs; //an arraylist used to store user inputs
    private Vector<String> errors; //an arraylist used to store any errors from the console

    //cstr, linkedEngine is the engine which will call the run method in the ConsoleWatch obj
    public ConsoleWatch(InputEngine linkedEngine) {
        //creates the instances for the member vars and ensures the arraylists have at least a capacity of 1
        this.inputs = new Vector<String>();
        this.inputs.ensureCapacity(1);
        this.errors = new Vector<String>();
        this.errors.ensureCapacity(1);

        this.addObserver(linkedEngine); //adds the linked Engine to it's list of observers
    }

    //checks the Console for user input and if there is such an input, notify the input engine to grab the new data
    public void run() {
        if (ConsoleInput.getConsoleOutput(this.inputs, this.errors)) {
            if (this.errors.size() != 0) {
                this.setChanged();
                this.notifyObservers(GameScript.LOG_DATA);
            }
            if (this.inputs.size() != 0) {
                this.setChanged();
                this.notifyObservers(GameScript.PROCESS_DATA);
            }
        }
    }

    //accessors
    public Vector<String> getInputs() {
        return this.inputs;
    }

    public Vector<String> getErrors() {
        return this.errors;
    }

    //TODO a method that only exists to pass the demo
    private void cutLinkToObserver(InputEngine linkedEngine) {
        linkedEngine.resetCW();
    }
}
