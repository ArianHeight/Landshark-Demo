package Data.Communication;

/*

Extends the GameScript Class
creates a console printing request

 */
public class ConsolePrintRequest extends GameScript {
    //cstr
    public ConsolePrintRequest(String str_msg) {
        super(COUT_DATA, str_msg); //creates a console output request with a msg attached
    }
}
