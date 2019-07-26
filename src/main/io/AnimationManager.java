package main.io;

import main.data.game0exceptions.FileNotOpenException;
import main.data.game0exceptions.ImageDidNotLoadException;
import main.data.game0exceptions.NoDataException;
import main.data.structure.VisualAnimationComponent;
import main.utility.HitboxAABB;

import java.awt.*;
import java.util.HashMap;
import java.util.Vector;

/*
this class is in charge of loading all sprite animations
 */
public class AnimationManager {
    private ImageManager internalTextures;
    private HashMap<String, VisualAnimationComponent> loadedAnimations;
    private GameFileReader animationReader;
    private Vector<String> logs;
    private Vector<String> errors;

    //uses an external ImageManager if given one, or creates an internal manager if given none
    public AnimationManager(ImageManager manager) {
        if (manager == null) {
            this.internalTextures = new ImageManager();
        } else {
            this.internalTextures = manager;
        }

        this.loadedAnimations = new HashMap<String, VisualAnimationComponent>();
        this.logs = new Vector<String>();
        this.errors = new Vector<String>();
    }

    /*
    MODIFIES:this
    EFFECT:If the animation attached to the filePath wasn't loaded, load the animation.
           Finds the animation and return a copy of it(parameters of plane, hitbox, and layer value
           are passed as params)

     */
    public VisualAnimationComponent makeAnimation(String filePath, Rectangle plane, HitboxAABB hitbox, int layerVal) {
        VisualAnimationComponent animation = this.loadedAnimations.get(filePath);

        //check if loaded already
        if (animation != null) {
            return animation.makeCpy(plane, hitbox, layerVal);
        }

        this.animationReader = new GameFileReader(filePath); //openfile
        String msg = this.animationReader.openFile();
        if (!msg.equals("")) { //error pushing
            this.errors.add(msg);
        }

        try {
            double framePause = this.animationReader.getNextDouble();
            animation = new VisualAnimationComponent(framePause, new Rectangle(),
                    new HitboxAABB(0.0, 1.0, 1.0, 0.0));
            while (true) {
                animation.addSprite(this.internalTextures.loadImage(this.animationReader.readLineFromFile()));
            }
        } catch (FileNotOpenException error) {
            this.errors.add("Could not read animation file at: " + filePath);
            animation = new VisualAnimationComponent(1, new Rectangle(),
                    new HitboxAABB(0.0, 1.0, 1.0, 0.0));
            animation.addSprite(null);
        } catch (NoDataException errorTwo) {
            animation.setLayerVal(0);
        } catch (ImageDidNotLoadException errorThree) {
            this.errors.add("Could not read image file at: " + filePath);
            animation = new VisualAnimationComponent(1, new Rectangle(),
                    new HitboxAABB(0.0, 1.0, 1.0, 0.0));
            animation.addSprite(null);
        } finally {
            msg = this.animationReader.closeFile(); //close file
            if (!msg.equals("")) { //error pushing
                this.errors.add(msg);
            }
        }

        return animation.makeCpy(plane, hitbox, layerVal);
    }
}
