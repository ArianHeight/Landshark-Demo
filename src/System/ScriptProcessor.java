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
    Vector<GameScript> v_gs_processQueue;

    //cstr
    public ScriptProcessor() {
        this.v_gs_processQueue = new Vector<GameScript>();
    }

    /*
    this method will add a PROCESS_DATA gamescript to the queue for future processing
     */
    public void addScriptToProcess(GameScript gs_script) {
        this.v_gs_processQueue.add(gs_script);
    }

    /*
    this method takes a Vector<GameScript> as an output
    it processes all queued scripts and adds them to the gamescript

    clears queue after processing
     */
    public void processScriptsInQueue(Vector<GameScript> v_gs_output) {
        Iterator<GameScript> gs_it = this.v_gs_processQueue.iterator();
        GameScript gs_temp = null;
        while (gs_it.hasNext()) {
            gs_temp = gs_it.next();

            if (gs_temp.getData().equals("end")) {
                v_gs_output.add(new EndProgramRequest());
            }
        }

        this.v_gs_processQueue.clear();
    }
}
