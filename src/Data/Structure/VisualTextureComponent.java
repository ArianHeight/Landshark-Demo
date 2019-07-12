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

    //cstr
    public VisualTextureComponent(Image im_newData, Rectangle r_newPlane) {
        super(gcType.VISUAL_TEXTURE);
        this.im_data = im_newData;
        this.r_renderPlane = r_newPlane;
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

    //accessor, return a reference to the render plane
    public Rectangle getRenderPlane() { return this.r_renderPlane; }
    //accessor, return a reference to the Image obj
    public Image getTexture() { return this.im_data; }
}
