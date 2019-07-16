package GameLogic;

import Data.Actor;
import Data.Structure.HPComponent;
import Data.Structure.PhysicsComponent;
import Data.Structure.VisualTextureComponent;
import Utility.HitboxAABB;

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

    //cstr TODO CHANGE THE TEXTURE!!
    public SpiderEnemy() {
        super(new PhysicsComponent(16.0, 6.0, 1.0, 1.0, 1.0, false),
                new VisualTextureComponent(new ImageIcon("./Game/Assets/Textures/spider1.png").getImage(), new Rectangle(0, 0, 64, 64)),
                new HPComponent(1));
        this.setAllTags("Enemy");
        ((VisualTextureComponent)this.v_c_memberComponents.get(DEFAULT_TEXTURE_INDEX)).setWorldPosRef((HitboxAABB) (this.v_c_memberComponents.get(WALKING_HITBOX_INDEX).getData()));
    }
}
