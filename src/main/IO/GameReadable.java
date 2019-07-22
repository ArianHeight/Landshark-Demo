package main.IO;

import main.Data.GameExceptions.FileNotOpenException;
import main.Data.GameExceptions.NoDataException;

/*
this class will have methods that allow a class to read data from files
 */
public interface GameReadable {

    //reads a single line from a file
    public String readLineFromFile() throws FileNotOpenException, NoDataException;
}
