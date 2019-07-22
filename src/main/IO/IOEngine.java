package main.IO;

import main.Data.Communication.GameScript;
import main.Data.Communication.LogRequest;
import main.Data.GameExceptions.FileNotOpenException;
import main.Data.GameExceptions.NoDataException;
import main.GameLogic.GameScore;

import java.util.Vector;

/*

This class will handle all Outputs to console and file main.IO
TODO do the file input part XD

 */
public class IOEngine {
    private GameFileWriter logWriter; //responsible for writing game logs
    private GameFileReader scoreReader; //responsible for reading high scores

    //cstr
    public IOEngine() {
        this.logWriter = new GameFileWriter("./Game/System/Logs/game_active.log");
        this.scoreReader = new GameFileReader("./Game/System/Data/scores.sav");
        System.out.println(this.logWriter.openFile(true)); //resets the file and opens it
        System.out.println(this.scoreReader.openFile()); //opens the file for reading
    }

    /*
    this method takes a GameScript obj and processes it, before passing data on to the appropriate methods
     */
    public void processRequest(GameScript request) {
        switch (request.getCmd()) {
            case GameScript.LOG_DATA:
                this.log(request.getData());
                break;
            default: //does nothing for now TODO maybe add a return of error
                break;
        }
    }

    /*
    this method takes a string msg and logs it to the .log file, as well as outputting it to the console
     */
    public void log(String log) {
        String logOutput = this.logWriter.writeContentToFile(log, true); //saves the output of the write attempt

        if (logOutput != "") { //if there is an error with the logging
            System.out.println(logOutput); //output error to console
        }
        System.out.println(log); //also log to console
    }

    /*
    this method takes a Vector<GameScore> as an output, reads all high scores and stores them in the vector
    pushes all warnings/errors out to the scriptOutput to be logged
     */
    public void readGameScore(Vector<GameScore> scores, Vector<GameScript> scriptOutput) {
        int intScore = 0;
        try {
            intScore = this.scoreReader.getNextInt();
        } catch (NoDataException errorOne) {
            scriptOutput.add(new LogRequest("Data could not be read from file at " + this.scoreReader.getFilePath()));
        } catch (FileNotOpenException errorTwo) {
            scriptOutput.add(new LogRequest("The file at " + this.scoreReader.getFilePath() + " is not open."));
        } finally {
            scores.add(new GameScore("Tester", intScore)); //TODO temp code
        }
    }

    /*
    call this when game engine closes!
    does cleanup for the file main.IO

    DO NOT CALL THIS IF THERE ARE STILL LOGS NEEDING TO BE WRITTEN!
     */
    public void closeSystem() {
        this.logWriter.closeFile();
        this.scoreReader.closeFile();
    }

    //TODO temp method
    public void outputToConsole(GameScript data) {
        System.out.println(data.getData());
    }
}