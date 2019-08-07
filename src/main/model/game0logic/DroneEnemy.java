package main.model.game0logic;

import main.model.data.structure.HpComponent;
import main.model.data.structure.PhysicsComponent;
import main.model.data.structure.VisualAnimationComponent;

import java.awt.*;

/*

this class extends the Actor class
will represent a single drone floating in the shark game

 */
public class DroneEnemy extends GameEnemy {
    private static VisualAnimationComponent standardAnimation = null;

    //cstr
    public DroneEnemy(double vel) {
        super(new PhysicsComponent(16.0, 3.75, 1.5, 1.0, 1.0, false),
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