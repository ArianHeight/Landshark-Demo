package model.data.structure;

import model.utility.HitboxAabb;

import java.awt.*;
import java.util.Iterator;
import java.util.Vector;

/*
A game component specifically used for storing a single Animation
will do so in the form of a Vector of VisualTextureComponents
Extends the GameComponent class
 */
public class VisualAnimationComponent extends VisualComponent {
    private Vector<Image> sprites;
    private double secondsBetweenFrame;
    private double secondsElapsed = 0.0; //time passed since last change
    private Iterator<Image> it;
    private Image currentSprite;

    //cstr
    //framePause is the amount of seconds between each frame of the animation
    public VisualAnimationComponent(double framePause, Rectangle plane, HitboxAabb hitbox) {
        super(GcType.VISUAL_ANIM);
        this.sprites = new Vector<Image>();
        this.secondsBetweenFrame = (framePause > 0 ? framePause : 1);
        this.it = this.sprites.iterator();
        this.renderPlane = plane;
        this.worldPos = hitbox;
        this.layer = 1;
    }

    //alt cstr
    //sprites is the vector of sprites in the animation
    //framePause is the amount of seconds between each frame of the animation
    //not suggested because the reference handling with the textures is not guarunteed(all hitboxes should point
    //to the same thing, and all rectangles should point to the same thing
    /*
    public VisualAnimationComponent(Vector<VisualTextureComponent> v_textures, double framePause) {
        super(GcType.VISUAL_ANIM);
        this.sprites = v_textures;
        this.secondsBetweenFrame = framePause;
        this.it = this.sprites.iterator();
    }*/

    /*
    this method takes an Image and adds it to the list of sprites that this animation holds
     */
    public void addSprite(Image data) {
        this.it = null; //sets iterator to null before changing the vector

        //adds the image
        this.sprites.add(data);

        this.it = this.sprites.iterator(); //remakes the iterator
        this.currentSprite = this.it.next();
    }

    /*
    MODIFIES:none
    EFFECT:returns an object reference to the Vector of VTCs which this class holds
     */
    @Override
    public Object getData() {
        return this.sprites;
    }

    /*
    A public method that will take care of all sprite updates
     */
    public void updateCurrentSprite(double timeElapsed) {
        this.secondsElapsed += timeElapsed;

        if (this.it == null) { //null guard
            this.it = this.sprites.iterator(); //start the animation
        }

        //iterates through the loop if the seconds elapsed is greater
        while (this.secondsElapsed >= this.secondsBetweenFrame) {
            if (!this.it.hasNext()) { //when the iterator reaches the end
                this.it = this.sprites.iterator(); //restart the animation
            }
            this.currentSprite = it.next(); //next sprite in the animation
            this.secondsElapsed -= this.secondsBetweenFrame;
        }
    }

    /*
    REQUIRES: a valid number of seconds that has passed since the last frame
    MODIFIES:this
    EFFECT:returns the current sprite the animation is on, and updates the current sprite if timeElapsed
           passes over the framePause time limit
           If u just want the texture, use the getTexture method instead
     */
    public Image getCurrentSprite(double timeElapsed) {
        this.updateCurrentSprite(timeElapsed);
        return this.currentSprite;
    }

    /*
    returns the current sprite the animation is on without updating the animation
     */
    @Override
    public Image getTexture() {
        return this.currentSprite;
    }

    /*
    makes a copy of this animation using new worldPosRef, a new drawing rectangle and a new layer value
     */
    public VisualAnimationComponent makeCpy(Rectangle plane, HitboxAabb hitbox, int layerVal) {
        //makes copy
        VisualAnimationComponent returnVal = new VisualAnimationComponent(this.secondsBetweenFrame, plane, hitbox);
        returnVal.setLayerVal(layerVal);

        //populate it with the same textures
        Iterator<Image> it = this.sprites.iterator();
        while (it.hasNext()) {
            returnVal.addSprite(it.next());
        }

        return returnVal;
    }
}
