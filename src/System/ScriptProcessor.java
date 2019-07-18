package System;

import Data.Communication.EndProgramRequest;
import Data.Communication.GameScript;

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
        Iterator<GameScript> gs_it = this.processQueue.iterator();
        GameScript gs_temp = null;
        while (gs_it.hasNext()) {
            gs_temp = gs_it.next();

            if (gs_temp.getData().equals("end")) {
                outputVector.add(new EndProgramRequest());
            }
        }

        this.processQueue.clear();
    }
}
