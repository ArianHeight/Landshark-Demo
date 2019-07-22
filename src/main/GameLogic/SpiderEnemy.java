package main.GameLogic;

import main.Data.Actor;
import main.Data.Structure.HPComponent;
import main.Data.Structure.PhysicsComponent;
import main.Data.Structure.VisualTextureComponent;
import main.Utility.HitboxAABB;

import javax.swing.*;
import java.awt.*;

/*
This class extends the Actor class
this will represent a single enemy on the field
 */
public class SpiderEnemy extends Actor {
    private final static int WALKING_HITBOX_INDEX = 0;
    private final static int DEFAULT_TEXTURE_INDEX = 1;
    private final static int HP_INDEX = 2;

    //cstr TODO TEST ANIMATIONS!!!
    public SpiderEnemy(double vel) {
        super(new PhysicsComponent(16.0, 3.75, 1.5, 1.0, 1.0, false),
                new VisualTextureComponent(new ImageIcon("./Game/Assets/Textures/drone1.png").getImage(), new Rectangle(0, 0, 64, 64)),
                new HPComponent(1));
        this.setAllTags("Enemy");
        ((VisualTextureComponent)this.memberComponents.get(DEFAULT_TEXTURE_INDEX)).setWorldPosRef((HitboxAABB) (this.memberComponents.get(WALKING_HITBOX_INDEX).getData()));
        ((PhysicsComponent)this.memberComponents.get(WALKING_HITBOX_INDEX)).setVelX(vel);
    }

    /*
   this method updates obj for deleting
    */
    @Override
    public void updateObj() {
        if (((HitboxAABB)this.memberComponents.get(WALKING_HITBOX_INDEX).getData()).getRight() <= -1.0) {
            this.setHP(0);
        }

        if (!this.findHPComponent().isAlive()) {
            this.setForDelete();
        }

        super.updateObj();
    }
}
