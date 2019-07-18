package Data;

import Data.Structure.PhysicsComponent;
import Data.Structure.VisualTextureComponent;
import Utility.HitboxAABB;

import java.awt.*;

/*

this class extends the GameObject class
will represent a map in game
a map will have
- at least one unmoveable(by physics engine) and not affected by gravity PhysicsComponent
- at least one VisualTextureComponent

 */
public class GameMap extends GameObject {
    //cstr
    public GameMap(PhysicsComponent pc_hitbox, VisualTextureComponent vtc_texture) {
        this.addComponent(pc_hitbox); //add physics
        this.addComponent(vtc_texture); //add texture
        this.setAllTags("Map");
    }

    //alt cstr
    public GameMap(HitboxAABB hb_hitbox, Image im_data) {
        this.addComponent(new PhysicsComponent(hb_hitbox, -1.0, false)); //add physics
        this.addComponent(new VisualTextureComponent(im_data, new Rectangle(0, 0, 1, 1))); //add textures
        this.setAllTags("Map");
    }
}
