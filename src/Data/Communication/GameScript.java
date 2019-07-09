package Data.Communication;

/*

abstract class for all requests going to and from the game engine
essentially a data packet

 */
public abstract class GameScript {
    //const name fields used for typing the script
    public final static int LOG_DATA = 0;
    public final static int COUT_DATA = 1;
    public final static int PROCESS_DATA = 2;

    private int i_cmd; //the type of script, for example LOG_DATA would be processed as a request to log the information
    private String str_data; //contains potentially extra information about the script

    //cstr, creates a gamescript with i_type for type and str_info for data
    public GameScript(int i_type, String str_info) {
        this.i_cmd = i_type;
        this.str_data = str_info;
    }

    /*
    REQUIRES:Nothing
    MODIFIES:None
    EFFECT:Accesses cmd type of the script
     */
    public int getCmd() {
        return this.i_cmd;
    }

    /*
    REQUIRES:Nothing
    MODIFIES:None
    EFFECT:Accesses extra information contained in the script
     */
    public String getData() {
        return this.str_data;
    }
}