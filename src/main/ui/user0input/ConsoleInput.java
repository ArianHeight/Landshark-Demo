package main.ui.user0input;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;
import java.util.Vector;

/*

The Console class handles console inputs and everything related to that

-getConsoleOutput attempts to read from the console, and will return true if a user has typed something

 */
public class ConsoleInput {
    //member vars
    //a variable used to hold the current line in the console(null if no input detected)
    private static String currentLine = null;
    //input stream reader for main.system.in
    private static InputStreamReader reader = new InputStreamReader(System.in);
    //console reader reading from main.system.in
    private static BufferedReader console = new BufferedReader(reader);

    /*
    This method takes an ArrayList<String> reference output as a parameter, that ArrayList will be the output for the
    method. this also takes another ArrayList<String> ref errors as a parameter, all errors encountered will be pushed
    through here
    returns true if a user has typed something into the console or if an error has been encountered, false otherwise

    this method gets one line of user input from the console and pushes it to output

    parameter output will be cleared first, and all lines of input from the console will be added to the end of it
     */
    public static boolean getConsoleOutput(Vector<String> output, Vector<String> errors) {
        //gets al output ready for processing
        output.clear();
        errors.clear();

        //create returnVal
        boolean returnVal = false;

        //try catch for reading from console
        try {
            if (console.ready()) {
                currentLine = console.readLine(); //reads line from console

                if (currentLine != null) { //if user has input
                    output.add(currentLine);
                    returnVal = true; //user has inputted, return true
                }
            }
        } catch (IOException error) { //add error msg to output
            errors.add("main.system encountered main.io error whilst reading msg from console...");
            output.clear(); //clears output

            return true; //early return for error encountered
        }

        return returnVal;
    }
}
