package main.data.structure;

import java.awt.*;

/*
this class will extend the GameComponent class
represents a line of text to be rendered on screen
 */
public class TextComponent extends GameComponent {
    private String text;
    private Font font;
    int screenPosX;
    int screenPosY;
    Color textColor;

    //cstr
    public TextComponent(String text, Font font, int x, int y, Color color) {
        super(GcType.TEXT);
        this.text = text;
        this.font = font;
        this.screenPosX = x;
        this.screenPosY = y;
        this.textColor = color;
    }

    /*
    returns a single String representing the stored text
     */
    @Override
    public Object getData() {
        return this.text;
    }

    //sets the String text that this component carries
    public void setText(String newText) {
        this.text = newText;
    }

    //returns the font of the text
    public Font getFont() {
        return this.font;
    }

    //returns the screen position on the x axis
    public int getX() {
        return this.screenPosX;
    }

    //returns the screen position on the y axis
    public int getY() {
        return this.screenPosY;
    }

    //returns the color of the text
    public Color getColor() {
        return this.textColor;
    }
}
