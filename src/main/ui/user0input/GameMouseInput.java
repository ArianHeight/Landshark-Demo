package main.ui.user0input;

import main.model.data.communication.GameScript;
import main.model.data.communication.MouseLocRequest;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Iterator;
import java.util.Vector;

/*
this class will handle every thing to do with in game mouse inputs
 */
public class GameMouseInput extends MouseAdapter {
    //vectors need to be synchronized in a tick() method
    //holds the locations where the mouse has been clicked since last frame
    Vector<Point> clickLocations;

    //cstr
    public GameMouseInput() {
        this.clickLocations = new Vector<Point>();
    }

    /*
    this is called whenever a click is detected on the window
     */
    @Override
    public void mouseClicked(MouseEvent e) {
        this.clickLocations.add(e.getPoint()); //adds location to queue
    }

    /*
    this method takes a Vector<GameScript> as an input and will dump translated mouse events into that vector
     */
    public void refresh(Vector<GameScript> output) {
        synchronized (this.clickLocations) {
            Iterator<Point> it = this.clickLocations.iterator();
            while (it.hasNext()) {
                output.add(new MouseLocRequest("click", it.next()));
            }
            this.clickLocations.clear();
        }
    }
}
