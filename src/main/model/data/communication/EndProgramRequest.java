package model.data.communication;

/*

Extends the GameScript class
A request specifically made to end the program

 */
public class EndProgramRequest extends GameScript {
    //cstr
    public EndProgramRequest() {
        super(END_PROGRAM, "");
    }

    //locking the setData() to do nothing because there is no data to set
    @Override
    public void setData(String newData) {
        //do nothing
    }
}
