package model.data.structure;

import model.utility.HitboxAabb;

import java.awt.*;

/*
extends the GameComponent class
will represent anything that can be visually drawn(except text, maybe I'll add it later)
 */
public abstract class VisualComponent extends GameComponent {
    protected Rectangle renderPlane; //the plane which to render the image on
    protected HitboxAabb worldPos;
    protected int layer; //determines the order in which things get rendered(smaller = rendered later)

    //cstr
    VisualComponent(GcType type) {
        super(type);
    }

    /*
    REQUIRES: For the obj to have a texture
    MODIFIES: this
    EFFECT: returns the image that is to be rendered
     */
    public abstract Image getTexture();

    //accessor, return a reference to the render plane
    public Rectangle getRenderPlane() {
        return this.renderPlane;
    }

    //sets the internal reference to a HitboxAabb obj
    public void setWorldPosRef(HitboxAabb ref) {
        this.worldPos = ref;
    }

    //gets the worldPos
    public HitboxAabb getWorldPosRef() {
        return this.worldPos;
    }

    //sets the layer value which determines render order
    public void setLayerVal(int val) {
        this.layer = val;
    }

    //gets the layer value
    public int getLayer() {
        return this.layer;
    }
}
