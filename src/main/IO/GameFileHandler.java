package main.IO;

/*
this interface will be used for every thing that handles file main.IO
(reads/writes)
 */
public interface GameFileHandler {
    //opens the file
    public String openFile();

    //closes the file
    public String closeFile();

    //returns true if the file is open, false otherwise
    public boolean isOpen();

    //returns the file path
    public String getFilePath();
}