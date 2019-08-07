package model.data.communication;

/*

extends the GameScripts class
creates a script for processing requests
mainly used by GameEngine

 */
public class ProcessRequest extends GameScript {
    //cstr
    public ProcessRequest(String request) {
        super(PROCESS_DATA, request); //creates a game script with PROCESS_DATA type
    }
}
