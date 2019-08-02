package main.data.communication;

import java.awt.*;

/*
A game script aimed at the physics engine to tell it to update and check collision with buttons/on screen ui elements
 */
public class MouseLocRequest extends GameScript {
    Point location;

    //cstr
    //info determines what type of event etc click or hold
    public MouseLocRequest(String info, Point loc) {
        super(MOUSE_LOCATION, info);
        this.location = loc;
    }

    //returns the held location
    public Point getLocation() {
        return this.location;
    }
}
