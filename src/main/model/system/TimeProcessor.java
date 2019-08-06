package main.model.system;

import main.model.data.communication.GameScript;

import java.time.format.DateTimeFormatter;
import java.time.LocalTime;

/*

This class will take care of all time-related tasks sent to it by the game engine
Intellij marks these as errors because of @since 1.8.0 from java.time
please ignore that because I know for a fact that this is being compiled in java jdk 1.8

 */
public class TimeProcessor {
    //member vars
    private DateTimeFormatter localTimeFormat; //format for tagging w/ time
    private LocalTime currentLocalTime; //stores the local time

    private double timeElapsed; //time since the last tick() call in seconds
    private long timeStampOne; //time stamp in milliseconds since unix epoch
    private long timeStampTwo; //time stamp 2 in millis since unix epoch

    //cstr
    public TimeProcessor() {
        //initializes all vars
        this.localTimeFormat = DateTimeFormatter.ofPattern("HH:mm:ss");
        this.currentLocalTime = LocalTime.now();
        this.timeElapsed = 1.0;
        this.timeStampOne = System.currentTimeMillis();
        this.timeStampTwo = 0;
    }

    /*
    This method takes a String msg as an input and returns the same msg,
    but tagged with the local time at the beginnning
     */
    public String tagMsg(String msg) {
        this.currentLocalTime = LocalTime.now(); //set time
        return this.currentLocalTime.format(this.localTimeFormat) + " | " + msg; //tag msg
    }

    /*
    this method takes a GameScript and tags the data contained
     */
    public GameScript tagScript(GameScript msg) {
        msg.setData(this.tagMsg(msg.getData())); //tags the script
        return msg; //returns the obj
    }

    /*
    updates timeElapsed since the last tick() call
     */
    public void tick() {
        this.timeStampTwo = System.currentTimeMillis();
        this.timeElapsed = (double) (this.timeStampTwo - this.timeStampOne) / 1000.0; //get time in seconds
        this.timeStampOne = this.timeStampTwo; //set last time stamp to current stamp
    }

    //accessor for timeElapsed
    public double getTimeElapsed() {
        return this.timeElapsed;
    }
}
