package GameTest;

import Data.Communication.LogRequest;
import System.TimeProcessor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class TimeProcessorTest {
    TimeProcessor te_subject;

    @BeforeEach
    public void before() {
        te_subject = new TimeProcessor();
    }

    @Test
    public void test1() { //testing tagginng
        assertTrue(te_subject.tagMsg("meep").endsWith("meep"));
        assertTrue(te_subject.tagScript(new LogRequest("meep")).getData().endsWith("meep"));
    }

    @Test
    public void test2() {
        te_subject.tick();
        assertTrue(te_subject.getTimeElapsed() >= 0.0);
        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
            assertTrue(false);
        }
        te_subject.tick();
        assertTrue(te_subject.getTimeElapsed() >= 1.0);
    }
}
