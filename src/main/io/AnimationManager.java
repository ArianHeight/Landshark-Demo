package main.io;

import main.data.structure.VisualAnimationComponent;
import main.utility.HitboxAABB;

import java.awt.*;
import java.util.HashMap;

/*
this class is in charge of loading all sprite animations
 */
public class AnimationManager {
    ImageManager internalTextures;
    HashMap<String, VisualAnimationComponent> loadedAnimations;
    GameFileReader animationReader;

    //uses an external ImageManager if given one, or creates an internal manager if given none
    public AnimationManager(ImageManager manager) {
        if (manager == null) {
            this.internalTextures = new ImageManager();
        } else {
            this.internalTextures = manager;
        }
    }

    /*
    MODIFIES:this
    EFFECT:If the animation attached to the filePath wasn't loaded, load the animation.
           Finds the animation and return a copy of it(parameters of plane, hitbox, and layer value are passed as params)

     */
    public VisualAnimationComponent makeAnimation(String filePath, Rectangle plane, HitboxAABB hitbox, int layerVal) {
        VisualAnimationComponent animation = this.loadedAnimations.get(filePath);

        //check if loaded already
        if (animation != null) {
            return animation.makeCpy(plane, hitbox, layerVal);
        }

        this.animationReader = new GameFileReader(filePath);

        return null; //TODO finish this method
    }
}
