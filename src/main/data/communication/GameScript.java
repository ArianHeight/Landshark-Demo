package main.data.communication;

/*

abstract class for all requests going to and from the game engine
essentially a data packet

 */
public abstract class GameScript {
    //const name fields used for typing the script
    public static final int LOG_DATA = 0;
    public static final int COUT_DATA = 1;
    public static final int PROCESS_DATA = 2;
    public static final int END_PROGRAM = 3;
    public static final int GAME_EVENT = 4;
    public static final int COLLISION_RESPONSE = 5;

    private int cmd; //the type of script, for example LOG_DATA would be processed as a request to log the information
    private String data; //contains potentially extra information about the script

    //cstr, creates a gamescript with type for type and info for data
    protected GameScript(int type, String info) {
        this.cmd = type;
        this.data = info;
    }

    /*
    REQUIRES:Nothing
    MODIFIES:None
    EFFECT:Accesses cmd type of the script
     */
    public int getCmd() {
        return this.cmd;
    }

    /*
    REQUIRES:Nothing
    MODIFIES:None
    EFFECT:Accesses extra information contained in the script
     */
    public String getData() {
        return this.data;
    }

    /*
    REQUIRES:input from a public int field in this class
    MODIFIES:this
    EFFECT:Sets a new script type based on the input
     */
    private void setCmd(int newCmd) {
        this.cmd = newCmd;
    }

    /*
    REQUIRES:String input
    MODIFIES:this
    EFFECT:Sets the data contained to newData
     */
    public void setData(String newData) {
        this.data = newData;
    }
}
