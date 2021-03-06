package model.io;

import model.data.game0exceptions.FileNotOpenException;
import model.data.game0exceptions.ImageDidNotLoadException;
import model.data.game0exceptions.NoDataException;
import model.data.structure.VisualAnimationComponent;
import model.utility.HitboxAabb;

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
    public VisualAnimationComponent makeAnimation(String filePath, Rectangle plane, HitboxAabb hitbox, int layerVal) {
        VisualAnimationComponent animation = this.loadedAnimations.get(filePath);

        //check if loaded already
        if (animation != null) {
            return animation.makeCpy(plane, hitbox, layerVal);
        }

        this.animationReader = new GameFileReader(filePath); //openfile

        this.pushError(this.animationReader.openFile());
        animation = this.loadAnimation(filePath);
        this.pushError(this.animationReader.closeFile()); //close file

        return animation.makeCpy(plane, hitbox, layerVal);
    }

    //Actually loads a new animation and returns it
    //takes the animation filePath as an input
    private VisualAnimationComponent loadAnimation(String filePath) {
        VisualAnimationComponent animation = null;
        boolean passedDouble = false;
        try {
            double framePause = this.animationReader.getNextDouble();
            passedDouble = true;
            animation = new VisualAnimationComponent(framePause, new Rectangle(),
                    new HitboxAabb(0.0, 1.0, 1.0, 0.0));
            this.unsafeLoadAnimation(animation);
        } catch (FileNotOpenException error) {
            animation = this.pushErrorAndGetDefault("Could not read animation file at: " + filePath);
        } catch (NoDataException errorTwo) {
            animation = this.testSucessfulLoad(animation, passedDouble, filePath);
        } catch (ImageDidNotLoadException errorThree) {
            animation = this.pushErrorAndGetDefault("Could not read image file at: " + filePath);
        }

        return animation;
    }

    //pushes an error if animation has been loaded past the 1st line sucessfully
    //returns a finished animation with layer value of 0 otherwise
    //passedDouble is whether or no the animation loaded past the 1st line
    //filePath is the String filePath that we use for error pushing
    private VisualAnimationComponent testSucessfulLoad(VisualAnimationComponent animation,
                                                       boolean passedDouble, String filePath) {
        if (passedDouble) {
            animation.setLayerVal(0);
            return animation;
        }

        return this.pushErrorAndGetDefault("File at: " + filePath
                + " is possibly broken and could not be read...");
    }

    //calls pushError() and getDefaultAnimation()
    private VisualAnimationComponent pushErrorAndGetDefault(String msg) {
        this.pushError(msg);
        return this.getDefaultAnimation();
    }

    //takes a String msg and pushes it as a log request if it isn't empty
    private void pushError(String msg) {
        if (!msg.equals("")) { //error pushing
            this.errors.add(msg);
        }
    }

    //creates and returns a default animation with a single null sprite
    private VisualAnimationComponent getDefaultAnimation() {
        VisualAnimationComponent animation = new VisualAnimationComponent(1, new Rectangle(),
                new HitboxAabb(0.0, 1.0, 1.0, 0.0));
        animation.addSprite(null);

        return animation;
    }

    //an unsafe private method that loads the animations linked from the .anim file
    //throws all exception to calling method
    private void unsafeLoadAnimation(VisualAnimationComponent animation) throws
            NoDataException, FileNotOpenException, ImageDidNotLoadException {
        String temp;
        while (true) {
            temp = this.animationReader.readLineFromFile();
            if (!temp.equals("")) {
                animation.addSprite(this.internalTextures.loadImage(temp));
            }
        }
    }

    //accessor to errors
    public Vector<String> getErrors() {
        return this.errors;
    }

    //clears all logs and errors
    public void clearLogsAndErrors() {
        this.logs.clear();
        this.errors.clear();
    }
}
