package Data.Structure;

import Utility.HitboxAABB;

import java.awt.*;

/*
A game component specifically used for storing Textures
Extends the GameComponent class
 */
public class VisualTextureComponent extends GameComponent {
    //member vars
    private Image data;
    private Rectangle renderPlane; //the plane which to render the image on
    private HitboxAABB worldPos;
    private int layer; //determines the order in which things get rendered(smaller = rendered later)

    //cstr
    public VisualTextureComponent(Image newData, Rectangle newPlane) {
        super(gcType.VISUAL_TEXTURE);
        this.data = newData;
        this.renderPlane = newPlane;
        this.worldPos = null;
        this.layer = 1;
    }

    //alt cstr
    public VisualTextureComponent(Image newData, Rectangle newPlane, HitboxAABB hitbox, int layer) {
        super(gcType.VISUAL_TEXTURE);
        this.data = newData;
        this.renderPlane = newPlane;
        this.worldPos = hitbox;
        this.layer = layer;
    }

    /*
    REQUiRES:NONE
    MODIFIES:NONE
    EFFECT:Returns an Image obj that is carried by this component
     */
    @Override
    public Object getData() {
        return this.data;
    }

    //accessor, return a reference to the render plane
    public Rectangle getRenderPlane() { return this.renderPlane; }
    //accessor, return a reference to the Image obj
    public Image getTexture() { return this.data; }
    //sets the internal reference to a HitboxAABB obj
    public void setWorldPosRef(HitboxAABB ref) { this.worldPos = ref; }
    //gets the worldPos
    public HitboxAABB getWorldPosRef() { return this.worldPos; }
    //sets the layer value which determines render order
    public void setLayerVal(int val) { this.layer = val; }
    //gets the layer value
    public int getLayer() { return this.layer; }
}
