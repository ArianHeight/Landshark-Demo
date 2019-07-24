package test;

import main.data.communication.*;
import main.data.structure.PhysicsComponent;
import main.utility.HitboxAABB;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class GameScriptTest {
    GameScript gs_subject;

    @BeforeEach
    public void before() {

    }


    public boolean testPiece(int in) {
        int n = 0;

        switch(in) {
            case GameScript.COUT_DATA:
            case GameScript.END_PROGRAM:
            case GameScript.GAME_EVENT:
            case GameScript.LOG_DATA:
            case GameScript.PROCESS_DATA:
                n++;
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
        gs_subject = new LogRequest("log");
        assertTrue(gs_subject.getCmd() == GameScript.LOG_DATA);
        assertTrue(((String)gs_subject.getData()).equals("log"));
        gs_subject.setData("new");
        assertTrue(((String)gs_subject.getData()).equals("new"));
    }

    @Test
    public void test3() { //process test
        gs_subject = new ProcessRequest("request");
        assertTrue(gs_subject.getCmd() == GameScript.PROCESS_DATA);
        assertTrue(((String)gs_subject.getData()).equals("request"));
        gs_subject.setData("new");
        assertTrue(((String)gs_subject.getData()).equals("new"));
    }

    @Test
    public void test4() { //ConsolePrint test
        gs_subject = new ConsolePrintRequest("request");
        assertTrue(gs_subject.getCmd() == GameScript.COUT_DATA);
        assertTrue(((String)gs_subject.getData()).equals("request"));
        gs_subject.setData("new");
        assertTrue(((String)gs_subject.getData()).equals("new"));
    }

    @Test
    public void test5() { //EndProgram test
        gs_subject = new EndProgramRequest();
        assertTrue(gs_subject.getCmd() == GameScript.END_PROGRAM);
        assertTrue(((String)gs_subject.getData()).equals(""));
        gs_subject.setData("new");
        assertTrue(((String)gs_subject.getData()).equals(""));
    }

    @Test
    public void test6() { //GameEvent test
        gs_subject = new GameEventRequest("spawnactor");
        assertTrue(gs_subject.getCmd() == GameScript.GAME_EVENT);
        assertTrue(((String)gs_subject.getData()).equals("spawnactor"));
        gs_subject.setData("new");
        assertTrue(((String)gs_subject.getData()).equals("new"));
    }

    @Test
    public void test7() { //CollisionDetected test
        PhysicsComponent one = new PhysicsComponent(new HitboxAABB(0.0, 4.0, 1.0, 0.0), -2.0, true);
        PhysicsComponent two = new PhysicsComponent(new HitboxAABB(1.0, 3.0, 1.5, 0.5), 1.0, true);

        gs_subject = new CollisionDetectedRequest(one, two);
        assertTrue(gs_subject.getCmd() == GameScript.GAME_EVENT);
        assertTrue(((CollisionDetectedRequest)gs_subject).getOne() == one);
        assertTrue(((CollisionDetectedRequest)gs_subject).getTwo() == two);
    }

    @Test
    public void test8() { //CollisionResponse test
        PhysicsComponent one = new PhysicsComponent(new HitboxAABB(0.0, 4.0, 1.0, 0.0), -2.0, true);
        PhysicsComponent two = new PhysicsComponent(new HitboxAABB(1.0, 3.0, 1.5, 0.5), 1.0, true);

        gs_subject = new CollisionResponseRequest(one, two);
        assertTrue(gs_subject.getCmd() == GameScript.COLLISION_RESPONSE);
        assertTrue(((CollisionResponseRequest)gs_subject).getOne() == one);
        assertTrue(((CollisionResponseRequest)gs_subject).getTwo() == two);
    }
}
