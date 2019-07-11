package Render;

import Data.Communication.*;
import Data.GameObject;
import Data.Structure.*;

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
    private static final Dimension d_standardSize = new Dimension(1280, 720);
    private static final int i_standardCloseBehaviour = JFrame.DISPOSE_ON_CLOSE;
    private static final boolean b_standardResizableWindow = false;
    private static final String str_standardContextName = "LandShark AI Simulation";

    //actual member vars
    private JFrame j_windowContext;
    private Vector<GameComponent> v_gc_renderTargets;
    private boolean b_windowOpen;

    //default cstr
    public RenderEngine() {
        //setup
        this.j_windowContext = new JFrame(str_standardContextName);
        this.b_windowOpen = false;

        this.j_windowContext.setSize(d_standardSize);
        this.j_windowContext.setResizable(b_standardResizableWindow);
        this.j_windowContext.setDefaultCloseOperation(i_standardCloseBehaviour);

        this.v_gc_renderTargets = new Vector<GameComponent>(); //for rendering
    }

    /*
    call this before doing anything
    this method will make the JFrame window visible and create the double buffer required to render

    returns a String of "" if nothing goes wrong, or an error msg if an exception is caught
     */
    public String openWindow() {
        if (this.b_windowOpen) { //window guard
            return "System tried to open a window when there is one already";
        }

        try {
            this.j_windowContext.setVisible(true); //opens the window
            this.j_windowContext.createBufferStrategy(2); //double buffering
            this.b_windowOpen = true;
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
        if (!this.b_windowOpen) { //window guard
            return "System tried to close a window that doesn't exist";
        }

        try {
            this.j_windowContext.dispatchEvent(new WindowEvent(this.j_windowContext, WindowEvent.WINDOW_CLOSING)); //sends a closing event to JFrame
            this.j_windowContext.dispose(); //destroy the window context
            this.b_windowOpen = false;
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
    private void renderToWindow(VisualTextureComponent vtc_renderTarget, Graphics g_context) {
        Rectangle r = vtc_renderTarget.getRenderPlane(); //temp holder for rendering plane
        g_context.drawImage(vtc_renderTarget.getTexture(), r.x, r.y, r.width, r.height, null); //draws image to screen
    }

    /*
    this method takes a GameObject and renders every attached GameObject to the window
    TODO maybe seperate this method into a few different methods
     */
    public void renderSceneToWindow(GameObject go_scene, Vector<GameScript> v_gs_engineRequests) {
        if (!this.b_windowOpen) { //window guard
            v_gs_engineRequests.add(new LogRequest("Renderer attempted to draw frame to a closed window"));
            return;
        }

        try {
            //initializes some variables
            BufferStrategy bs_buffer = this.j_windowContext.getBufferStrategy(); //get connected buffer
            Graphics g_context = bs_buffer.getDrawGraphics();

            //grabs all the textures into a single vector
            this.v_gc_renderTargets.clear(); //clear before compiling list
            go_scene.compileComponentList(this.v_gc_renderTargets, GameComponent.gcType.VISUAL_TEXTURE);

            //iterates through textures and renders them one by one
            Iterator<GameComponent> gc_it = this.v_gc_renderTargets.iterator();
            while (gc_it.hasNext()) {
                this.renderToWindow((VisualTextureComponent)gc_it.next(), g_context);
            }

            g_context.dispose();
            bs_buffer.show(); //flush the buffer to screen
        }
        catch (Exception error) {
            //error.printStackTrace();
            v_gs_engineRequests.add(new LogRequest("Renderer encountered an error while attempting to draw frame"));
        }
    }

    //adds a KeyListener to window context
    public void addKeyListenerToWindow(KeyListener kl_listener) {
        this.j_windowContext.addKeyListener(kl_listener);
    }

    //takes a double x and y coord in world space and translates it to screen space
    public static int worldSpaceToScreenSpace (double d_x, double d_y) {
        return 0; //TODO temp
    }
}
