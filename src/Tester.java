import IO.GameFileWriter;
import IO.IOEngine;

public class Tester {
    public static void run() {
        GameFileWriter ioe = new GameFileWriter("./Game/System/Logs/game_active.log");
        System.out.println(ioe.openFile(true));
        System.out.println(ioe.writeContentToFile("this is a log", true));
        System.out.println(ioe.writeContentToFile("this is another log", true));
        System.out.println(ioe.closeFile());
    }
}
