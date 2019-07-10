package Data.Structure;

import java.awt.*;

/*
A game component specifically used for storing Textures
Extends the GameComponent class
 */
public class VisualTextureComponent extends GameComponent {
    //member vars
    private Image im_data;
    private Rectangle r_renderPlane; //the plane which to render the image on
    private boolean b_visible; //whether or not the texture is visible at the moment

    //cstr
    public VisualTextureComponent(Image im_newData, Rectangle r_newPlane) {
        super(gcType.VISUAL_TEXTURE);
        this.im_data = im_newData;
        this.r_renderPlane = r_newPlane;
        this.b_visible = true;
    }

    /*
    REQUiRES:NONE
    MODIFIES:NONE
    EFFECT:Returns an Image obj that is carried by this component
     */
    @Override
    public Object getData() {
        return this.im_data;
    }

    //accessor, returns a boolean value of visibility
    public boolean getVisible() { return this.b_visible; }

    //accessor, return a reference to the render plane
    public Rectangle getRenderPlane() { return this.r_renderPlane; }
    //accessor, return a reference to the Image obj
    public Image getTexture() { return this.im_data; }

    //mutator, sets visibility to true
    public void setVisible() { this.b_visible = true; }
    //mutator, sets visibility to false
    public void setInvisible() { this.b_visible = false; }
}
