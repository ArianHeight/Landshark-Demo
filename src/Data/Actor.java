package Data;

import Data.Structure.GameComponent;
import Data.Structure.HPComponent;
import Data.Structure.PhysicsComponent;
import Data.Structure.VisualTextureComponent;

import java.awt.*;

/*
This will represent any GameObject with an hitbox, hp, and textures/animation
extends GameObject
 */
public class Actor extends GameObject {
    //cstr
    public Actor(PhysicsComponent pc_physics, VisualTextureComponent vtc_texture, HPComponent hpc_health) {
        this.addComponent(pc_physics); //add physics
        this.addComponent(vtc_texture); //add texture
        this.addComponent(hpc_health); //add hp
    }

    //alt cstr
    public Actor(double d_topLeftX, double d_topLeftY, double d_width, double d_height, double d_mass, Image im_texture, int hp) {
        this.addComponent(new PhysicsComponent(d_topLeftX, d_topLeftY, d_width, d_height, d_mass)); //physics
        this.addComponent(new VisualTextureComponent(im_texture, new Rectangle(0, 0, 1, 1))); //texture
        this.addComponent(new HPComponent(hp)); //hp
    }

    /*
    this method takes hp off of the HPComponent

    returns -1 if there is no hp
    returns 0 if the obj has 0 hp
    returns 1 if the obj has more than 0 hp
     */
    public int TakeDmg(int i_dmg) {
        //grabs the hp
        HPComponent hpc_health = (HPComponent) this.findFirstActiveComponentInObj(GameComponent.gcType.HITPOINT);

        if (hpc_health == null) { //null guard
            return -1;
        }

        boolean returnVal = hpc_health.takeDmg(i_dmg); //take dmg
        if (returnVal) { //obj is alive
            return 1;
        }

        return 0; //obj is ded
    }
}
