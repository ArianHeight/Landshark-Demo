package GameTest;

import Data.Structure.PhysicsComponent;
import Physics.PhysicsEngine;
import Utility.HitboxAABB;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class PhysicsEngineTest {
    private PhysicsEngine pe;
    
    @BeforeEach
    public void before() {
        pe = new PhysicsEngine();
    }

    @Test
    public void test1() { //test left
        PhysicsComponent pc_one = new PhysicsComponent(new HitboxAABB(0.0, 1.0, 1.0, 0.0), 2.0, true);
        PhysicsComponent pc_two = new PhysicsComponent(new HitboxAABB(0.5, 1.5, 1.0, 0.0), -1.0, true);
        pe.doCollisionResponse(pc_one, pc_two);
        assertTrue(((HitboxAABB)pc_two.getData()).getLeft() == 0.5);
        assertTrue(((HitboxAABB)pc_one.getData()).getRight() == 0.5);
    }

    @Test
    public void test1b () { //test AABB detection method
        HitboxAABB hb_one = new HitboxAABB(0.0, 1.0, 1.0, 0.0);
        HitboxAABB hb_two = new HitboxAABB(0.5, 1.5, 1.0, 0.0);
        assertTrue(pe.doCollisionDetection(hb_one, hb_two));
        assertTrue(pe.doCollisionDetection(hb_two, hb_one));
        hb_one = new HitboxAABB(0.0, 1.0, 1.0, 0.0);
        hb_two = new HitboxAABB(0.0, 1.0, 0.00001, -0.9999);
        assertTrue(pe.doCollisionDetection(hb_one, hb_two));
        assertTrue(pe.doCollisionDetection(hb_two, hb_one));
        hb_one = new HitboxAABB(0.0, 1.0, 1.0, 0.0);
        hb_two = new HitboxAABB(0.5, 1.5, 1.5, 0.5);
        assertTrue(pe.doCollisionDetection(hb_one, hb_two));
        assertTrue(pe.doCollisionDetection(hb_two, hb_one));
        hb_one = new HitboxAABB(0.0, 1.0, 1.0, 0.0);
        hb_two = new HitboxAABB(-1.01, -0.01, 1.0, 0.0);
        assertTrue(!pe.doCollisionDetection(hb_one, hb_two));
        assertTrue(!pe.doCollisionDetection(hb_two, hb_one));
    }

    @Test
    public void test2() { //test right
        PhysicsComponent pc_one = new PhysicsComponent(new HitboxAABB(0.0, 1.0, 1.0, 0.0), 2.0, true);
        PhysicsComponent pc_two = new PhysicsComponent(new HitboxAABB(-0.5, 0.5, 1.0, 0.0), -1.0, true);
        pe.doCollisionResponse(pc_one, pc_two);
        assertTrue(((HitboxAABB)pc_two.getData()).getRight() == 0.5);
        assertTrue(((HitboxAABB)pc_one.getData()).getLeft() == 0.5);
    }

    @Test
    public void test3() { //test up
        PhysicsComponent pc_one = new PhysicsComponent(new HitboxAABB(0.0, 1.0, 1.0, 0.0), 2.0, true);
        PhysicsComponent pc_two = new PhysicsComponent(new HitboxAABB(0.0, 1.0, 0.5, -0.5), -1.0, true);
        pe.doCollisionResponse(pc_one, pc_two);
        assertTrue(((HitboxAABB)pc_two.getData()).getTop() == 0.5);
        assertTrue(((HitboxAABB)pc_one.getData()).getBottom() == 0.5);
    }

    @Test
    public void test4() { //test down
        PhysicsComponent pc_one = new PhysicsComponent(new HitboxAABB(0.0, 1.0, 1.0, 0.0), 2.0, true);
        PhysicsComponent pc_two = new PhysicsComponent(new HitboxAABB(0.0, 1.0, 1.5, 0.5), -1.0, true);
        pe.doCollisionResponse(pc_one, pc_two);
        assertTrue(((HitboxAABB)pc_two.getData()).getBottom() == 0.5);
        assertTrue(((HitboxAABB)pc_one.getData()).getTop() == 0.5);
    }

    @Test
    public void test5() {
        PhysicsComponent pc_one = new PhysicsComponent(new HitboxAABB(0.0, 4.0, 1.0, 0.0), -2.0, true);
        PhysicsComponent pc_two = new PhysicsComponent(new HitboxAABB(1.0, 3.0, 1.5, 0.5), 1.0, true);
        pe.doCollisionResponse(pc_one, pc_two);
        assertTrue(((HitboxAABB)pc_two.getData()).getBottom() == 1.0);
        assertTrue(((HitboxAABB)pc_one.getData()).getTop() == 1.0);
    }

    @Test
    public void test6() { //testing gravity?
        PhysicsComponent one = new PhysicsComponent(new HitboxAABB(0.0, 1.0, 1.0, 0.0), 1.0, true);
        one.setVelX(1.0);
        one.setVelY(2.0);
        pe.doGravityCalculations(one, 2.0, 1.0, 1.0);
        assertTrue(one.getVelX() == 3.0);
        assertTrue(one.getVelY() == 3.0);

        one = new PhysicsComponent(new HitboxAABB(0.0, 1.0, 1.0, 0.0), -1.0, true);
        pe.doGravityCalculations(one, 2.0, 1.0, 1.0);
        assertTrue(one.getVelX() == 2.0);
        assertTrue(one.getVelY() == 1.0);

        one = new PhysicsComponent(new HitboxAABB(0.0, 1.0, 1.0, 0.0), 1.0, false);
        pe.doGravityCalculations(one, 2.0, 1.0, 1.0);
        assertTrue(one.getVelX() == 0.0);
        assertTrue(one.getVelY() == 0.0);
    }

    @Test
    public void test7() { //testing position??
        PhysicsComponent one = new PhysicsComponent(new HitboxAABB(0.0, 1.0, 1.0, 0.0), 1.0, true);
        one.setVelX(1.0);
        one.setVelY(2.0);
        pe.doPositionCalculations(one, 0.5);
        assertTrue(((HitboxAABB)one.getData()).getBottom() == 1.0);
        assertTrue(((HitboxAABB)one.getData()).getLeft() == 0.5);

        one = new PhysicsComponent(new HitboxAABB(0.0, 1.0, 1.0, 0.0), 1.0, false);
        one.setVelX(1.0);
        one.setVelY(2.0);
        pe.doPositionCalculations(one, 2.0);
        assertTrue(((HitboxAABB)one.getData()).getBottom() == 4.0);
        assertTrue(((HitboxAABB)one.getData()).getLeft() == 2.0);

        one = new PhysicsComponent(new HitboxAABB(0.0, 1.0, 1.0, 0.0), -1.0, false);
        one.setVelX(1.0);
        one.setVelY(2.0);
        pe.doPositionCalculations(one, 100.0);
        assertTrue(((HitboxAABB)one.getData()).getBottom() == 0.0);
        assertTrue(((HitboxAABB)one.getData()).getLeft() == 0.0);
    }
}
