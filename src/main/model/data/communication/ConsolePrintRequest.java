package main.model.data.communication;

/*

Extends the GameScript Class
creates a console printing request

 */
public class ConsolePrintRequest extends GameScript {
    //cstr
    public ConsolePrintRequest(String msg) {
        super(COUT_DATA, msg); //creates a console output request with a msg attached
    }
}
