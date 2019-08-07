package main.model.game0logic;

import main.model.data.GameObject;
import main.model.data.structure.TextComponent;

import java.awt.*;

/*
this class is a single line of text on screen
 */
public class LandSharkText extends GameObject {

    //cstr
    public LandSharkText() {
        this.addComponent(new TextComponent("", new Font("Impact", 0, 30), 20, 70, Color.black));
    }

    //sets the text field of this obj
    public void setText(String newText) {
        ((TextComponent)this.memberComponents.get(0)).setText(newText);
    }
}
