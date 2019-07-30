package main.data.structure;

import main.utility.HitboxAabb;

import java.awt.*;

/*
A game component specifically used for storing Textures
Extends the VisualComponent class
 */
public class VisualTextureComponent extends VisualComponent {
    //member vars
    private Image data;

    //cstr
    public VisualTextureComponent(Image newData, Rectangle newPlane) {
        super(GcType.VISUAL_TEXTURE);
        this.data = newData;
        this.renderPlane = newPlane;
        this.worldPos = null;
        this.layer = 1;
    }

    //alt cstr
    public VisualTextureComponent(Image newData, Rectangle newPlane, HitboxAabb hitbox, int layer) {
        super(GcType.VISUAL_TEXTURE);
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

    //accessor, return a reference to the Image obj
    public Image getTexture() {
        return this.data;
    }
}
