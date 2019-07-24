package main.Data;

/*
A public interface that will require the implementing class to have a response method
for player inputs/controls
 */
public interface ControlInterface {
    /*
    REQUIRES:a valid String input as implemented in each class
    EFFECT:takes a String input and executes some action based on which the input is
     */
    public void inputResponse(String input);

    /*
    REQUIRES:none
    MODIFIES:none
    EFFECT:returns whether or not the hp of the obj called is over 0
     */
    public boolean isAlive();
}
