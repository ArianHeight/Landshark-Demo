package test;

import model.data.structure.VisualAnimationComponent;
import model.io.AnimationManager;
import model.utility.HitboxAabb;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.awt.*;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class AnimationMngrTest {
    AnimationManager subject;

    @BeforeEach
    public void before() {
        subject = new AnimationManager(null); //internal texture manager
    }

    @Test
    public void test1() { //test successful load
        Rectangle rect = new Rectangle(4, 5, 5, 5);
        HitboxAabb hb = new HitboxAabb(-1.0, 0.0, -1.0, -2.0);
        VisualAnimationComponent animation = subject.makeAnimation(
                "./Game/Assets/Animations/walkingShark.anim", rect, hb, 5);
        assertTrue(subject.getErrors().size() == 0);
        assertTrue(animation.getWorldPosRef() == hb);
        assertTrue(animation.getRenderPlane() == rect);
        assertTrue(animation.getLayer() == 5);
    }

    @Test
    public void test2() { //test wrong file loc
        VisualAnimationComponent animation = subject.makeAnimation("./Game/System/Test/some.anim",
                new Rectangle(4, 5, 5, 5),
                new HitboxAabb(-1.0, 0.0, -1.0, -2.0), 5);
        assertTrue(subject.getErrors().size() > 0);
    }

    @Test
    public void test3() { //tests incorrect format #1
        VisualAnimationComponent animation = subject.makeAnimation("./Game/System/Test/brokenDouble.anim",
                new Rectangle(4, 5, 5, 5),
                new HitboxAabb(-1.0, 0.0, -1.0, -2.0), 5);
        assertTrue(subject.getErrors().size() > 0);
    }

    @Test
    public void test4() { //test incorrect format #2
        VisualAnimationComponent animation = subject.makeAnimation("./Game/System/Test/brokenPaths.anim",
                new Rectangle(4, 5, 5, 5),
                new HitboxAabb(-1.0, 0.0, -1.0, -2.0), 5);
        assertTrue(subject.getErrors().size() > 0);
    }

    @Test
    public void test5() { //test already loaded
        Rectangle rect = new Rectangle(4, 5, 5, 5);
        HitboxAabb hb = new HitboxAabb(-1.0, 0.0, -1.0, -2.0);
        VisualAnimationComponent animation = subject.makeAnimation(
                "./Game/Assets/Animations/walkingShark.anim", rect, hb, 5);
        VisualAnimationComponent animationTwo = subject.makeAnimation(
                "./Game/Assets/Animations/walkingShark.anim", null, null, 4);
        assertTrue(animation.getData().equals(animationTwo.getData()));
        assertTrue(animationTwo.getLayer() == 4);
        assertTrue(animationTwo.getRenderPlane() == null);
        assertTrue(animationTwo.getWorldPosRef() == null);
    }
}
