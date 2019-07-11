package Data;

import Data.GameObject;
import java.util.Iterator;
import java.util.Vector;
import Data.Structure.*;

/*
This will represent any GameObject with an hitbox, hp, and textures/animation
extends GameObject
 */
public class Actor extends GameObject {
    //cstr TODO add a component for hp
    public Actor(PhysicsComponent pc_physics, VisualTextureComponent vtc_texture) {
        this.addComponent(pc_physics); //add physics
        this.addComponent(vtc_texture); //add texture
    }


}
