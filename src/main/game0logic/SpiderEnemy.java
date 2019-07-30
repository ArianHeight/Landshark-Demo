package main.game0logic;

import main.data.Actor;
import main.data.structure.HPComponent;
import main.data.structure.PhysicsComponent;
import main.data.structure.VisualAnimationComponent;
import main.data.structure.VisualComponent;
import main.utility.HitboxAabb;

import java.awt.*;

/*
This class extends the Actor class
this will represent a single enemy on the field
 */
public class SpiderEnemy extends Actor {
    private static final int WALKING_HITBOX_INDEX = 0;
    private static final int DEFAULT_ANIMATION_INDEX = 1;
    private static final int HP_INDEX = 2;
    private static VisualAnimationComponent standardAnimation = null;

    //cstr TODO TEST ANIMATIONS!!!
    public SpiderEnemy(double vel) {
        super(new PhysicsComponent(16.0, 3.0, 1.5, 1.0, 1.0, false),
                standardAnimation.makeCpy(new Rectangle(), null, 1),
                new HPComponent(1));
        this.setAllTags("Enemy");
        ((VisualComponent)this.memberComponents.get(DEFAULT_ANIMATION_INDEX)).setWorldPosRef(
                (HitboxAabb) (this.memberComponents.get(WALKING_HITBOX_INDEX).getData()));
        ((PhysicsComponent)this.memberComponents.get(WALKING_HITBOX_INDEX)).setVelX(vel);
    }

    /*
    this method sets the default animations
     */
    public static void setDefaultAnimation(VisualAnimationComponent anim) {
        standardAnimation = anim;
    }

    /*
   this method updates obj for deleting
    */
    @Override
    public void updateObj() {
        if (((HitboxAabb)this.memberComponents.get(WALKING_HITBOX_INDEX).getData()).getRight() <= -1.0) {
            this.setHP(0);
        }

        if (!this.findHPComponent().isAlive()) {
            this.setForDelete();
        }

        super.updateObj();
    }
}
