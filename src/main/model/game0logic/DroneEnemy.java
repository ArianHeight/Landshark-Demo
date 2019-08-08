package model.game0logic;

import model.data.structure.HpComponent;
import model.data.structure.PhysicsComponent;
import model.data.structure.VisualAnimationComponent;
import model.utility.HitboxAabb;

import java.awt.*;

/*

this class extends the Actor class
will represent a single drone floating in the shark game

 */
public class DroneEnemy extends GameEnemy {
    private static VisualAnimationComponent standardAnimation = null;

    //cstr
    public DroneEnemy(double vel, boolean top) {
        super(new PhysicsComponent(16.0, 3.75, 1.5, 1.0, 1.0, false),
                standardAnimation.makeCpy(new Rectangle(), null, 1),
                new HpComponent(1),
                vel);
        if (top) {
            ((HitboxAabb)this.memberComponents.get(WALKING_HITBOX_INDEX).getData()).moveY(0.75);
        }
    }

    /*
    this method sets the default animations
     */
    public static void setDefaultAnimation(VisualAnimationComponent anim) {
        standardAnimation = anim;
    }
}
