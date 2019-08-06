package main.model.io;

import main.model.data.game0exceptions.FileNotOpenException;
import main.model.data.game0exceptions.NoDataException;

/*
this class will have methods that allow a class to read data from files
 */
public interface GameReadable {

    //reads a single line from a file
    public String readLineFromFile() throws FileNotOpenException, NoDataException;
}
