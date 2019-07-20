package test;

import IO.GameFileReader;
import IO.GameFileWriter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class FileIOTest {
    GameFileReader reader;
    GameFileWriter writer;

    @BeforeEach
    public void before() {
        reader = new GameFileReader("./Game/System/Test/read.txt");
        writer = new GameFileWriter("./Game/System/Test/write.txt");
    }

    @Test
    public void test1() { //test opening
        assertTrue(reader.openFile().equals(""));
        assertTrue(reader.isOpen());
        assertTrue(reader.closeFile().equals(""));
        assertTrue(!reader.isOpen());
        assertTrue(reader.openFile().equals(""));
        assertTrue(reader.isOpen());
        assertTrue(reader.closeFile().equals(""));
        assertTrue(!reader.isOpen());
    }

    @Test
    public void test2() { //tests closing
        assertTrue(writer.openFile().equals(""));
        assertTrue(writer.isOpen());
        assertTrue(writer.closeFile().equals(""));
        assertTrue(!writer.isOpen());
        assertTrue(writer.openFile().equals(""));
        assertTrue(writer.isOpen());
        assertTrue(writer.closeFile().equals(""));
        assertTrue(!writer.isOpen());
    }

    @Test
    public void test3() { //tests loading and reading
        assertTrue(writer.openFile(true).equals(""));
        assertTrue(writer.writeContentToFile("Hello World.", false).equals(""));
        assertTrue(writer.closeFile().equals(""));
        reader = new GameFileReader("./Game/System/Test/write.txt");
        assertTrue(reader.openFile().equals(""));
        //System.out.println(reader.readLineFromFile());
        assertTrue(reader.readLineFromFile().equals("Hello World."));
        assertTrue(reader.closeFile().equals(""));
        assertTrue(writer.openFile(true).equals(""));
        assertTrue(writer.writeContentToFile("Hello", false).equals(""));
        assertTrue(writer.closeFile().equals(""));
        reader = new GameFileReader("./Game/System/Test/write.txt");
        assertTrue(reader.openFile().equals(""));
        assertTrue(reader.readLineFromFile().equals("Hello"));
        assertTrue(reader.closeFile().equals(""));
    }

    @Test
    public void test3b() { //tests loading and reading
        assertTrue(writer.openFile(true).equals(""));
        assertTrue(writer.writeContentToFile("Hello World.", true).equals(""));
        assertTrue(writer.closeFile().equals(""));
        reader = new GameFileReader("./Game/System/Test/write.txt");
        assertTrue(reader.openFile().equals(""));
        assertTrue(reader.readLineFromFile().equals("Hello World."));
        assertTrue(reader.closeFile().equals(""));
        assertTrue(writer.openFile(false).equals(""));
        assertTrue(writer.writeContentToFile("Hello", false).equals(""));
        assertTrue(writer.closeFile().equals(""));
        reader = new GameFileReader("./Game/System/Test/write.txt");
        assertTrue(reader.openFile().equals(""));
        assertTrue(reader.readLineFromFile().equals("Hello World."));
        assertTrue(reader.readLineFromFile().equals("Hello"));
        assertTrue(reader.closeFile().equals(""));
    }

    @Test
    public void test4() { //tests error
        reader = new GameFileReader("./BLAHBLAHBLAH/BLAH.txt");
        assertTrue(!reader.openFile().equals(""));
        assertTrue(!reader.isOpen());
        assertTrue(!reader.closeFile().equals(""));
    }

    @Test
    public void test5() { //tests error
        writer = new GameFileWriter("./BLAHBLAHBLAH/BLAH.txt");
        assertTrue(!writer.openFile().equals(""));
        assertTrue(!writer.isOpen());
        assertTrue(!writer.closeFile().equals(""));
    }

    @Test
    public void test6() { //test next int
        assertTrue(writer.openFile(true).equals(""));
        assertTrue(writer.writeContentToFile("2130", false).equals(""));
        assertTrue(writer.closeFile().equals(""));
        reader = new GameFileReader("./Game/System/Test/write.txt");
        assertTrue(reader.openFile().equals(""));
        //System.out.println(reader.readLineFromFile());
        assertTrue(reader.getNextInt() == 2130);
        assertTrue(reader.closeFile().equals(""));
    }
}
