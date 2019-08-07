package model.game0logic;

import model.data.GameButton;
import model.data.structure.UiComponent;
import model.data.structure.VisualTextureComponent;

import java.awt.*;

/*
this class extends the GameButton class

used for checking if there will be a replay
 */
public class ReplayButton extends GameButton {
    private static Image standardTexture = null;

    //cstr
    public ReplayButton() {
        super(new UiComponent(new Rectangle(500, 300, 280, 120)),
                new VisualTextureComponent(standardTexture,
                        new Rectangle(500, 300, 280, 120), null, 0));
    }

    /*
    this method sets the default texutre
     */
    public static void setDefaultTexture(Image texture) {
        standardTexture = texture;
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
