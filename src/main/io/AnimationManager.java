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

        boolean passedDouble = false;
        try {
            double framePause = this.animationReader.getNextDouble();
            passedDouble = true;
            animation = new VisualAnimationComponent(framePause, new Rectangle(),
                    new HitboxAABB(0.0, 1.0, 1.0, 0.0));
            String temp;
            while (true) {
                temp = this.animationReader.readLineFromFile();
                if (!temp.equals("")) {
                    animation.addSprite(this.internalTextures.loadImage(temp));
                }
            }
        } catch (FileNotOpenException error) {
            this.errors.add("Could not read animation file at: " + filePath);
            animation = new VisualAnimationComponent(1, new Rectangle(),
                    new HitboxAABB(0.0, 1.0, 1.0, 0.0));
            animation.addSprite(null);
        } catch (NoDataException errorTwo) {
            if (passedDouble) {
                animation.setLayerVal(0);
            } else {
                this.errors.add("File at: " + filePath + " is possibly broken and could not be read...");
                animation = new VisualAnimationComponent(1, new Rectangle(),
                        new HitboxAABB(0.0, 1.0, 1.0, 0.0));
                animation.addSprite(null);
            }
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

    //accessor to logs
    public Vector<String> getLogs() {
        return this.logs;
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
