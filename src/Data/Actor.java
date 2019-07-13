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
    //adds in order
    //physics
    //texture
    //hp
    public Actor(PhysicsComponent pc_physics, VisualTextureComponent vtc_texture, HPComponent hpc_health) {
        this.addComponent(pc_physics); //add physics
        this.addComponent(vtc_texture); //add texture
        this.addComponent(hpc_health); //add hp
    }

    //alt cstr
    //adds in order
    //physics
    //texture
    //hp
    public Actor(double d_topLeftX, double d_topLeftY, double d_width, double d_height, double d_mass, boolean b_grav, Image im_texture, int i_hp) {
        this.addComponent(new PhysicsComponent(d_topLeftX, d_topLeftY, d_width, d_height, d_mass, b_grav)); //physics
        this.addComponent(new VisualTextureComponent(im_texture, new Rectangle(0, 0, 1, 1))); //texture
        this.addComponent(new HPComponent(i_hp)); //hp
    }

    /*
    this method takes hp off of the HPComponent

    returns -1 if there is no hp
    returns 0 if the obj has 0 hp
    returns 1 if the obj has more than 0 hp
     */
    public int TakeDmg(int i_dmg) {
        //grabs the hp
        HPComponent hpc_health = this.findHPComponent();

        if (hpc_health == null) { //null guard
            return -1;
        }

        boolean returnVal = hpc_health.takeDmg(i_dmg); //take dmg
        if (returnVal) { //obj is alive
            return 1;
        }

        return 0; //obj is ded
    }

    /*
    this method takes an int input and sets the hp to that value
     */
    public void setHP(int i_newHP) {
        //grabs the hp
        HPComponent hpc_health = this.findHPComponent();

        if (hpc_health == null) { //null guard
            return; //no hp
        }

        hpc_health.setHP(i_newHP); //sets the hp
    }

    //private method to find the HPComponent quickly
    private HPComponent findHPComponent() {
        return (HPComponent) this.findFirstActiveComponentInObj(GameComponent.gcType.HITPOINT);
    }
}
