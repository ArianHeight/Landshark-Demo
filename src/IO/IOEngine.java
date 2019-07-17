package IO;

import Data.Communication.GameScript;
import GameLogic.GameScore;

import java.util.Vector;

/*

This class will handle all Outputs to console and file IO
TODO do the file input part XD

 */
public class IOEngine {
    private GameFileWriter gfw_logWriter; //responsible for writing game logs
    private GameFileReader gfr_scoreReader; //responsible for reading high scores

    //cstr
    public IOEngine() {
        this.gfw_logWriter = new GameFileWriter("./Game/System/Logs/game_active.log");
        this.gfr_scoreReader = new GameFileReader("./Game/System/Data/scores.sav");
        System.out.println(this.gfw_logWriter.openFile(true)); //resets the file and opens it
        System.out.println(this.gfr_scoreReader.openFile()); //opens the file for reading
    }

    /*
    this method takes a GameScript obj and processes it, before passing data on to the appropriate methods
     */
    public void processRequest(GameScript gs_request) {
        switch (gs_request.getCmd()) {
            case GameScript.LOG_DATA:
                this.log(gs_request.getData());
                break;
            default: //does nothing for now TODO maybe add a return of error
                break;
        }
    }

    /*
    this method takes a string msg and logs it to the .log file, as well as outputting it to the console
     */
    public void log(String str_log) {
        String str_logOutput = this.gfw_logWriter.writeContentToFile(str_log, true); //saves the output of the write attempt

        if (str_logOutput != "") { //if there is an error with the logging
            System.out.println(str_logOutput); //output error to console
        }
        System.out.println(str_log); //also log to console
    }

    /*
    this method takes a Vector<GameScore> as an output, reads all high scores and stores them in the vector
     */
    public void readGameScore(Vector<GameScore> v_gsc_scores) {
        v_gsc_scores.add(new GameScore("Tester", this.gfr_scoreReader.getNextInt())); //TODO temp code
    }

    /*
    call this when game engine closes!
    does cleanup for the file IO

    DO NOT CALL THIS IF THERE ARE STILL LOGS NEEDING TO BE WRITTEN!
     */
    public void closeSystem() {
        this.gfw_logWriter.closeFile();
        this.gfr_scoreReader.closeFile();
    }

    //TODO temp method
    public void outputToConsole(GameScript gs_data) {
        System.out.println(gs_data.getData());
    }
}
