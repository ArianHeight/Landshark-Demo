package main.io;

import main.data.game0exceptions.FileNotOpenException;
import main.data.game0exceptions.NoDataException;

/*
this class will have methods that allow a class to read data from files
 */
public interface GameReadable {

    //reads a single line from a file
    public String readLineFromFile() throws FileNotOpenException, NoDataException;
}
