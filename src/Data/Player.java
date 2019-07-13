package Data;

import Data.Structure.HPComponent;
import Data.Structure.PhysicsComponent;
import Data.Structure.VisualTextureComponent;

import java.awt.*;

/*
This will be the class which represents a Player
extends the Actor class
implements the ControlInterface interface
 */
public class Player extends Actor implements ControlInterface {
    //cstr
    public Player(PhysicsComponent pc_physics, VisualTextureComponent vtc_texture, HPComponent hpc_health) {
        super(pc_physics, vtc_texture, hpc_health);
    }

    //alt cstr
    public Player(double d_topLeftX, double d_topLeftY, double d_width, double d_height, double d_mass, boolean b_grav, Image im_texture, int i_hp) {
        super(d_topLeftX, d_topLeftY, d_width, d_height, d_mass, b_grav, im_texture, i_hp);
    }

    /*
    REQUIRES:A valid String
    MODIFIES:this
    EFFECT:If the str_input equals "Die", then set player's hp to 0
     */
    @Override
    public void inputResponse(String str_input) {
        if (str_input.equals("KillPlayer")) {
            this.setHP(0); //kills the player
        }
    }
}
