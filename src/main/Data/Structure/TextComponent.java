package main.Data.Structure;

import java.awt.*;

/*
this class will extend the GameComponent class
represents a line of text to be rendered on screen
 */
public class TextComponent extends GameComponent {
    private String text;
    private Font font;
    int xScreenPos;
    int yScreenPos;
    Color textColor;

    //cstr
    public TextComponent(String text, Font font, int x, int y, Color color) {
        super(gcType.TEXT);
        this.text = text;
        this.font = font;
        this.xScreenPos = x;
        this.yScreenPos = y;
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
    public Font getFont() { return this.font; }

    //returns the screen position on the x axis
    public int getX() { return this.xScreenPos; }

    //returns the screen position on the y axis
    public int getY() { return this.yScreenPos; }

    //returns the color of the text
    public Color getColor() { return this.textColor; }
}
