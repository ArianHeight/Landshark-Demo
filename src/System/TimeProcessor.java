package System;

import Data.Communication.GameScript;

import java.time.format.DateTimeFormatter;
import java.time.LocalTime;

/*

This class will take care of all time-related tasks sent to it by the game engine
Intellij marks these as errors because of @since 1.8.0 from java.time
please ignore that because I know for a fact that this is being compiled in java jdk 1.8

 */
public class TimeProcessor {
    //member vars
    private DateTimeFormatter dtf_localTimeFormat; //format for tagging w/ time
    private LocalTime lt_currentLocalTime; //stores the local time

    private double d_timeElapsed; //time since the last tick() call in seconds
    private long l_timeStampOne; //time stamp in milliseconds since unix epoch
    private long getL_timeStampTwo; //time stamp 2 in millis since unix epoch

    //cstr
    public TimeProcessor() {
        //initializes all vars
        this.dtf_localTimeFormat = DateTimeFormatter.ofPattern("HH:mm:ss");
        this.lt_currentLocalTime = LocalTime.now();
        this.d_timeElapsed = 1.0;
        this.l_timeStampOne = System.currentTimeMillis();
        this.getL_timeStampTwo = 0;
    }

    /*
    This method takes a String msg as an input and returns the same msg, but tagged with the local time at the beginnning
     */
    public String tagMsg(String str_msg) {
        this.lt_currentLocalTime = LocalTime.now(); //set time
        return this.lt_currentLocalTime.format(this.dtf_localTimeFormat) + " | " + str_msg; //tag msg
    }

    /*
    this method takes a GameScript and tags the data contained
     */
    public GameScript tagScript(GameScript gs_msg) {
        gs_msg.setData(this.tagMsg(gs_msg.getData())); //tags the script
        return gs_msg; //returns the obj
    }

    /*
    updates d_timeElapsed since the last tick() call
     */
    public void tick() {
        this.getL_timeStampTwo = System.currentTimeMillis();
        this.d_timeElapsed = (double) (this.getL_timeStampTwo - this.l_timeStampOne) / 1000.0; //get time in seconds
        this.l_timeStampOne = this.getL_timeStampTwo; //set last time stamp to current stamp
    }

    //accessor for d_timeElapsed
    public double getTimeElapsed() {
        return this.d_timeElapsed;
    }
}
