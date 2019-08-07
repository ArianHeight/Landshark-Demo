package model.data;

import model.data.structure.GameComponent;
import model.data.structure.UiComponent;
import model.data.structure.VisualComponent;

/*
this class extends the GameObject class

responsible for being a single button on screen
 */
public class GameButton extends GameObject {
    //cstr
    public GameButton(UiComponent uic, VisualComponent backDrop) {
        this.addComponent(uic);
        this.addComponent(backDrop);
    }

    //returns whether the button has been pressed or not
    public boolean isPressed() {
        return ((UiComponent)this.findFirstActiveComponentInObj(GameComponent.GcType.UI)).getPressedState();
    }

    //same as isPressed() but also resets state of pressed to false
    public boolean getPressedAndReset() {
        if (this.isPressed()) {
            ((UiComponent)this.findFirstActiveComponentInObj(GameComponent.GcType.UI)).resetState();
            return true;
        }

        return false;
    }
}
