package main.data.communication;

/*

Extends the GameScript class
will be a request for anything that will be sent to the main.gameLogic package from the GameEngine

 */
public class GameEventRequest extends GameScript{
    //cstr
    public GameEventRequest(String str_request) {
        super(GAME_EVENT, str_request);
    }
}
