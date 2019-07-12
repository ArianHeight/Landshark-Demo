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
        PhysicsComponent pc_one = new PhysicsComponent(new HitboxAABB(0.0, 1.0, 1.0, 0.0), 2.0);
        PhysicsComponent pc_two = new PhysicsComponent(new HitboxAABB(0.5, 1.5, 1.0, 0.0), -1.0);
        pe.doCollisionResponse(pc_one, pc_two);
        assertTrue(((HitboxAABB)pc_two.getData()).getLeft() == 0.5);
        assertTrue(((HitboxAABB)pc_one.getData()).getRight() == 0.5);
    }

    @Test
    public void test1b () {
        HitboxAABB hb_one = new HitboxAABB(0.0, 1.0, 1.0, 0.0);
        HitboxAABB hb_two = new HitboxAABB(0.5, 1.5, 1.0, 0.0);
        assertTrue(pe.doCollisionDetection(hb_one, hb_two));
        assertTrue(pe.doCollisionDetection(hb_two, hb_one));
    }

    @Test
    public void test2() { //test right
        PhysicsComponent pc_one = new PhysicsComponent(new HitboxAABB(0.0, 1.0, 1.0, 0.0), 2.0);
        PhysicsComponent pc_two = new PhysicsComponent(new HitboxAABB(-0.5, 0.5, 1.0, 0.0), -1.0);
        pe.doCollisionResponse(pc_one, pc_two);
        assertTrue(((HitboxAABB)pc_two.getData()).getRight() == 0.5);
        assertTrue(((HitboxAABB)pc_one.getData()).getLeft() == 0.5);
    }

    @Test
    public void test3() { //test up
        PhysicsComponent pc_one = new PhysicsComponent(new HitboxAABB(0.0, 1.0, 1.0, 0.0), 2.0);
        PhysicsComponent pc_two = new PhysicsComponent(new HitboxAABB(0.0, 1.0, 0.5, -0.5), -1.0);
        pe.doCollisionResponse(pc_one, pc_two);
        assertTrue(((HitboxAABB)pc_two.getData()).getTop() == 0.5);
        assertTrue(((HitboxAABB)pc_one.getData()).getBottom() == 0.5);
    }

    @Test
    public void test4() { //test down
        PhysicsComponent pc_one = new PhysicsComponent(new HitboxAABB(0.0, 1.0, 1.0, 0.0), 2.0);
        PhysicsComponent pc_two = new PhysicsComponent(new HitboxAABB(0.0, 1.0, 1.5, 0.5), -1.0);
        pe.doCollisionResponse(pc_one, pc_two);
        assertTrue(((HitboxAABB)pc_two.getData()).getBottom() == 0.5);
        assertTrue(((HitboxAABB)pc_one.getData()).getTop() == 0.5);
    }
}
