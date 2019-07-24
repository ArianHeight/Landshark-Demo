package main.game0logic;

import main.data.GameObject;
import main.data.structure.TextComponent;

import java.awt.*;

/*
this class is a single line of text on screen
 */
public class LandSharkText extends GameObject {

    //cstr
    LandSharkText() {
        this.addComponent(new TextComponent("", new Font("Impact", 0, 30), 20, 70, Color.black));
    }

    //sets the text field of this obj
    public void setText(String newText) {
        ((TextComponent)this.memberComponents.get(0)).setText(newText);
    }
}
