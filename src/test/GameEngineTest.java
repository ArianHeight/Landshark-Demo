package test;

import model.data.communication.EndProgramRequest;
import model.data.communication.LogRequest;
import model.system.GameEngine;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class GameEngineTest {
    private GameEngine subject;

    @BeforeEach
    public void before() {
        subject = new GameEngine();
    }

    @Test
    public void test1() { //just to make sure it runs
        subject.startEngine("");
        subject.addScript(new LogRequest("hi"));
        subject.processAllScripts();
        subject.addScript(new EndProgramRequest());
        subject.run();
        subject.endEngine();
        assertTrue(true);
    }
}
