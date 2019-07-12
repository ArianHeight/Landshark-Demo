package IO;


import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

/*

this class is responsible for writing to a single file
to be called from IOEngine class whenever a request for logging or saving comes in

BIG WARNING THIS CLASS WILL NOT CLOSE FILES BY ITSELF PLEASE MANAGE CORRECTLY AND CALL close()!

 */
public class GameFileWriter {
    private String str_filePath;
    private File f;
    private FileWriter fw_writer;
    private BufferedWriter bw_writer;
    private boolean b_isOpen = false; //whether the file is open or not

    //cstr
    public GameFileWriter(String str_path) {
        this.str_filePath = str_path; //sets the filepath
    }

    /*
    IMPORTANT:This method is to be called before any other method can be called, may chain from cstr in future
    MODIFIES:this
    EFFECT:Opens a file without truncating it, for writing
           Takes a boolean param b_truncFile which will determine if the file will be truncated before any writing
           this method returns an empty string if every thing goes well, but will return an error msg if there is an error
     */
    public String openFile(boolean b_truncFile) {
        if (this.b_isOpen) {
            return "The file system is attempting to open at " + this.str_filePath + " is opened already";
        }

        try {
            this.f = new File(this.str_filePath); //opens the file

            if (!this.f.exists()) {
                this.f.createNewFile(); //creates new file in place if the file does not exist
            }

            this.fw_writer = new FileWriter(this.f); //makes file writer
            this.bw_writer = new BufferedWriter(this.fw_writer); //makes buffered writer

            if (b_truncFile) {
                this.bw_writer.flush(); //truncates the file if b_truncFile is true
            }
        }
        catch (IOException error) { //catch io exceptions
            return "System encountered an error while trying to open a file at " + this.str_filePath;
        }

        this.b_isOpen = true; //file has been opened no problems encountered
        return ""; //return "" for no error encountered
    }

    /*
    IMPORTANT:This method is to be called after everything that you want to be written has been written
    REQUIRES:The file has been opened(this.OpenFile(boolean) has been called once and this method has not)
    MODIFIES:this
    EFFECT:Closes a file that was open for writing
           this method returns an empty string if every thing goes well, but will return an error msg if there is an exception thrown
     */
    public String closeFile() {
        if (!this.b_isOpen) { //file guard
            return "The file system is attempting to close at " + this.str_filePath + " is not open...";
        }

        try {
            this.bw_writer.close(); //closes the buffered writer
        }
        catch (IOException error) {
            return "System encountered an error while trying to close a file at " + this.str_filePath; //passes error msg out
        }

        this.b_isOpen = false; //file has been closed no problems encountered
        return ""; //no errors
    }

    /*
    REQUIRES:A legible message to be written in str_msg
    MODIFIES:this
    EFFECT:Takes String message as an input and writes that msg to the file if it has been opened
           Takes a boolean var as an input and will create a newline based on that
           Please remember to call openFile beforehand
     */
    public String writeContentToFile(String str_msg, boolean b_newline) {
        if (!this.b_isOpen) { //file guard
            return "System encountered an error while trying to write msg \"" + str_msg + "\" to a file at " + this.str_filePath + " which was not opened";
        }

        try {
            if (b_newline) {
                str_msg += "\n"; //adds a newline to the end of the msg
            }

            this.bw_writer.write(str_msg); //writes the msg to the file
        }
        catch (IOException error) { //catch io exceptions
            return "System encountered an error while trying to write msg " + str_msg + " to a file at " + this.str_filePath;
        }

        return ""; //no problems during execution
    }

    //this method returns whether or not the file has been opened for writing
    public boolean isOpen() {
        return this.b_isOpen;
    }
}
