package main.game0logic;

import main.data.structure.HpComponent;
import main.data.structure.PhysicsComponent;
import main.data.structure.VisualAnimationComponent;

import java.awt.*;

/*
This class extends the Actor class
this will represent a single enemy on the field
 */
public class SpiderEnemy extends GameEnemy {
    private static VisualAnimationComponent standardAnimation = null;

    //cstr
    public SpiderEnemy(double vel) {
        super(new PhysicsComponent(16.0, 3.0, 1.5, 1.0, 1.0, false),
                standardAnimation.makeCpy(new Rectangle(), null, 1),
                new HpComponent(1),
                vel);
    }

    /*
    this method sets the default animations
     */
    public static void setDefaultAnimation(VisualAnimationComponent anim) {
        standardAnimation = anim;
    }
}
