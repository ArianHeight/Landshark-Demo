package test;

import model.data.GameObject;
import model.data.GameScene;
import model.data.communication.GameScript;
import model.data.communication.MouseLocRequest;
import model.data.structure.PhysicsComponent;
import model.data.structure.UiComponent;
import model.physics.PhysicsEngine;
import model.utility.HitboxAabb;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.awt.*;
import java.util.Vector;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class PhysicsEngineTest {
    private PhysicsEngine pe;
    
    @BeforeEach
    public void before() {
        pe = new PhysicsEngine();
    }

    @Test
    public void test1() { //test left
        PhysicsComponent one = new PhysicsComponent(new HitboxAabb(0.0, 1.0, 1.0, 0.0), 2.0, true);
        PhysicsComponent two = new PhysicsComponent(new HitboxAabb(0.5, 1.5, 1.0, 0.0), -1.0, true);
        pe.doCollisionResponse(one, two);
        assertTrue(((HitboxAabb)two.getData()).getLeft() == 0.5);
        assertTrue(((HitboxAabb)one.getData()).getRight() == 0.5);
    }

    @Test
    public void test1b() { //test AABB detection method
        HitboxAabb one = new HitboxAabb(0.0, 1.0, 1.0, 0.0);
        HitboxAabb two = new HitboxAabb(0.5, 1.5, 1.0, 0.0);
        assertTrue(pe.doCollisionDetection(one, two));
        assertTrue(pe.doCollisionDetection(two, one));
        one = new HitboxAabb(0.0, 1.0, 1.0, 0.0);
        two = new HitboxAabb(0.0, 1.0, 0.00001, -0.9999);
        assertTrue(pe.doCollisionDetection(one, two));
        assertTrue(pe.doCollisionDetection(two, one));
        one = new HitboxAabb(0.0, 1.0, 1.0, 0.0);
        two = new HitboxAabb(0.5, 1.5, 1.5, 0.5);
        assertTrue(pe.doCollisionDetection(one, two));
        assertTrue(pe.doCollisionDetection(two, one));
        one = new HitboxAabb(0.0, 1.0, 1.0, 0.0);
        two = new HitboxAabb(-1.01, -0.01, 1.0, 0.0);
        assertTrue(!pe.doCollisionDetection(one, two));
        assertTrue(!pe.doCollisionDetection(two, one));
    }

    @Test
    public void test1c() { //test collision against a point
        Rectangle rect = new Rectangle(0, 0, 10, 5);
        assertTrue(pe.doCollisionDetection(rect, new Point(5, 2)));
        assertTrue(!pe.doCollisionDetection(rect, new Point(-1, 2)));
        assertTrue(!pe.doCollisionDetection(rect, new Point(11, 2)));
        assertTrue(!pe.doCollisionDetection(rect, new Point(5, -1)));
        assertTrue(!pe.doCollisionDetection(rect, new Point(-1, 6)));
    }

    @Test
    public void test2() { //test right
        PhysicsComponent one = new PhysicsComponent(new HitboxAabb(0.0, 1.0, 1.0, 0.0), 2.0, true);
        PhysicsComponent two = new PhysicsComponent(new HitboxAabb(-0.5, 0.5, 1.0, 0.0), -1.0, true);
        pe.doCollisionResponse(one, two);
        assertTrue(((HitboxAabb)two.getData()).getRight() == 0.5);
        assertTrue(((HitboxAabb)one.getData()).getLeft() == 0.5);
    }

    @Test
    public void test3() { //test up
        PhysicsComponent one = new PhysicsComponent(new HitboxAabb(0.0, 1.0, 1.0, 0.0), 2.0, true);
        PhysicsComponent two = new PhysicsComponent(new HitboxAabb(0.0, 1.0, 0.5, -0.5), -1.0, true);
        pe.doCollisionResponse(one, two);
        assertTrue(((HitboxAabb)two.getData()).getTop() == 0.5);
        assertTrue(((HitboxAabb)one.getData()).getBottom() == 0.5);
    }

    @Test
    public void test4() { //test down
        PhysicsComponent one = new PhysicsComponent(new HitboxAabb(0.0, 1.0, 1.0, 0.0), 2.0, true);
        PhysicsComponent two = new PhysicsComponent(new HitboxAabb(0.0, 1.0, 1.5, 0.5), -1.0, true);
        pe.doCollisionResponse(one, two);
        assertTrue(((HitboxAabb)two.getData()).getBottom() == 0.5);
        assertTrue(((HitboxAabb)one.getData()).getTop() == 0.5);
    }

    @Test
    public void test5() { //testing collision response behaviour
        PhysicsComponent one = new PhysicsComponent(new HitboxAabb(0.0, 4.0, 1.0, 0.0), -2.0, true);
        PhysicsComponent two = new PhysicsComponent(new HitboxAabb(1.0, 3.0, 1.5, 0.5), 1.0, true);
        pe.doCollisionResponse(one, two);
        assertTrue(((HitboxAabb)two.getData()).getBottom() == 1.0);
        assertTrue(((HitboxAabb)one.getData()).getTop() == 1.0);
    }

    @Test
    public void test5b() { //testing collision response on two inmovable components
        HitboxAabb hbOne = new HitboxAabb(0.0, 1.0, 1.0, 0.0);
        HitboxAabb hbTwo = new HitboxAabb(0.5, 1.5, 1.0, 0.0);
        PhysicsComponent one = new PhysicsComponent(hbOne, -2.0, true);
        PhysicsComponent two = new PhysicsComponent(hbTwo, -1.0, true);
        pe.doCollisionResponse(one, two);
        assertTrue(hbOne.getLeft() == 0.0);
        assertTrue(hbTwo.getLeft() == 0.5);
    }

    @Test
    public void test5c() { //testing response on two moveable components
        HitboxAabb hbOne = new HitboxAabb(0.0, 1.0, 1.0, 0.0);
        HitboxAabb hbTwo = new HitboxAabb(0.5, 1.5, 1.0, 0.0);
        PhysicsComponent one = new PhysicsComponent(hbOne, 1.0, true);
        PhysicsComponent two = new PhysicsComponent(hbTwo, 1.0, true);
        pe.doCollisionResponse(one, two);
        assertTrue(hbOne.getLeft() == -0.25);
        assertTrue(hbTwo.getLeft() == 0.75);
    }

    @Test
    public void test5d() { //testing response on border case for resetting velocity
        HitboxAabb hbOne = new HitboxAabb(0.0, 1.0, 1.0, 0.0);
        HitboxAabb hbTwo = new HitboxAabb(1.0, 2.0, 1.0, 0.0);
        PhysicsComponent one = new PhysicsComponent(hbOne, -1.0, true);
        PhysicsComponent two = new PhysicsComponent(hbTwo, 1.0, true);
        two.setVelX(-1.0);
        pe.doCollisionResponse(one, two);
        assertTrue(hbTwo.getLeft() == 1.0);
        assertTrue(two.getVelX() == 0.0);
    }

    @Test
    public void test5e() { //testing response on border case for resetting velocity(y instead of x)
        HitboxAabb hbOne = new HitboxAabb(0.0, 1.0, 1.0, 0.0);
        HitboxAabb hbTwo = new HitboxAabb(0.0, 1.0, 2.0, 1.0);
        PhysicsComponent one = new PhysicsComponent(hbOne, -1.0, true);
        PhysicsComponent two = new PhysicsComponent(hbTwo, 1.0, true);
        two.setVelY(-1.0);
        pe.doCollisionResponse(one, two);
        assertTrue(hbTwo.getBottom() == 1.0);
        assertTrue(two.getVelY() == 0.0);
    }

    @Test
    public void test6() { //testing gravity?
        PhysicsComponent one = new PhysicsComponent(new HitboxAabb(0.0, 1.0, 1.0, 0.0), 1.0, true);
        one.setVelX(1.0);
        one.setVelY(2.0);
        pe.doGravityCalculations(one, 2.0, 1.0, 1.0);
        assertTrue(one.getVelX() == 3.0);
        assertTrue(one.getVelY() == 3.0);

        one = new PhysicsComponent(new HitboxAabb(0.0, 1.0, 1.0, 0.0), -1.0, true);
        pe.doGravityCalculations(one, 2.0, 1.0, 1.0);
        assertTrue(one.getVelX() == 2.0);
        assertTrue(one.getVelY() == 1.0);

        one = new PhysicsComponent(new HitboxAabb(0.0, 1.0, 1.0, 0.0), 1.0, false);
        pe.doGravityCalculations(one, 2.0, 1.0, 1.0);
        assertTrue(one.getVelX() == 0.0);
        assertTrue(one.getVelY() == 0.0);
    }

    @Test
    public void test7() { //testing position??
        PhysicsComponent one = new PhysicsComponent(new HitboxAabb(0.0, 1.0, 1.0, 0.0), 1.0, true);
        one.setVelX(1.0);
        one.setVelY(2.0);
        pe.doPositionCalculations(one, 0.5);
        assertTrue(((HitboxAabb)one.getData()).getBottom() == 1.0);
        assertTrue(((HitboxAabb)one.getData()).getLeft() == 0.5);

        one = new PhysicsComponent(new HitboxAabb(0.0, 1.0, 1.0, 0.0), 1.0, false);
        one.setVelX(1.0);
        one.setVelY(2.0);
        pe.doPositionCalculations(one, 2.0);
        assertTrue(((HitboxAabb)one.getData()).getBottom() == 4.0);
        assertTrue(((HitboxAabb)one.getData()).getLeft() == 2.0);

        one = new PhysicsComponent(new HitboxAabb(0.0, 1.0, 1.0, 0.0), -1.0, false);
        one.setVelX(1.0);
        one.setVelY(2.0);
        pe.doPositionCalculations(one, 100.0);
        assertTrue(((HitboxAabb)one.getData()).getBottom() == 0.0);
        assertTrue(((HitboxAabb)one.getData()).getLeft() == 0.0);
    }

    @Test
    public void test8() { //testing scene physics without collision
        PhysicsComponent one = new PhysicsComponent(new HitboxAabb(0.0, 1.0, 5.0, 4.0), 1.0, true);
        PhysicsComponent two = new PhysicsComponent(new HitboxAabb(0.0, 10.0, 1.0, 0.0), -1.0, false);
        one.setVelX(1.0);
        one.setVelY(0.0);
        pe.setGravityY(-1.0);
        GameObject scene = new GameScene();
        scene.addComponent(one);
        scene.addComponent(two);
        Vector<GameScript> output = new Vector<GameScript>();
        pe.doScenePhysics(scene, output, 1.0);
        //assertTrue(output.size() == 0);
        assertTrue(one.getVelX() == 1.0);
        assertTrue(one.getVelY() == -1.0);
        HitboxAabb hbOne = (HitboxAabb) one.getData();
        HitboxAabb hbTwo = (HitboxAabb) two.getData();
        assertTrue(hbOne.getLeft() == 1.0);
        assertTrue(hbOne.getTop() == 4.0);
        assertTrue(hbTwo.getTop() == 1.0);
        assertTrue(hbTwo.getLeft() == 0.0);
    }

    @Test
    public void test9() { //testing scene physics with collision
        PhysicsComponent one = new PhysicsComponent(new HitboxAabb(0.0, 1.0, 5.0, 4.0), 1.0, true);
        PhysicsComponent two = new PhysicsComponent(new HitboxAabb(0.0, 10.0, 1.0, 0.0), -1.0, false);
        one.setVelX(1.0);
        one.setVelY(-2.5);
        pe.setGravityY(-1.0);
        GameObject scene = new GameScene();
        scene.addComponent(one);
        scene.addComponent(two);
        Vector<GameScript> output = new Vector<GameScript>();
        pe.doScenePhysics(scene, output, 1.0);
        assertTrue(output.size() == 1);
        assertTrue(one.getVelX() == 1.0);
        assertTrue(one.getVelY() == -3.5);
        test9seondPart((HitboxAabb) one.getData(), (HitboxAabb) two.getData());
    }

    //follow up method for test9
    private void test9seondPart(HitboxAabb hbOne, HitboxAabb hbTwo) {
        assertTrue(hbOne.getLeft() == 1.0);
        assertTrue(hbOne.getTop() == 1.5);
        assertTrue(hbTwo.getTop() == 1.0);
        assertTrue(hbTwo.getLeft() == 0.0);
    }

    @Test
    public void test10() { //mouse click detection testing
        UiComponent uiOne = new UiComponent(new Rectangle(0, 0, 10, 5));
        UiComponent uiTwo = new UiComponent(new Rectangle(100, 100, 110, 105));
        GameObject scene = new GameScene();
        scene.addComponent(uiOne);
        scene.addComponent(uiTwo);

        pe.addMouesLocToQueue(new MouseLocRequest("click", new Point(5, 2)));
        pe.addMouesLocToQueue(new MouseLocRequest("click", new Point(20, 30)));
        pe.addMouesLocToQueue(new MouseLocRequest("hold", new Point(5, 3)));

        pe.updateMouseCollisions(scene);
        assertTrue(uiOne.getPressedState());
        assertTrue(!uiTwo.getPressedState());
    }
}
