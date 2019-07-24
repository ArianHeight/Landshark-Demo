package main.io;


import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

/*

this class is responsible for writing to a single file
to be called from IOEngine class whenever a request for logging or saving comes in

BIG WARNING THIS CLASS WILL NOT CLOSE FILES BY ITSELF PLEASE MANAGE CORRECTLY AND CALL close()!

 */
public class GameFileWriter implements GameFileHandler, GameWriteable {
    private String filePath;
    private File f;
    private FileWriter fwWriter;
    private BufferedWriter bwWriter;
    private boolean isOpen = false; //whether the file is open or not

    //cstr
    public GameFileWriter(String path) {
        this.filePath = path; //sets the filepath
    }

    /*
    IMPORTANT:This method is to be called before any other method can be called, may chain from cstr in future
    MODIFIES:this
    EFFECT:Opens a file without truncating it, for writing
           Takes a boolean param truncFile which will determine if the file will be truncated before any writing
           this method returns an empty string if every thing goes well, but will return an error msg if there is an error
     */
    public String openFile(boolean truncFile) {
        if (this.isOpen) {
            return "The file system is attempting to open at " + this.filePath + " is opened already";
        }

        try {
            this.f = new File(this.filePath); //opens the file

            if (!this.f.exists()) {
                this.f.createNewFile(); //creates new file in place if the file does not exist
            }

            //truncates the file if truncFile is true
            this.fwWriter = new FileWriter(this.f, !truncFile); //makes file writer
            this.bwWriter = new BufferedWriter(this.fwWriter); //makes buffered writer
        }
        catch (IOException error) { //catch io exceptions
            return "system encountered an error while trying to open a file at " + this.filePath;
        }

        this.isOpen = true; //file has been opened no problems encountered
        return ""; //return "" for no error encountered
    }

    //opens the file without truncation
    @Override
    public String openFile() {
        return this.openFile(false);
    }

    /*
    IMPORTANT:This method is to be called after everything that you want to be written has been written
    REQUIRES:The file has been opened(this.OpenFile(boolean) has been called once and this method has not)
    MODIFIES:this
    EFFECT:Closes a file that was open for writing
           this method returns an empty string if every thing goes well, but will return an error msg if there is an exception thrown
     */
    @Override
    public String closeFile() {
        if (!this.isOpen) { //file guard
            return "The file system is attempting to close at " + this.filePath + " is not open...";
        }

        try {
            this.bwWriter.close(); //closes the buffered writer
        }
        catch (IOException error) {
            return "system encountered an error while trying to close a file at " + this.filePath; //passes error msg out
        }

        this.isOpen = false; //file has been closed no problems encountered
        return ""; //no errors
    }

    /*
    REQUIRES:A legible message to be written in msg
    MODIFIES:this
    EFFECT:Takes String message as an input and writes that msg to the file if it has been opened
           Takes a boolean var as an input and will create a newline based on that
           Please remember to call openFile beforehand
     */
    @Override
    public String writeContentToFile(String msg, boolean newline) {
        if (!this.isOpen) { //file guard
            return "system encountered an error while trying to write msg \"" + msg + "\" to a file at " + this.filePath + " which was not opened";
        }

        try {
            if (newline) {
                msg += "\n"; //adds a newline to the end of the msg
            }

            this.bwWriter.write(msg); //writes the msg to the file
        }
        catch (IOException error) { //catch io exceptions
            return "system encountered an error while trying to write msg " + msg + " to a file at " + this.filePath;
        }

        return ""; //no problems during execution
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
