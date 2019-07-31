package main.render;

import main.data.communication.EndProgramRequest;
import main.data.communication.GameScript;
import main.data.communication.LogRequest;
import main.data.GameObject;
import main.data.structure.*;
import main.data.structure.TextComponent;
import main.utility.HitboxAabb;
import main.utility.Sorter;

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
            return "main.system tried to open a window when there is one already";
        }

        try {
            this.windowContext.setVisible(true); //opens the window
            this.windowContext.createBufferStrategy(2); //double buffering
            this.windowOpen = true;
        } catch (Exception error) {
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
            return "main.system tried to close a window that doesn't exist";
        }

        try {
            //sends a closing event to JFrame
            this.windowContext.dispatchEvent(new WindowEvent(this.windowContext, WindowEvent.WINDOW_CLOSING));
            this.windowContext.dispose(); //destroy the window context
            this.windowOpen = false;
        } catch (Exception error) {
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
    private void renderToWindow(VisualComponent renderTarget, Graphics grContext) {
        Rectangle r = renderTarget.getRenderPlane(); //temp holder for rendering plane
        HitboxAabb hb = renderTarget.getWorldPosRef();

        if (hb != null) {
            Dimension topLeft = worldSpaceToScreenSpace(hb.getLeft(), hb.getTop(), null);
            Dimension bottomRight = worldSpaceToScreenSpace(hb.getRight(), hb.getBottom(), null);
            r.setRect(topLeft.width, topLeft.height,
                      bottomRight.width - topLeft.width, bottomRight.height - topLeft.height);
        }

        if (renderTarget.getTexture() == null) { //no texture
            grContext.setColor(black);
            grContext.fillRect(r.x, r.y, r.width, r.height);
        } else { //texture exists
            grContext.drawImage(renderTarget.getTexture(), r.x, r.y, r.width, r.height, null); //draws image to screen
        }
    }

    /*
    Dangerously renders a single TextComponent to the screen

    POTENTIALLY UNCAUGHT EXCEPTIONS!
    that's why this method is private
     */
    private void renderTextToWindow(TextComponent renderTarget, Graphics grContext) {
        grContext.setColor(renderTarget.getColor());
        grContext.setFont(renderTarget.getFont());
        grContext.drawString((String)renderTarget.getData(), renderTarget.getX(), renderTarget.getY());
    }

    /*
    this method takes a GameObject and renders every attached GameObject to the window
    TODO maybe seperate this method into a few different methods
     */
    public void renderSceneToWindow(GameObject scene, Vector<GameScript> engineRequests, double timeElapsed) {
        if (!this.windowOpen) { //window guard
            //engineRequests.add(new LogRequest("Renderer attempted to draw frame to a closed window"));
            engineRequests.add(new EndProgramRequest());
            return;
        }

        try {
            //initializes some variables
            BufferStrategy buffer = this.windowContext.getBufferStrategy(); //get connected buffer
            Graphics grContext = buffer.getDrawGraphics();

            //grabs all the textures into a single vector
            this.renderTargets.clear(); //clear before compiling list
            scene.compileComponentList(this.renderTargets, GameComponent.GcType.VISUAL_TEXTURE);

            this.updateAnimationSprites(scene, timeElapsed);
            this.renderAllVisualComponents(grContext);
            this.renderAllText(scene, grContext);

            grContext.dispose();
            buffer.show(); //flush the buffer to screen
        } catch (Exception error) {
            //error.printStackTrace();
            engineRequests.add(new LogRequest("Renderer encountered an error while attempting to draw frame"));
            this.closeWindow();
        }
    }

    //updates the sprites for every animation
    //call this before compiling the final render list
    private void updateAnimationSprites(GameObject scene, double timeElapsed) {
        //grabs all the animations into a single vector, and adds the current sprites to the texture vector
        this.animations.clear();
        scene.compileComponentList(this.animations, GameComponent.GcType.VISUAL_ANIM);

        //grabs the current sprite and puts it into the renderTargets
        //also updates the sprite timings
        Iterator<GameComponent> gcIt = this.animations.iterator();
        while (gcIt.hasNext()) {
            ((VisualAnimationComponent)gcIt.next()).updateCurrentSprite(timeElapsed);
        }

        this.renderTargets.addAll(this.animations);
    }

    //renders all components in renderTarget
    //call this after compiling final render list
    private void renderAllVisualComponents(Graphics grContext) {
        //sorts them by layer value
        Sorter.quicksortForVC(this.renderTargets, this.renderTargets.size() / 2);

        //iterates through textures and renders them one by one
        for (int i = this.renderTargets.size() - 1; i >= 0; i--) {
            this.renderToWindow((VisualComponent)this.renderTargets.get(i), grContext);
        }
    }

    //renders all text components in renderTarget
    //call this after rendering textures
    private void renderAllText(GameObject scene, Graphics grContext) {
        //reuse renderTargets, but this time for text
        this.renderTargets.clear();
        scene.compileComponentList(this.renderTargets, GameComponent.GcType.TEXT);

        //iterates through all text and renders them to screen
        Iterator<GameComponent> gcIt = this.renderTargets.iterator();
        while (gcIt.hasNext()) {
            this.renderTextToWindow((TextComponent)gcIt.next(), grContext);
        }
    }

    //adds a KeyListener to window context
    public void addKeyListenerToWindow(KeyListener listener) {
        this.windowContext.addKeyListener(listener);
    }

    //takes a double x and y coord in world space and translates it to screen space
    public static Dimension worldSpaceToScreenSpace(double x, double y, GameComponent camera) {
        if (camera == null) {
            return new Dimension((int)(x * 1280.0 / 21.33333333), (int)(720 - (y * 720.0 / 12.0)));
        }

        return new Dimension(0, 1); //TODO add stuff for custom camera
    }
}
