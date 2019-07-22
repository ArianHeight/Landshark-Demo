package test;

import main.Utility.HitboxAABB;
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

    @Test
    public void test5() { //test center alignment
        hb_subject.alignCenterX(new HitboxAABB(0.0, 2.0, 2.0, 0.0));
        hb_subject.alignCenterY(new HitboxAABB(0.0, 2.0, 2.0, 0.0));
        assertTrue(hb_subject.getLeft() == 0.5);
        assertTrue(hb_subject.getRight() == 1.5);
        assertTrue(hb_subject.getTop() == 1.5);
        assertTrue(hb_subject.getBottom() == 0.5);
    }

    @Test
    public void test6() { //test right and top alignment
        hb_subject.alignRightX(new HitboxAABB(0.0, 2.0, 2.0, 0.0));
        hb_subject.alignTopY(new HitboxAABB(0.0, 2.0, 2.0, 0.0));
        assertTrue(hb_subject.getLeft() == 1.0);
        assertTrue(hb_subject.getRight() == 2.0);
        assertTrue(hb_subject.getTop() == 2.0);
        assertTrue(hb_subject.getBottom() == 1.0);
    }

    @Test
    public void test7() { //test left and bottom alignment
        hb_subject.alignLeftX(new HitboxAABB(5.0, 7.0, 7.0, 5.0));
        hb_subject.alignBottomY(new HitboxAABB(5.0, 7.0, 7.0, 5.0));
        assertTrue(hb_subject.getLeft() == 5.0);
        assertTrue(hb_subject.getRight() == 6.0);
        assertTrue(hb_subject.getTop() == 6.0);
        assertTrue(hb_subject.getBottom() == 5.0);
    }
}
