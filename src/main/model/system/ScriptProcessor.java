package model.system;

import model.data.communication.EndProgramRequest;
import model.data.communication.GameScript;
import model.data.communication.LogRequest;
import model.data.communication.ProcessRequest;

import java.util.Iterator;
import java.util.Vector;

/*
This method will take care of process scripts

every script of type PROCESS_DATA will come through here
 */
public class ScriptProcessor {
    Vector<GameScript> processQueue;

    //cstr
    public ScriptProcessor() {
        this.processQueue = new Vector<GameScript>();
    }

    /*
    this method will add a PROCESS_DATA gamescript to the queue for future processing
     */
    public void addScriptToProcess(GameScript script) {
        this.processQueue.add(script);
    }

    /*
    this method takes a Vector<GameScript> as an output
    it processes all queued scripts and adds them to the gamescript

    clears queue after processing
     */
    public void processScriptsInQueue(Vector<GameScript> outputVector) {
        Iterator<GameScript> it = this.processQueue.iterator();
        GameScript tempScript = null;
        while (it.hasNext()) {
            tempScript = it.next();

            if (tempScript.getData().equals("end")) {
                outputVector.add(new EndProgramRequest());
            }
        }

        this.processQueue.clear();
    }

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
