package main.data;

import main.data.structure.PhysicsComponent;
import main.data.structure.VisualComponent;

/*
this class extends the GameObject class

responsible for being a single button on screen
 */
public class GameButton extends GameObject {
    protected boolean pressed;

    //cstr
    public GameButton(PhysicsComponent pc, VisualComponent backDrop) {
        this.addComponent(pc);
        this.addComponent(backDrop);
    }

    //returns whether the button has been pressed or not
    public boolean isPressed() {
        return this.pressed;
    }

    //same as isPressed() but also resets state of pressed to false
    public boolean getPressedAndReset() {
        if (this.pressed) {
            this.pressed = false;
            return true;
        }

        return false;
    }

    //sets the pressed state of the button to true
    public void pressButton() {
        this.pressed = true;
    }
}
