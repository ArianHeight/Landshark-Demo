package main.game0logic;

import main.data.Actor;
import main.data.structure.HPComponent;
import main.data.structure.PhysicsComponent;
import main.data.structure.VisualAnimationComponent;
import main.data.structure.VisualTextureComponent;
import main.utility.HitboxAABB;

import javax.swing.*;
import java.awt.*;

/*

this class extends the Actor class
will represent a single drone floating in the shark game
TODO make new super class for drones and spiders

 */
public class DroneEnemy extends Actor {
    private final static int WALKING_HITBOX_INDEX = 0;
    private final static int DEFAULT_TEXTURE_INDEX = 1;
    private final static int HP_INDEX = 2;
    private final static int DEFAULT_ANIMATION_INDEX = 3;

    //cstr TODO CHANGE THE TEXTURE!!
    public DroneEnemy(double vel) {
        super(new PhysicsComponent(16.0, 3.75, 1.5, 1.0, 1.0, false),
                new VisualTextureComponent(new ImageIcon("./Game/Assets/Textures/drone1.png").getImage(), new Rectangle(0, 0, 64, 64)),
                new HPComponent(1));
        this.setAllTags("Enemy");
        ((VisualTextureComponent)this.memberComponents.get(DEFAULT_TEXTURE_INDEX)).setWorldPosRef((HitboxAABB) (this.memberComponents.get(WALKING_HITBOX_INDEX).getData()));
        ((PhysicsComponent)this.memberComponents.get(WALKING_HITBOX_INDEX)).setVelX(vel);
        VisualAnimationComponent animation = new VisualAnimationComponent(0.1, new Rectangle(),
                (HitboxAABB) (this.memberComponents.get(WALKING_HITBOX_INDEX).getData()));
        animation.addSprite(new ImageIcon("./Game/Assets/Textures/drone1.png").getImage());
        animation.addSprite(new ImageIcon("./Game/Assets/Textures/drone2.png").getImage());
        animation.addSprite(new ImageIcon("./Game/Assets/Textures/drone3.png").getImage());
        animation.addSprite(new ImageIcon("./Game/Assets/Textures/drone4.png").getImage());
        this.addComponent(animation);
        this.memberComponents.get(DEFAULT_TEXTURE_INDEX).deactivate();
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
