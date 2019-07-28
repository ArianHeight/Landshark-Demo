package main.user0input;

import main.data.communication.GameScript;
import main.game0logic.KeyBindings;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.Iterator;
import java.util.Vector;

/*
this class will handle everything to do with in game keyboard inputs
 */
public class GameKeyInput extends KeyAdapter {
    //vectors need to be synchronized in a tick() method
    //if the key has been clicked since last refresh
    private Vector<Integer> keysClicked;
    //holds the keys that were clicked in the last/this refresh, but not released
    private Vector<Integer> keysNotReleased;
    //holds the keys that were released in this refresh
    private Vector<Integer> keysReleased;

    //cstr
    public GameKeyInput() {
        //initializes member vars
        this.keysClicked = new Vector<Integer>();
        this.keysNotReleased = new Vector<Integer>();
        this.keysReleased = new Vector<Integer>();

        //ensures capacity for some of these vectors
        this.keysReleased.ensureCapacity(10); //for 10 fingers
        this.keysNotReleased.ensureCapacity(10);
        this.keysClicked.ensureCapacity(10);
    }

    /*
    handles key presses
     */
    @Override
    public void keyPressed(KeyEvent e) {
        super.keyPressed(e);
        this.keysClicked.add(e.getKeyCode());
    }

    /*
    handles key releases
     */
    @Override
    public void keyReleased(KeyEvent e) {
        super.keyReleased(e);
        this.keysReleased.add(e.getKeyCode());
    }

    /*
    this method takes a Vector<GameScript> as an input and will dump translated key events into that vector
     */
    public void refresh(Vector<GameScript> output) {
        //synchronise both vectors to be thread safe
        synchronized (this.keysClicked) {
            synchronized (this.keysReleased) {
                KeyEvent tempKeyEvent;

                //iterates through the unreleased keys and makes sure they aren't on the clicked keys list
                this.keysClicked.removeAll(this.keysNotReleased);

                //iterates through the clicked keys and outputs appropriate GameScripts
                this.getClickBindings(output);

                //moves the clicked keys over to keysNotReleased
                this.keysNotReleased.addAll(this.keysClicked);
                this.keysClicked.clear(); //clears for next refresh

                //iterates through keys not released and outputs appropriate game scripts
                this.getHoldBindings(output);

                //remove all keys that have been released from keysNotReleased
                this.keysNotReleased.removeAll(this.keysReleased);
                this.keysReleased.clear(); //clear for next refresh
            }
        }
    }

    //private method used to grab all click bindings and send them to output
    private void getClickBindings(Vector<GameScript> output) {
        GameScript tempScript = null;
        Iterator<Integer> itInt = this.keysClicked.iterator();

        while (itInt.hasNext()) {
            tempScript = KeyBindings.getClickBindingFor(itInt.next());

            if (tempScript != null) { //if there is a script present
                output.add(tempScript); //add the GameScript to the output
            }
        }
    }

    //private method used to grab all held bindings and send them to output
    private void getHoldBindings(Vector<GameScript> output) {
        GameScript tempScript = null;
        Iterator<Integer> itInt = this.keysClicked.iterator();

        itInt = this.keysNotReleased.iterator();
        while (itInt.hasNext()) {
            tempScript = KeyBindings.getHoldBindingsFor(itInt.next());

            if (tempScript != null) {
                output.add(tempScript);
            }
        }
    }
}
