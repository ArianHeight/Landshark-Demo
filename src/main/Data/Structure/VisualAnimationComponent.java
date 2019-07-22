package main.Data.Structure;

import main.Utility.HitboxAABB;

import java.awt.*;
import java.util.Iterator;
import java.util.Vector;

/*
A game component specifically used for storing a single Animation
will do so in the form of a Vector of VisualTextureComponents
Extends the GameComponent class
 */
public class VisualAnimationComponent extends GameComponent{
    private Vector<VisualTextureComponent> v_vtc_sprites;
    private double d_secondsBetweenFrame;
    private double d_secondsElapsed = 0.0; //time passed since last change
    private Iterator<VisualTextureComponent> vtc_it;
    private VisualTextureComponent vtc_currentSprite;
    private Rectangle r_renderPlane;
    private HitboxAABB hb_worldPosRef;

    //cstr
    //d_framePause is the amount of seconds between each frame of the animation
    public VisualAnimationComponent(double d_framePause, Rectangle r_plane, HitboxAABB hb_hitbox) {
        super(gcType.VISUAL_ANIM);
        this.v_vtc_sprites = new Vector<VisualTextureComponent>();
        this.d_secondsBetweenFrame = d_framePause;
        this.vtc_it = this.v_vtc_sprites.iterator();
        this.r_renderPlane = r_plane;
        this.hb_worldPosRef = hb_hitbox;
    }

    //alt cstr
    //v_vtc_textures is the vector of sprites in the animation
    //d_framePause is the amount of seconds between each frame of the animation
    //not suggested because the reference handling with the textures is not guarunteed(all hitboxes should point
    //to the same thing, and all rectangles should point to the same thing
    /*
    public VisualAnimationComponent(Vector<VisualTextureComponent> v_vtc_textures, double d_framePause) {
        super(gcType.VISUAL_ANIM);
        this.v_vtc_sprites = v_vtc_textures;
        this.d_secondsBetweenFrame = d_framePause;
        this.vtc_it = this.v_vtc_sprites.iterator();
    }*/

    /*
    this method takes an Image and adds it to the list of sprites that this animation holds
     */
    public void addSprite(Image im_data) {
        this.vtc_it = null; //sets iterator to null before changing the vector

        //creates obj to be added
        VisualTextureComponent vtc_temp = new VisualTextureComponent(im_data, this.r_renderPlane);
        vtc_temp.setWorldPosRef(this.hb_worldPosRef);
        this.v_vtc_sprites.add(vtc_temp);

        this.vtc_it = this.v_vtc_sprites.iterator(); //remakes the iterator
    }

    /*
    MODIFIES:none
    EFFECT:returns an object reference to the Vector of VTCs which this class holds
     */
    @Override
    public Object getData() {
        return this.v_vtc_sprites;
    }

    /*
    REQUIRES: this.d_secondsElapsed has to be updated by other means, usually the getCurrentSprite(double) method
    A private method that will take care of all sprite updates
     */
    private void updateCurrentSprite() {
        if (this.vtc_it == null) { //null guard
            this.vtc_it = this.v_vtc_sprites.iterator(); //start the animation
        }

        while (this.d_secondsElapsed >= this.d_secondsBetweenFrame) { //iterates through the loop if the seconds elapsed is greater
            if (!this.vtc_it.hasNext()) { //when the iterator reaches the end
                this.vtc_it = this.v_vtc_sprites.iterator(); //restart the animation
            }
            this.vtc_currentSprite = vtc_it.next(); //next sprite in the animation
            this.d_secondsElapsed -= this.d_secondsBetweenFrame;
        }
    }

    /*
    REQUIRES: a valid number of seconds that has passed since the last frame
    MODIFIES:this
    EFFECT:returns the current sprite the animation is on, and updates the current sprite if d_timeElapsed
           passes over the d_framePause time limit
     */
    public VisualTextureComponent getCurrentSprite(double d_timeElapsed) {
        this.updateCurrentSprite();
        this.d_secondsElapsed += d_timeElapsed;
        return this.vtc_currentSprite;
    }
}
