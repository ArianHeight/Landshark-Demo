package main.data.communication;

/*

Extends the GameScript Class
creates a logging request

 */
public class LogRequest extends GameScript{
    //cstr
    public LogRequest(String str_logMsg) {
        super(LOG_DATA, str_logMsg); //creates a GameScript with the LOG_DATA type and a msg to be displayed
    }
}
