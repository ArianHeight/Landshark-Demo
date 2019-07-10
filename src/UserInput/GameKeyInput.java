package UserInput;

import Data.Communication.GameScript;
import GameLogic.KeyBindings;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Vector;

/*
this class will handle everything to do with in game keyboard inputs
 */
public class GameKeyInput extends KeyAdapter {
    //vectors need to be synchronized in a tick() method
    private Vector<Integer> v_k_keysClicked; //if the key has been clicked since last refresh
    private Vector<Integer> v_k_keysNotReleased; //holds the keys that were clicked in the last/this refresh, but not released
    private Vector<Integer> v_k_keysReleased; //holds the keys that were released in this refresh

    //cstr
    public GameKeyInput() {
        //initializes member vars
        this.v_k_keysClicked = new Vector<Integer>();
        this.v_k_keysNotReleased = new Vector<Integer>();
        this.v_k_keysReleased = new Vector<Integer>();

        //ensures capacity for some of these vectors
        this.v_k_keysReleased.ensureCapacity(10); //for 10 fingers
        this.v_k_keysNotReleased.ensureCapacity(10);
        this.v_k_keysClicked.ensureCapacity(10);
    }

    /*
    handles key presses
     */
    @Override
    public void keyPressed(KeyEvent e) {
        super.keyPressed(e);
        this.v_k_keysClicked.add(e.getKeyCode());
    }

    /*
    handles key releases
     */
    @Override
    public void keyReleased(KeyEvent e) {
        super.keyReleased(e);
        this.v_k_keysReleased.add(e.getKeyCode());
    }

    /*
    this method takes a Vector<GameScript> as an input and will dump translated key events into that vector
     */
    public synchronized void refresh(Vector<GameScript> v_gs_output) {
        //synchronise both vectors to be thread safe
        synchronized (this.v_k_keysClicked) {
            synchronized (this.v_k_keysReleased) {
                KeyEvent k_temp;

                //iterates through the unreleased keys and makes sure they aren't on the clicked keys list
                this.v_k_keysClicked.removeAll(this.v_k_keysNotReleased);

                //iterates through the clicked keys and outputs appropriate GameScripts
                GameScript gs_temp = null;
                Iterator<Integer> i_it = this.v_k_keysClicked.iterator();
                while (i_it.hasNext()) {
                    gs_temp = KeyBindings.getClickBindingFor(i_it.next());

                    if (gs_temp != null) { //if there is a script present
                        v_gs_output.add(gs_temp); //add the GameScript to the output
                    }
                }

                //moves the clicked keys over to keysNotReleased
                this.v_k_keysNotReleased.addAll(this.v_k_keysClicked);
                this.v_k_keysClicked.clear(); //clears for next refresh

                //remove all keys that have been released from keysNotReleased
                this.v_k_keysNotReleased.removeAll(this.v_k_keysReleased);
                this.v_k_keysReleased.clear(); //clear for next refresh
            }
        }
    }
}
