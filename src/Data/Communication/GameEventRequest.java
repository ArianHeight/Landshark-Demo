package Data.Communication;

/*

Extends the GameScript class
will be a request for anything that will be sent to the GameLogic package from the GameEngine

 */
public class GameEventRequest extends GameScript{
    //cstr
    public GameEventRequest(String str_request) {
        super(GAME_EVENT, str_request);
    }
}
