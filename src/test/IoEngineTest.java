package test;

import model.data.communication.GameScript;
import model.data.communication.LogRequest;
import model.data.communication.ProcessRequest;
import model.data.game0exceptions.NoDataException;
import model.io.GameFileReader;
import model.io.IoEngine;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Vector;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class IoEngineTest {
    IoEngine subject;

    @BeforeEach
    public void before() {
        subject = new IoEngine();
    }

    @Test
    public void test1() { //test logging and process request
        subject.processRequest(new LogRequest("hi"));
        subject.processRequest(new ProcessRequest("bye"));
        subject.logAll();
        GameFileReader reader = new GameFileReader("data/system/logs/game_active.log");
        reader.openFile();
        try {
            assertTrue(reader.readLineFromFile().equals("hi"));
        } catch (Exception error) {
            assertTrue(false);
        }
        try {
            reader.readLineFromFile();
            assertTrue(false);
        } catch (NoDataException errorOne) {
            assertTrue(true);
        } catch (Exception error) {
            assertTrue(false);
        }
    }

    @Test
    public void test2() { //test animation loading
        Vector<GameScript> errors = new Vector<GameScript>();
        subject.loadAnimation("data/assets/Animations/walkingShark.anim", errors);
        assertTrue(errors.size() == 0);
        subject.loadAnimation("data/system/test/some.anim", errors);
        subject.loadAnimation("data/system/test/brokenDouble.anim", errors);
        subject.loadAnimation("data/system/test/brokenPaths.anim", errors);
        assertTrue(errors.size() == 3);
    }

    @Test
    public void test3() { //tests texture loading even tho I don't use it?
        Vector<GameScript> errors = new Vector<GameScript>();
        subject.loadTexture("data/assets/textures/shark1.png", errors);
        assertTrue(errors.size() == 0);
        subject.loadTexture("data/system/test/some.png", errors);
        assertTrue(errors.size() == 1);
    }

    @Test
    public void test4() { //closed system test
        subject.closeSystem();
        assertTrue(true);
    }
}
