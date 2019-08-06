package test;

import main.model.data.communication.LogRequest;
import main.model.system.TimeProcessor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class TimeProcessorTest {
    TimeProcessor subject;

    @BeforeEach
    public void before() {
        subject = new TimeProcessor();
    }

    @Test
    public void test1() { //testing tagginng
        assertTrue(subject.tagMsg("meep").endsWith("meep"));
        assertTrue(subject.tagScript(new LogRequest("meep")).getData().endsWith("meep"));
    }

    @Test
    public void test2() { //testing tick
        subject.tick();
        assertTrue(subject.getTimeElapsed() >= 0.0);
        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
            assertTrue(false);
        }
        subject.tick();
        assertTrue(subject.getTimeElapsed() >= 1.0);
    }
}
