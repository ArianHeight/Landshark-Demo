package IO;


import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

/*

this class is responsible for writing to a single file
to be called from IOEngine class whenever a request for logging or saving comes in

 */
public class GameFileWriter {
    private String str_filePath;
    private File f;
    private FileWriter fw_writer;
    private BufferedWriter bw_writer;

    //cstr
    public GameFileWriter(String str_path) {
        this.str_filePath = str_path; //sets the filepath
    }

    /*
    TODO finish dis function pls Edmond
     */
    public boolean openFileTrunc() {
        /*try {

        }
        catch (IOException error) {

        }*/
        return true;
    }
}
