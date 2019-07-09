/*

Entry point for the program
Main will probably call Tester
Main will definitely call GameEngine to start the game execution

 */

import System.GameEngine;
import System.TimeProcessor;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferStrategy;

public class Main {
    //entry method, args is cmd line arguments
    public static void main(String args[]) {
        /*
        JFrame j = new JFrame("test");
        j.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        j.setMaximumSize(new Dimension(1280, 720));
        j.setMinimumSize(new Dimension(640, 360));
        j.setResizable(false);
        j.setSize(new Dimension(1280, 720));
        Image i1 = new ImageIcon("./Game/Assets/Textures/zombieKnight.png").getImage();
        j.setIconImage(i1);
        j.setVisible(true);
        j.createBufferStrategy(2);
        BufferStrategy bs = j.getBufferStrategy();
        bs.getDrawGraphics().drawImage(i1, 0, 0, 32, 32,null);
        bs.getDrawGraphics().dispose();
        bs.show();*/ // temp code yes

        TimeProcessor t = new TimeProcessor();
        System.out.println(t.tagMsg("I say hi"));

        GameEngine e = new GameEngine();
        e.startEngine("GameData/game.sav");

        e.run();

        e.endEngine();;
        System.out.println("Engine finished running, ending program..."); //placeholder line
    }
}
