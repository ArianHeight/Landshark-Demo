package main.model.game0logic;

/*
this class extends the Actor class

acts as a superclass for all in game enemies to extend
 */

import main.model.data.Actor;
import main.model.data.structure.HpComponent;
import main.model.data.structure.PhysicsComponent;
import main.model.data.structure.VisualComponent;
import main.model.utility.HitboxAabb;

public abstract class GameEnemy extends Actor {
    protected static final int WALKING_HITBOX_INDEX = 0;
    protected static final int DEFAULT_ANIMATION_INDEX = 1;
    protected static final int HP_INDEX = 2;

    //cstr
    protected GameEnemy(PhysicsComponent pc, VisualComponent vc, HpComponent hp, double vel) {
        super(pc, vc, hp);
        this.setAllTags("Enemy");
        ((VisualComponent)this.memberComponents.get(DEFAULT_ANIMATION_INDEX)).setWorldPosRef(
                (HitboxAabb) (this.memberComponents.get(WALKING_HITBOX_INDEX).getData()));
        ((PhysicsComponent)this.memberComponents.get(WALKING_HITBOX_INDEX)).setVelX(vel);
    }

    /*
   this method updates obj for deleting
    */
    @Override
    public void updateObj() {
        if (((HitboxAabb)this.memberComponents.get(WALKING_HITBOX_INDEX).getData()).getRight() <= -1.0) {
            this.setHP(0);
        }

        if (!this.findHpComponent().isAlive()) {
            this.setForDelete();
        }

        super.updateObj();
    }
}
