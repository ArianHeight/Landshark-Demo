package model.io;

import model.data.communication.GameScript;
import model.data.communication.LogRequest;
import model.data.game0exceptions.FileNotOpenException;
import model.data.game0exceptions.ImageDidNotLoadException;
import model.data.game0exceptions.NoDataException;
import model.data.structure.VisualAnimationComponent;
import model.game0logic.GameScore;
import model.utility.HitboxAabb;

import java.awt.*;
import java.util.Iterator;
import java.util.Vector;

/*

This class will handle all Outputs to console and file model.io

 */
public class IoEngine {
    private GameFileWriter logWriter; //responsible for writing game logs
    private GameFileReader scoreReader; //responsible for reading high scores
    private ImageManager textureMngr; //responsible for managing all Images
    private AnimationManager animationMngr; //responsible for managing all animations
    private Vector<String> logQueue; //holds a queue for logging

    //cstr
    public IoEngine() {
        this.logWriter = new GameFileWriter("./Game/system/Logs/game_active.log");
        this.scoreReader = new GameFileReader("./Game/system/data/scores.sav");
        this.textureMngr = new ImageManager();
        this.animationMngr = new AnimationManager(this.textureMngr);
        this.logQueue = new Vector<String>();
        System.out.println(this.logWriter.openFile(true)); //truncates the file
        System.out.println(this.logWriter.closeFile());
        System.out.println(this.scoreReader.openFile()); //opens the file for reading
    }

    /*
    this method takes a GameScript obj and processes it, before passing data on to the appropriate methods
    log requests will have data extracted and stored inside a queue
     */
    public void processRequest(GameScript request) {
        switch (request.getCmd()) {
            case GameScript.LOG_DATA:
                this.logQueue.add(request.getData());
                break;
            default: //does nothing for now TODO maybe add a return of error
                break;
        }
    }

    /*
    this method takes all logs in the queue and log them all, then clears the queue
     */
    public void logAll() {
        String msg = this.logWriter.openFile();

        if (!msg.equals("")) { //file open guard
            System.out.println(msg);
            return;
        }

        Iterator<String> it = this.logQueue.iterator();
        while (it.hasNext()) {
            this.log(it.next());
        }

        this.logQueue.clear();
        msg = this.logWriter.closeFile(); //file close guard
        if (!msg.equals("")) {
            System.out.println(msg);
        }
    }

    /*
    this method takes a string msg and logs it to the .log file, as well as outputting it to the console
     */
    private void log(String log) {
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
            scores.add(new GameScore("Tester", intScore)); //TODO temp code
        } catch (NoDataException errorOne) {
            scriptOutput.add(new LogRequest("data could not be read from file at " + this.scoreReader.getFilePath()));
        } catch (FileNotOpenException errorTwo) {
            scriptOutput.add(new LogRequest("The file at " + this.scoreReader.getFilePath() + " is not open."));
        }
    }

    /*
    this method takes a filePath to the Image and loads it, returning the Image itself
    second param is the error script output
     */
    public Image loadTexture(String filePath, Vector<GameScript> scriptOutput) {
        try {
            return this.textureMngr.loadImage(filePath);
        } catch (ImageDidNotLoadException error) {
            scriptOutput.add(new LogRequest("Could not load image at: " + filePath));
        }

        return null;
    }

    /*
    this method takes a filePath to an Animation and loads it, returning a copy of the vac
    the second param is the error script output
     */
    public VisualAnimationComponent loadAnimation(String filePath, Vector<GameScript> scriptOutput) {
        HitboxAabb defaultHb = new HitboxAabb(0.0, 1.0, 1.0, 0.0);
        VisualAnimationComponent returnVal = this.animationMngr.makeAnimation(filePath,
                new Rectangle(), defaultHb, 0);

        Vector<String> errors = this.animationMngr.getErrors();
        for (String str : errors) {
            scriptOutput.add(new LogRequest(str));
        }
        this.animationMngr.clearLogsAndErrors();

        return returnVal;
    }

    /*
    call this when game engine closes!
    does cleanup for the file io

    DO NOT CALL THIS IF THERE ARE STILL LOGS NEEDING TO BE WRITTEN!
     */
    public void closeSystem() {
        this.logWriter.closeFile();
        this.scoreReader.closeFile();
    }
}
