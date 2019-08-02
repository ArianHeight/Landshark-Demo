package main.game0logic;

import main.data.GameButton;
import main.data.structure.UiComponent;
import main.data.structure.VisualTextureComponent;

import java.awt.*;

/*
this class extends the GameButton class

used for checking if there will be a replay
 */
public class ReplayButton extends GameButton {
    //cstr
    ReplayButton() {
        super(new UiComponent(new Rectangle(500, 300, 280, 120)),
                new VisualTextureComponent(null,
                        new Rectangle(500, 300, 280, 120), null, 0));

    }

    /*
    updating by resetting the press state of the UiComponent
     */
    @Override
    public void updateObj() {
        this.getPressedAndReset(); //resets the press state

        super.updateObj();
    }
}
