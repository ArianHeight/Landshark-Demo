package Utility;

import Data.Communication.*;

import java.util.Iterator;
import java.util.Vector;

/*

This class will process raw data into Game Scripts

 */
public class DataProcessor {

    /*
    this method processes the incoming string data into LogRequests which are added to the end of the output vector
     */
    public static void processErrorData(Vector<GameScript> v_gs_output, Vector<String> v_str_data) {
        Iterator<String> it = v_str_data.iterator(); //iterates through the data and adds each one as a new log request to the output
        while (it.hasNext()) {
            v_gs_output.add(new LogRequest(it.next()));
        }
    }

    /*
    this method processes the incoming string data into ProcessRequests which are added to the end of the output vector
     */
    public static void processStringData(Vector<GameScript> v_gs_output, Vector<String> v_str_data) {
        Iterator<String> it = v_str_data.iterator(); //iterates through the data and adds each one as a new process request to the output
        while (it.hasNext()) {
            v_gs_output.add(new ProcessRequest(it.next()));
        }
    }
}
