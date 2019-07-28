package main.utility;

import main.data.communication.*;

import java.util.Iterator;
import java.util.Vector;

/*

This class will process raw data into Game Scripts

 */
public class DataProcessor {

    /*
    this method processes the incoming string data into LogRequests which are added to the end of the output vector
     */
    public static void processErrorData(Vector<GameScript> output, Vector<String> data) {
        //iterates through the data and adds each one as a new log request to the output
        Iterator<String> it = data.iterator();
        while (it.hasNext()) {
            output.add(new LogRequest("ERROR: " + it.next()));
        }
    }

    /*
    this method processes the incoming string data into ProcessRequests which are added to the end of the output vector
     */
    public static void processStringData(Vector<GameScript> output, Vector<String> data) {
        //iterates through the data and adds each one as a new process request to the output
        Iterator<String> it = data.iterator();
        String temp = "";
        while (it.hasNext()) {
            temp = it.next();
            if (temp.startsWith("/")) { //check if it's a command
                //take out the / at the front
                output.add(new ProcessRequest(temp.replaceFirst("/", "")));
            }
        }
    }
}
