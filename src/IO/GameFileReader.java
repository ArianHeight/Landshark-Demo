package IO;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;

/*
This class will handle all file reading related activities
 */
public class GameFileReader implements GameFileHandler, GameReadable {
    private String str_filePath;
    private File f;
    private Scanner sc_reader;
    private boolean b_isOpen = false; //whether the file is open or not

    //cstr
    public GameFileReader(String str_path) {
        this.str_filePath = str_path;
    }

    /*
    IMPORTANT:This method is to be called before any other method can be called, may chain from cstr in future
    MODIFIES:this
    EFFECT:Opens a file without truncating it, for reading. Returns a String "" for no errors, or an error msg if one
           is encountered
     */
    @Override
    public String openFile() {
        if (this.b_isOpen) {
            return "The file system is attempting to open at " + this.str_filePath + " is opened already";
        }

        try {
            this.f = new File(this.str_filePath); //opens the file

            if (!this.f.exists()) {
                this.f.createNewFile(); //creates new file in place if the file does not exist
            }

            this.sc_reader = new Scanner(this.f);
        }
        catch (IOException error) { //catch io exceptions
            return "System encountered an error while trying to open a file at " + this.str_filePath;
        }

        this.b_isOpen = true; //file has been opened no problems encountered
        return ""; //return "" for no error encountered
    }

    /*
    IMPORTANT:This method is to be called after everything that you want to be written has been written
    REQUIRES:The file has been opened(this.OpenFile() has been called once and this method has not)
    MODIFIES:this
    EFFECT:Closes a file that was open for reading
           this method returns an empty string if every thing goes well, but will return an error msg if there is an exception thrown
     */
    @Override
    public String closeFile() {
        if (!this.b_isOpen) { //file guard
            return "The file system is attempting to close at " + this.str_filePath + " is not open...";
        }

        try {
            this.sc_reader.close(); //closes the buffered writer
        }
        catch (Exception error) {
            return "System encountered an error while trying to close a file at " + this.str_filePath; //passes error msg out
        }

        this.b_isOpen = false; //file has been closed no problems encountered
        return ""; //no errors
    }

    /*
    returns the next int in the file
    if there is not next int, return 0
    if file is not open, return -1
     */
    public int getNextInt() {
        if (!this.b_isOpen) {
            return -1;
        }

        if (this.sc_reader.hasNextInt()) {
            return this.sc_reader.nextInt();
        }

        return 0;
    }

    /*
    returns the next line if there is a next line
    returns "" if there is none or if the file is not open
     */
    @Override
    public String readLineFromFile() {
        if (!this.b_isOpen) {
            return "";
        }

        if (this.sc_reader.hasNextLine()) {
            return this.sc_reader.nextLine();
        }

        return "";
    }

    //this method returns whether or not the file has been opened for writing
    @Override
    public boolean isOpen() {
        return this.b_isOpen;
    }
}
