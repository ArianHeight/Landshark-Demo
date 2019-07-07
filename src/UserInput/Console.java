package UserInput;
import java.io.*;
import java.util.ArrayList;

/*

The Console class handles console inputs and everything related to that

-getConsoleOutput attempts to read from the console, and will return true if a user has typed something

 */
public class Console {
    //member vars
    private static String str_currentLine = null; //a variable used to hold the current line in the console(null if no input detected)
    private static BufferedReader br_console = new BufferedReader(new InputStreamReader(System.in)); //console reader reading from System.in

    /*
    This method takes an ArrayList<String> reference al_str_output as a parameter, that ArrayList will be the output for the method
    this also takes another ArrayList<String> ref al_str_errors as a parameter, all errors encountered will be pushed through here
    returns true if a user has typed something into the console, false otherwise

    this method gets one line of user input from the console and pushes it to al_str_output

    parameter al_str_output will be cleared first, and all lines of input from the console will be added to the end of it
     */
    public static boolean getConsoleOutput(ArrayList<String> al_str_output, ArrayList<String> al_str_errors) {
        //gets al output ready for processing
        al_str_output.clear();
        al_str_errors.clear();
        al_str_output.ensureCapacity(1);
        al_str_errors.ensureCapacity(1);

        //create returnVal
        boolean returnVal = false;

        //try catch for reading from console
        try {
            str_currentLine = br_console.readLine(); //reads line from console

            if (str_currentLine != null) //if user has input
            {
                al_str_output.add(str_currentLine);
                returnVal = true; //user has inputted, return true
            }
        }
        catch (IOException error) {
            al_str_errors.add("System encountered IO error whilst reading msg from console..."); //add error msg to output
            al_str_output.clear(); //clears output

            return false; //early return for error encountered
        }

        return returnVal;
    }
}
