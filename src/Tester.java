import javax.swing.*;
import java.awt.*;

public class Tester {
    public static void run() {
        if (new ImageIcon("./Game/Texture/text.png").getImageLoadStatus() == MediaTracker.ERRORED) {
            System.out.println("null");
        }
    }
}
