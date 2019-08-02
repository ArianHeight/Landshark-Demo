package main.data.structure;

import java.awt.*;

/*
this component will describe everything having to do with an on screen UI element
 */
public class UiComponent extends GameComponent {
    Rectangle hitbox;
    boolean pressState;

    public UiComponent(Rectangle hb) {
        super(GcType.UI);
        this.hitbox = hb;
        this.pressState = false;
    }

    /*
    returns a Rectangle representing the on screen hitbox
     */
    @Override
    public Object getData() {
        return this.hitbox;
    }

    //sets pressedState to true
    public void press() {
        this.pressState = true;
    }

    //sets pressedState to false
    public void resetState() {
        this.pressState = false;
    }
}
