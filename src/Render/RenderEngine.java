package Render;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowEvent;
import java.awt.image.BufferStrategy;

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

    //default cstr
    public RenderEngine() {
        //setup
        this.j_windowContext = new JFrame(str_standardContextName);

        this.j_windowContext.setSize(d_standardSize);
        this.j_windowContext.setResizable(b_standardResizableWindow);
        this.j_windowContext.setDefaultCloseOperation(i_standardCloseBehaviour);
    }

    /*
    call this before doing anything
    this method will make the JFrame window visible and create the double buffer required to render

    returns a String of "" if nothing goes wrong, or an error msg if an exception is caught
     */
    public String openWindow() {
        try {
            this.j_windowContext.setVisible(true); //opens the window
            this.j_windowContext.createBufferStrategy(2); //double buffering
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
        try {
            this.j_windowContext.dispatchEvent(new WindowEvent(this.j_windowContext, WindowEvent.WINDOW_CLOSING)); //sends a closing event to JFrame
            this.j_windowContext.dispose(); //destroy the window context
        }
        catch (Exception error) {
            error.printStackTrace();
            return "Rendering Engine encountered an error while trying to close a window"; //something went wrong
        }

        return "";
    }
}
