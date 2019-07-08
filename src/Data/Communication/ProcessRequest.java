package Data.Communication;

/*

extends the GameScripts class
creates a script for processing requests
mainly used by GameEngine

 */
public class ProcessRequest extends GameScript{
    //cstr
    public ProcessRequest(String str_request) {
        super(PROCESS_DATA, str_request); //creates a game script with PROCESS_DATA type
    }
}
