package test;

import model.data.game0exceptions.FileNotOpenException;
import model.data.game0exceptions.NoDataException;
import model.io.GameFileReader;
import model.io.GameFileWriter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class FileIoTest {
    GameFileReader reader;
    GameFileWriter writer;

    @BeforeEach
    public void before() {
        reader = new GameFileReader("./Game/system/Test/read.txt");
        writer = new GameFileWriter("./Game/system/Test/write.txt");
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
        reader = new GameFileReader("./Game/system/Test/write.txt");
        assertTrue(reader.openFile().equals(""));
        //system.out.println(reader.readLineFromFile());
        try {
            assertTrue(reader.readLineFromFile().equals("Hello World."));
        } catch (Exception e) {
            assertTrue(false);
        }
        assertTrue(reader.closeFile().equals(""));
        this.test3secondPart();
    }

    private void test3secondPart() {
        assertTrue(writer.openFile(true).equals(""));
        assertTrue(writer.writeContentToFile("Hello", false).equals(""));
        assertTrue(writer.closeFile().equals(""));
        reader = new GameFileReader("./Game/system/Test/write.txt");
        assertTrue(reader.openFile().equals(""));
        try {
            assertTrue(reader.readLineFromFile().equals("Hello"));
        } catch (Exception e) {
            assertTrue(false);
        }
        assertTrue(reader.closeFile().equals(""));
    }

    @Test
    public void test3b() { //tests loading and reading
        assertTrue(writer.openFile(true).equals(""));
        assertTrue(writer.writeContentToFile("Hello World.", true).equals(""));
        assertTrue(writer.closeFile().equals(""));
        reader = new GameFileReader("./Game/system/Test/write.txt");
        assertTrue(reader.openFile().equals(""));
        try {
            assertTrue(reader.readLineFromFile().equals("Hello World."));
        } catch (Exception e) {
            assertTrue(false);
        }
        assertTrue(reader.closeFile().equals(""));
        this.test3bSecondPart();
    }

    private void test3bSecondPart() {
        assertTrue(writer.openFile(false).equals(""));
        assertTrue(writer.writeContentToFile("Hello", false).equals(""));
        assertTrue(writer.closeFile().equals(""));
        reader = new GameFileReader("./Game/system/Test/write.txt");
        assertTrue(reader.openFile().equals(""));
        try {
            assertTrue(reader.readLineFromFile().equals("Hello World."));
            assertTrue(reader.readLineFromFile().equals("Hello"));
            reader.readLineFromFile();
            assertTrue(false);
        } catch (NoDataException e) {
            assertTrue(true);
        } catch (Exception e1) {
            assertTrue(false);
        }
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
        reader = new GameFileReader("./Game/system/Test/write.txt");
        assertTrue(reader.openFile().equals(""));
        //system.out.println(reader.readLineFromFile());
        try {
            assertTrue(reader.getNextInt() == 2130);
        } catch (Exception e) {
            assertTrue(false);
        }
        this.test6secondPart();
    }

    private void test6secondPart() {
        try {
            reader.getNextInt();
            assertTrue(false);
        } catch (NoDataException e) {
            assertTrue(true);
        } catch (Exception e1) {
            assertTrue(false);
        }
        assertTrue(reader.closeFile().equals(""));
        try {
            reader.readLineFromFile();
            assertTrue(false);
        } catch (FileNotOpenException e) {
            assertTrue(true);
        } catch (Exception e1) {
            assertTrue(false);
        }
    }

    @Test
    public void test6b() { //next int file guard
        try {
            reader.getNextInt();
            assertTrue(false);
        } catch (FileNotOpenException e) {
            assertTrue(true);
        } catch (Exception e1) {
            assertTrue(false);
        }
    }

    @Test
    public void test7() { //test next double
        assertTrue(writer.openFile(true).equals(""));
        assertTrue(writer.writeContentToFile("2130.00", false).equals(""));
        assertTrue(writer.closeFile().equals(""));
        reader = new GameFileReader("./Game/system/Test/write.txt");
        assertTrue(reader.openFile().equals(""));
        //system.out.println(reader.readLineFromFile());
        try {
            assertTrue(reader.getNextDouble() == 2130.0);
        } catch (Exception e) {
            assertTrue(false);
        }
        this.test7secondPart();
    }

    private void test7secondPart() {
        try {
            reader.getNextDouble();
            assertTrue(false);
        } catch (NoDataException e) {
            assertTrue(true);
        } catch (Exception e1) {
            assertTrue(false);
        }
        assertTrue(reader.closeFile().equals(""));
        try {
            reader.readLineFromFile();
        } catch (FileNotOpenException e) {
            assertTrue(true);
        } catch (Exception e1) {
            assertTrue(false);
        }
    }

    @Test
    public void test7b() { //next int file guard
        try {
            reader.getNextDouble();
            assertTrue(false);
        } catch (FileNotOpenException e) {
            assertTrue(true);
        } catch (Exception e1) {
            assertTrue(false);
        }
    }

    @Test
    public void test8() { //filePath check and opening already opened file
        assertTrue(reader.openFile().equals(""));
        assertTrue(!reader.openFile().equals(""));
        assertTrue(writer.openFile().equals(""));
        assertTrue(!writer.openFile().equals(""));

        assertTrue(reader.getFilePath().equals("./Game/system/Test/read.txt"));
        assertTrue(writer.getFilePath().equals("./Game/system/Test/write.txt"));
    }

    @Test
    public void test9() { //testing file writing with closed file
        assertTrue(!writer.writeContentToFile("blue bird", false).equals(""));
    }
}
