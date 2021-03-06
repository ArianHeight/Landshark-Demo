package model.io;

import model.data.game0exceptions.FileNotOpenException;
import model.data.game0exceptions.NoDataException;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;

/*
This class will handle all file reading related activities
 */
public class GameFileReader implements GameFileHandler, GameReadable {
    private String filePath;
    private File file;
    private Scanner reader;
    private boolean isOpen = false; //whether the file is open or not

    //cstr
    public GameFileReader(String path) {
        this.filePath = path;
    }

    /*
    IMPORTANT:This method is to be called before any other method can be called, may chain from cstr in future
    MODIFIES:this
    EFFECT:Opens a file without truncating it, for reading. Returns a String "" for no errors, or an error msg if one
           is encountered
     */
    @Override
    public String openFile() {
        if (this.isOpen) {
            return "The file system is attempting to open at " + this.filePath + " is opened already";
        }

        try {
            this.file = new File(this.filePath); //opens the file

            if (!this.file.exists()) {
                this.file.createNewFile(); //creates new file in place if the file does not exist
            }

            this.reader = new Scanner(this.file);
        } catch (IOException error) { //catch io exceptions
            return "system encountered an error while trying to open a file at " + this.filePath;
        }

        this.isOpen = true; //file has been opened no problems encountered
        return ""; //return "" for no error encountered
    }

    /*
    IMPORTANT:This method is to be called after everything that you want to be written has been written
    REQUIRES:The file has been opened(this.OpenFile() has been called once and this method has not)
    MODIFIES:this
    EFFECT:Closes a file that was open for reading
           this method returns an empty string if every thing goes well,
           but will return an error msg if there is an exception thrown
     */
    @Override
    public String closeFile() {
        if (!this.isOpen) { //file guard
            return "The file system is attempting to close at " + this.filePath + " is not open...";
        }

        try {
            this.reader.close(); //closes the buffered writer
        } catch (Exception error) {
            //passes error msg out
            return "system encountered an error while trying to close a file at " + this.filePath;
        }

        this.isOpen = false; //file has been closed no problems encountered
        return ""; //no errors
    }

    /*
    returns the next int in the file
    if there is not next int, throw a NoDataException
    if file is not open, throw a FileNotOpenException
     */
    public int getNextInt() throws FileNotOpenException, NoDataException {
        if (!this.isOpen) {
            throw new FileNotOpenException();
        }

        if (!this.reader.hasNextInt()) {
            throw new NoDataException();
        }

        return this.reader.nextInt();
    }

    /*
    returns the next double in the file
    if there is not next double, throw a NoDataException
    if file is not open, throw a FileNotOpenException
     */
    public double getNextDouble() throws FileNotOpenException, NoDataException {
        if (!this.isOpen) {
            throw new FileNotOpenException();
        }

        if (!this.reader.hasNextDouble()) {
            throw new NoDataException();
        }

        return this.reader.nextDouble();
    }

    /*
    returns the next line if there is a next line
    if there is no next line, throw a NoDataException
    if file is not open, throw a FileNotOpenException
     */
    @Override
    public String readLineFromFile() throws FileNotOpenException, NoDataException {
        if (!this.isOpen) {
            throw new FileNotOpenException();
        }

        if (!this.reader.hasNextLine()) {
            throw new NoDataException();
        }

        return this.reader.nextLine();
    }

    //this method returns whether or not the file has been opened for writing
    @Override
    public boolean isOpen() {
        return this.isOpen;
    }

    //returns the file path saved b this writer
    @Override
    public String getFilePath() {
        return this.filePath;
    }
}
