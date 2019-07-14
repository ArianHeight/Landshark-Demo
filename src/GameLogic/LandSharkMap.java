package GameLogic;

import Data.GameMap;
import Data.Structure.PhysicsComponent;
import Data.Structure.VisualTextureComponent;
import Utility.HitboxAABB;

import java.awt.*;

/*
this class extends the GameMap object
the in game floor which the landshark will run on
 */
public class LandSharkMap extends GameMap{
    private final static int HITBOX_INDEX = 0;
    private final static int DEFAULT_TEXTURE_INDEX = 1;

    //cstr
    public LandSharkMap() {
        super(new PhysicsComponent(new HitboxAABB(-5.0, 35.0, 5.0, -5.0), -1.0, false),
                new VisualTextureComponent(null, new Rectangle(0, 0, 1, 1)));
        ((VisualTextureComponent)this.v_c_memberComponents.get(DEFAULT_TEXTURE_INDEX)).setWorldPosRef((HitboxAABB) (this.v_c_memberComponents.get(HITBOX_INDEX).getData()));
    }
}
