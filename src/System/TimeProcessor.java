package System;

import java.time.format.DateTimeFormatter;
import java.time.LocalTime;

/*

This class will take care of all time-related tasks sent to it by the game engine

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
    TODO description
     */
    public String tagMsg(String str_msg) {
        this.lt_currentLocalTime = LocalTime.now();
        return this.lt_currentLocalTime.format(this.dtf_localTimeFormat) + " | " + str_msg;
    }
}
