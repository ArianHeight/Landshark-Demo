package test;

import main.model.data.communication.GameScript;
import main.model.data.communication.LogRequest;
import main.model.data.communication.ProcessRequest;
import main.model.data.game0exceptions.NoDataException;
import main.model.io.GameFileReader;
import main.model.io.IoEngine;
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
        GameFileReader reader = new GameFileReader("./Game/system/Logs/game_active.log");
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
        subject.loadAnimation("./Game/Assets/Animations/walkingShark.anim", errors);
        assertTrue(errors.size() == 0);
        subject.loadAnimation("./Game/System/Test/some.anim", errors);
        subject.loadAnimation("./Game/System/Test/brokenDouble.anim", errors);
        subject.loadAnimation("./Game/System/Test/brokenPaths.anim", errors);
        assertTrue(errors.size() == 3);
    }

    @Test
    public void test3() { //tests texture loading even tho I don't use it?
        Vector<GameScript> errors = new Vector<GameScript>();
        subject.loadTexture("./Game/Assets/Textures/shark1.png", errors);
        assertTrue(errors.size() == 0);
        subject.loadTexture("./Game/System/Test/some.png", errors);
        assertTrue(errors.size() == 1);
    }
}
