package test;

import main.utility.HitboxAABB;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class HitboxAABBTest {
    private HitboxAABB subject;

    @BeforeEach
    public void before() {
        subject = new HitboxAABB(0.0, 1.0, 1.0, 0.0);
    }

    @Test
    public void test1() { //test accessors
        assertTrue(subject.getLeft() == 0.0);
        assertTrue(subject.getRight() == 1.0);
        assertTrue(subject.getTop() == 1.0);
        assertTrue(subject.getBottom() == 0.0);
    }

    @Test
    public void test2() { //test moving on x
        subject.moveX(1.0);
        assertTrue(subject.getLeft() == 1.0);
        assertTrue(subject.getRight() == 2.0);
        assertTrue(subject.getTop() == 1.0);
        assertTrue(subject.getBottom() == 0.0);
        subject.moveX(-1.0);
        assertTrue(subject.getLeft() == 0.0);
        assertTrue(subject.getRight() == 1.0);
        assertTrue(subject.getTop() == 1.0);
        assertTrue(subject.getBottom() == 0.0);
    }

    @Test
    public void test3() { //test moving on y
        subject.moveY(1.0);
        assertTrue(subject.getLeft() == 0.0);
        assertTrue(subject.getRight() == 1.0);
        assertTrue(subject.getTop() == 2.0);
        assertTrue(subject.getBottom() == 1.0);
        subject.moveY(-1.0);
        assertTrue(subject.getLeft() == 0.0);
        assertTrue(subject.getRight() == 1.0);
        assertTrue(subject.getTop() == 1.0);
        assertTrue(subject.getBottom() == 0.0);
    }

    @Test
    public void test4() { //test setter
        subject.setNew(1.0, 2.0, 2.0, 1.0);
        assertTrue(subject.getLeft() == 1.0);
        assertTrue(subject.getRight() == 2.0);
        assertTrue(subject.getTop() == 2.0);
        assertTrue(subject.getBottom() == 1.0);
    }

    @Test
    public void test5() { //test center alignment
        subject.alignCenterX(new HitboxAABB(0.0, 2.0, 2.0, 0.0));
        subject.alignCenterY(new HitboxAABB(0.0, 2.0, 2.0, 0.0));
        assertTrue(subject.getLeft() == 0.5);
        assertTrue(subject.getRight() == 1.5);
        assertTrue(subject.getTop() == 1.5);
        assertTrue(subject.getBottom() == 0.5);
    }

    @Test
    public void test6() { //test right and top alignment
        subject.alignRightX(new HitboxAABB(0.0, 2.0, 2.0, 0.0));
        subject.alignTopY(new HitboxAABB(0.0, 2.0, 2.0, 0.0));
        assertTrue(subject.getLeft() == 1.0);
        assertTrue(subject.getRight() == 2.0);
        assertTrue(subject.getTop() == 2.0);
        assertTrue(subject.getBottom() == 1.0);
    }

    @Test
    public void test7() { //test left and bottom alignment
        subject.alignLeftX(new HitboxAABB(5.0, 7.0, 7.0, 5.0));
        subject.alignBottomY(new HitboxAABB(5.0, 7.0, 7.0, 5.0));
        assertTrue(subject.getLeft() == 5.0);
        assertTrue(subject.getRight() == 6.0);
        assertTrue(subject.getTop() == 6.0);
        assertTrue(subject.getBottom() == 5.0);
    }
}
