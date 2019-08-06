package main.model.system;

import main.model.data.communication.EndProgramRequest;
import main.model.data.communication.GameScript;

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
}
