package main.Render;

import main.Data.Communication.GameScript;
import main.Data.Communication.LogRequest;
import main.Data.GameObject;
import main.Data.Structure.GameComponent;
import main.Data.Structure.TextComponent;
import main.Data.Structure.VisualAnimationComponent;
import main.Data.Structure.VisualTextureComponent;
import main.Utility.HitboxAABB;
import main.Utility.Sorter;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyListener;
import java.awt.event.WindowEvent;
import java.awt.image.BufferStrategy;
import java.util.Iterator;
import java.util.Vector;

/*

This class will take care of all things to do with rendering

 */
public class RenderEngine {
    //default values
    private static final Dimension standardSize = new Dimension(1280, 720);
    private static final int standardCloseBehaviour = JFrame.DISPOSE_ON_CLOSE;
    private static final boolean standardResizableWindow = false;
    private static final String standardContextName = "LandShark AI Simulation";
    private static final Color black = new Color(0);

    //actual member vars
    private JFrame windowContext;
    private Vector<GameComponent> renderTargets;
    private Vector<GameComponent> animations;
    private boolean windowOpen;

    //default cstr
    public RenderEngine() {
        //setup
        this.windowContext = new JFrame(standardContextName);
        this.windowOpen = false;

        this.windowContext.setSize(standardSize);
        this.windowContext.setResizable(standardResizableWindow);
        this.windowContext.setDefaultCloseOperation(standardCloseBehaviour);

        this.renderTargets = new Vector<GameComponent>(); //for rendering
        this.animations = new Vector<GameComponent>(); //for rendering also
    }

    /*
    call this before doing anything
    this method will make the JFrame window visible and create the double buffer required to render

    returns a String of "" if nothing goes wrong, or an error msg if an exception is caught
     */
    public String openWindow() {
        if (this.windowOpen) { //window guard
            return "main.System tried to open a window when there is one already";
        }

        try {
            this.windowContext.setVisible(true); //opens the window
            this.windowContext.createBufferStrategy(2); //double buffering
            this.windowOpen = true;
        }
        catch (Exception error) {
            error.printStackTrace();
            return "Rendering Engine encountered an error while trying to open a window"; //something went wrong
        }

        return ""; //nothing went wrong
    }

    /*
    call this method to close the JFrame window

    DO NOT CALL IF THERE IS STILL SOMETHING THAT NEEDS TO BE RENDERED
     */
    public String closeWindow() {
        if (!this.windowOpen) { //window guard
            return "main.System tried to close a window that doesn't exist";
        }

        try {
            this.windowContext.dispatchEvent(new WindowEvent(this.windowContext, WindowEvent.WINDOW_CLOSING)); //sends a closing event to JFrame
            this.windowContext.dispose(); //destroy the window context
            this.windowOpen = false;
        }
        catch (Exception error) {
            error.printStackTrace();
            return "Rendering Engine encountered an error while trying to close a window"; //something went wrong
        }

        return "";
    }

    /*
    Dangerously renders a single VisualTextureComponent to the screen

    POTENTIALLY UNCAUGHT EXCEPTIONS!
    that's why this method is private
     */
    private void renderToWindow(VisualTextureComponent renderTarget, Graphics gContext) {
        Rectangle r = renderTarget.getRenderPlane(); //temp holder for rendering plane
        HitboxAABB hb = renderTarget.getWorldPosRef();

        if (hb != null) {
            Dimension topLeft = worldSpaceToScreenSpace(hb.getLeft(), hb.getTop(), null);
            Dimension bottomRight = worldSpaceToScreenSpace(hb.getRight(), hb.getBottom(), null);
            r.setRect(topLeft.width, topLeft.height, bottomRight.width - topLeft.width, bottomRight.height - topLeft.height);
        }

        if (renderTarget.getTexture() == null) { //no texture
            gContext.setColor(black);
            gContext.fillRect(r.x, r.y, r.width, r.height);
        }
        else { //texture exists
            gContext.drawImage(renderTarget.getTexture(), r.x, r.y, r.width, r.height, null); //draws image to screen
        }
    }

    /*
    Dangerously renders a single TextComponent to the screen

    POTENTIALLY UNCAUGHT EXCEPTIONS!
    that's why this method is private
     */
    private void renderTextToWindow(TextComponent renderTarget, Graphics gContext) {
        gContext.setColor(renderTarget.getColor());
        gContext.setFont(renderTarget.getFont());
        gContext.drawString((String)renderTarget.getData(), renderTarget.getX(), renderTarget.getY());
    }

    /*
    this method takes a GameObject and renders every attached GameObject to the window
    TODO maybe seperate this method into a few different methods
     */
    public void renderSceneToWindow(GameObject scene, Vector<GameScript> engineRequests, double timeElapsed) {
        if (!this.windowOpen) { //window guard
            //engineRequests.add(new LogRequest("Renderer attempted to draw frame to a closed window"));
            return;
        }

        try {
            //initializes some variables
            BufferStrategy buffer = this.windowContext.getBufferStrategy(); //get connected buffer
            Graphics gContext = buffer.getDrawGraphics();

            //grabs all the textures into a single vector
            this.renderTargets.clear(); //clear before compiling list
            scene.compileComponentList(this.renderTargets, GameComponent.gcType.VISUAL_TEXTURE);

            //grabs all the animations into a single vector, and adds the current sprites to the texture vector
            this.animations.clear();
            scene.compileComponentList(this.animations, GameComponent.gcType.VISUAL_ANIM);

            //grabs the current sprite and puts it into the renderTargets
            //also updates the sprite timings
            Iterator<GameComponent> gcIt = this.animations.iterator();
            while (gcIt.hasNext()) {
                this.renderTargets.add(((VisualAnimationComponent)gcIt.next()).getCurrentSprite(timeElapsed));
            }

            //sorts them by layer
            Sorter.quicksortForVTC(this.renderTargets, this.renderTargets.size() / 2);

            //iterates through textures and renders them one by one
            for (int i = this.renderTargets.size() - 1; i >= 0; i--) {
                this.renderToWindow((VisualTextureComponent)this.renderTargets.get(i), gContext);
            }

            //reuse renderTargets, but this time for text
            this.renderTargets.clear();
            scene.compileComponentList(this.renderTargets, GameComponent.gcType.TEXT);
            //iterates through all text and renders them to screen
            gcIt = this.renderTargets.iterator();
            while (gcIt.hasNext()) {
                this.renderTextToWindow((TextComponent)gcIt.next(), gContext);
            }


            gContext.dispose();
            buffer.show(); //flush the buffer to screen
        }
        catch (Exception error) {
            //error.printStackTrace();
            engineRequests.add(new LogRequest("Renderer encountered an error while attempting to draw frame"));
            this.closeWindow();
        }
    }

    //adds a KeyListener to window context
    public void addKeyListenerToWindow(KeyListener listener) {
        this.windowContext.addKeyListener(listener);
    }

    //takes a double x and y coord in world space and translates it to screen space
    public static Dimension worldSpaceToScreenSpace (double x, double y, GameComponent camera) {
        if (camera == null) {
            return new Dimension((int)(x * 1280.0 / 21.33333333), (int)(720 - (y * 720.0 / 12.0)));
        }

        return new Dimension(0 , 1); //TODO add stuff for custom camera
    }
}
