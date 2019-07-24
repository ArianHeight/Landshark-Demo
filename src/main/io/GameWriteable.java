package main.io;

/*
this interface contains a method that allows the class to write to a file
 */
public interface GameWriteable {

    //write the string to the file, with a newline if the boolean is true
    //returns an error msg if there is an exception, or ""
    public String writeContentToFile(String str_msg, boolean b_newline);
}
