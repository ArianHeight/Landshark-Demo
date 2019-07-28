package test;

import main.data.communication.*;
import main.data.structure.PhysicsComponent;
import main.utility.HitboxAABB;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class GameScriptTest {
    GameScript subject;

    @BeforeEach
    public void before() {

    }


    public boolean testPiece(int in) {
        int n = 0;

        switch (in) {
            case GameScript.COUT_DATA:
            case GameScript.END_PROGRAM:
            case GameScript.GAME_EVENT:
            case GameScript.LOG_DATA:
            case GameScript.PROCESS_DATA:
                n++;
                break;
            default:
                //do nothing
        }

        return n == 1;
    }

    @Test
    public void test1() { //typing test
        assertTrue(testPiece(GameScript.COUT_DATA));
        assertTrue(testPiece(GameScript.END_PROGRAM));
        assertTrue(testPiece(GameScript.GAME_EVENT));
        assertTrue(testPiece(GameScript.LOG_DATA));
        assertTrue(testPiece(GameScript.PROCESS_DATA));
    }

    @Test
    public void test2() { //log test
        subject = new LogRequest("log");
        assertTrue(subject.getCmd() == GameScript.LOG_DATA);
        assertTrue(((String)subject.getData()).equals("log"));
        subject.setData("new");
        assertTrue(((String)subject.getData()).equals("new"));
    }

    @Test
    public void test3() { //process test
        subject = new ProcessRequest("request");
        assertTrue(subject.getCmd() == GameScript.PROCESS_DATA);
        assertTrue(((String)subject.getData()).equals("request"));
        subject.setData("new");
        assertTrue(((String)subject.getData()).equals("new"));
    }

    @Test
    public void test4() { //ConsolePrint test
        subject = new ConsolePrintRequest("request");
        assertTrue(subject.getCmd() == GameScript.COUT_DATA);
        assertTrue(((String)subject.getData()).equals("request"));
        subject.setData("new");
        assertTrue(((String)subject.getData()).equals("new"));
    }

    @Test
    public void test5() { //EndProgram test
        subject = new EndProgramRequest();
        assertTrue(subject.getCmd() == GameScript.END_PROGRAM);
        assertTrue(((String)subject.getData()).equals(""));
        subject.setData("new");
        assertTrue(((String)subject.getData()).equals(""));
    }

    @Test
    public void test6() { //GameEvent test
        subject = new GameEventRequest("spawnactor");
        assertTrue(subject.getCmd() == GameScript.GAME_EVENT);
        assertTrue(((String)subject.getData()).equals("spawnactor"));
        subject.setData("new");
        assertTrue(((String)subject.getData()).equals("new"));
    }

    @Test
    public void test7() { //CollisionDetected test
        PhysicsComponent one = new PhysicsComponent(new HitboxAABB(0.0, 4.0, 1.0, 0.0), -2.0, true);
        PhysicsComponent two = new PhysicsComponent(new HitboxAABB(1.0, 3.0, 1.5, 0.5), 1.0, true);

        subject = new CollisionDetectedRequest(one, two);
        assertTrue(subject.getCmd() == GameScript.GAME_EVENT);
        assertTrue(((CollisionDetectedRequest)subject).getOne() == one);
        assertTrue(((CollisionDetectedRequest)subject).getTwo() == two);
    }

    @Test
    public void test8() { //CollisionResponse test
        PhysicsComponent one = new PhysicsComponent(new HitboxAABB(0.0, 4.0, 1.0, 0.0), -2.0, true);
        PhysicsComponent two = new PhysicsComponent(new HitboxAABB(1.0, 3.0, 1.5, 0.5), 1.0, true);

        subject = new CollisionResponseRequest(one, two);
        assertTrue(subject.getCmd() == GameScript.COLLISION_RESPONSE);
        assertTrue(((CollisionResponseRequest)subject).getOne() == one);
        assertTrue(((CollisionResponseRequest)subject).getTwo() == two);
    }
}
