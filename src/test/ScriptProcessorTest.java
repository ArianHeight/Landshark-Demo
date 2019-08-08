package test;

import model.data.communication.GameScript;
import model.data.communication.ProcessRequest;
import model.system.ScriptProcessor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Vector;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class ScriptProcessorTest {
    ScriptProcessor subject;

    @BeforeEach
    public void before() {
        subject = new ScriptProcessor();
    }

    @Test
    public void test1() { //test script processing
        subject.addScriptToProcess(new ProcessRequest("start'"));
        subject.addScriptToProcess(new ProcessRequest("end"));
        subject.addScriptToProcess(new ProcessRequest("die"));

        Vector<GameScript> someVector = new Vector<GameScript>();
        subject.processScriptsInQueue(someVector);
        assertTrue(someVector.size() == 1);
        assertTrue(someVector.get(0).getCmd() == GameScript.END_PROGRAM);
    }

    @Test
    public void test2() { //tests error processing
        Vector<GameScript> someVector = new Vector<GameScript>();
        Vector<String> msg = new Vector<String>();
        msg.add("Hi");
        msg.add("say something I'm giving up on u");
        ScriptProcessor.processErrorData(someVector, msg);
        assertTrue(someVector.size() == 2);
        assertTrue(someVector.get(0).getData().startsWith("ERROR: "));
        assertTrue(someVector.get(0).getCmd() == GameScript.LOG_DATA);
        assertTrue(someVector.get(1).getData().startsWith("ERROR: "));
        assertTrue(someVector.get(1).getCmd() == GameScript.LOG_DATA);
    }

    @Test
    public void test3() { //tests process request processing
        Vector<GameScript> someVector = new Vector<GameScript>();
        Vector<String> msg = new Vector<String>();
        msg.add("/end");
        msg.add("/bye");
        ScriptProcessor.processStringData(someVector, msg);
        assertTrue(someVector.size() == 2);
        assertTrue(someVector.get(0).getData().equals("end"));
        assertTrue(someVector.get(0).getCmd() == GameScript.PROCESS_DATA);
        assertTrue(someVector.get(1).getData().equals("bye"));
        assertTrue(someVector.get(1).getCmd() == GameScript.PROCESS_DATA);
    }
}
