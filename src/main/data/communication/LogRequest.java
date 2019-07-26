package main.data.communication;

/*

Extends the GameScript Class
creates a logging request

 */
public class LogRequest extends GameScript {
    //cstr
    public LogRequest(String logMsg) {
        super(LOG_DATA, logMsg); //creates a GameScript with the LOG_DATA type and a msg to be displayed
    }
}
