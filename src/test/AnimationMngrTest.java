package test;

import main.data.structure.VisualAnimationComponent;
import main.io.AnimationManager;
import main.utility.HitboxAABB;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.awt.*;

public class AnimationMngrTest {
    AnimationManager subject;

    @BeforeEach
    public void before() {
        subject = new AnimationManager(null); //internal texture manager
    }

    @Test
    public void test1() { //test successful load
        VisualAnimationComponent animation = subject.makeAnimation("./Game/Assets/Animations/walkingShark.anim",
                new Rectangle(4, 5, 5, 5),
                new HitboxAABB(-1.0, 0.0, -1.0, -2.0), 5);
        //assertTrue();
    }
}
