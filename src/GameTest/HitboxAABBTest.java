package GameTest;

import Utility.HitboxAABB;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class HitboxAABBTest {
    private HitboxAABB hb_subject;

    @BeforeEach
    public void before() {
        hb_subject = new HitboxAABB(0.0, 1.0, 1.0, 0.0);
    }

    @Test
    public void test1() { //test accessors
        assertTrue(hb_subject.getLeft() == 0.0);
        assertTrue(hb_subject.getRight() == 1.0);
        assertTrue(hb_subject.getTop() == 1.0);
        assertTrue(hb_subject.getBottom() == 0.0);
    }

    @Test
    public void test2() { //test moving on x
        hb_subject.moveX(1.0);
        assertTrue(hb_subject.getLeft() == 1.0);
        assertTrue(hb_subject.getRight() == 2.0);
        assertTrue(hb_subject.getTop() == 1.0);
        assertTrue(hb_subject.getBottom() == 0.0);
        hb_subject.moveX(-1.0);
        assertTrue(hb_subject.getLeft() == 0.0);
        assertTrue(hb_subject.getRight() == 1.0);
        assertTrue(hb_subject.getTop() == 1.0);
        assertTrue(hb_subject.getBottom() == 0.0);
    }

    @Test
    public void test3() { //test moving on y
        hb_subject.moveY(1.0);
        assertTrue(hb_subject.getLeft() == 0.0);
        assertTrue(hb_subject.getRight() == 1.0);
        assertTrue(hb_subject.getTop() == 2.0);
        assertTrue(hb_subject.getBottom() == 1.0);
        hb_subject.moveY(-1.0);
        assertTrue(hb_subject.getLeft() == 0.0);
        assertTrue(hb_subject.getRight() == 1.0);
        assertTrue(hb_subject.getTop() == 1.0);
        assertTrue(hb_subject.getBottom() == 0.0);
    }

    @Test
    public void test4() { //test setter
        hb_subject.setNew(1.0, 2.0, 2.0, 1.0);
        assertTrue(hb_subject.getLeft() == 1.0);
        assertTrue(hb_subject.getRight() == 2.0);
        assertTrue(hb_subject.getTop() == 2.0);
        assertTrue(hb_subject.getBottom() == 1.0);
    }
}
