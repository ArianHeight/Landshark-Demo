package Data.Structure;

import Utility.HitboxAABB;

import java.awt.*;

/*
A game component specifically used for storing Textures
Extends the GameComponent class
 */
public class VisualTextureComponent extends GameComponent {
    //member vars
    private Image im_data;
    private Rectangle r_renderPlane; //the plane which to render the image on
    private HitboxAABB hb_worldPos;
    private int i_layer; //determines the order in which things get rendered(smaller = rendered later)

    //cstr
    public VisualTextureComponent(Image im_newData, Rectangle r_newPlane) {
        super(gcType.VISUAL_TEXTURE);
        this.im_data = im_newData;
        this.r_renderPlane = r_newPlane;
        this.hb_worldPos = null;
        this.i_layer = 1;
    }

    //alt cstr
    public VisualTextureComponent(Image im_newData, Rectangle r_newPlane, HitboxAABB hb_hitbox, int i_layer) {
        super(gcType.VISUAL_TEXTURE);
        this.im_data = im_newData;
        this.r_renderPlane = r_newPlane;
        this.hb_worldPos = hb_hitbox;
        this.i_layer = i_layer;
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
    //sets the internal reference to a HitboxAABB obj
    public void setWorldPosRef(HitboxAABB hb_ref) { this.hb_worldPos = hb_ref; }
    //gets the worldPos
    public HitboxAABB getWorldPosRef() { return this.hb_worldPos; }
    //sets the layer value which determines render order
    public void setLayerVal(int i_val) { this.i_layer = i_val; }
    //gets the layer value
    public int getLayer() { return this.i_layer; }
}
