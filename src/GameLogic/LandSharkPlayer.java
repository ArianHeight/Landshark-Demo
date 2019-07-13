package GameLogic;

import Data.Player;
import Data.Structure.HPComponent;
import Data.Structure.PhysicsComponent;
import Data.Structure.VisualTextureComponent;

import javax.swing.*;
import java.awt.*;

public class LandSharkPlayer extends Player {
    private final static int WALKING_HITBOX_INDEX = 0;
    private final static int DEFAULT_TEXTURE_INDEX = 1;
    private final static int HP_INDEX = 2;
    private final static int CROUCHING_HITBOX_INDEX = 3;
    private final static double JUMP_VELOCITY = 3.0;

    private boolean b_crouchCalled; //whether or not crouch() has been called

    //cstr TODO some stuff w/ file loading and such
    public LandSharkPlayer() {
        super(new PhysicsComponent(0.0, 1.0, 2.0, 1.0, 1.0, true),
                new VisualTextureComponent(new ImageIcon("./Game/Assets/zombieKnight.png").getImage(), new Rectangle(0, 0, 1, 1)),
                new HPComponent(1));
        this.addComponent(new PhysicsComponent(0.0, 0.5, 2.0,0.5, 1.0, true)); //crouch hitbox
        this.v_c_memberComponents.get(CROUCHING_HITBOX_INDEX).deactivate();
        this.b_crouchCalled = false;
    }

    /*
    REQUIRES:A valid String
    MODIFIES:this
    EFFECT:Calls Player.inputResponse with parameter of str_input
           calls this.jump() if str_input equals "JumpPlayer"
           calls this.crouch if str_input equals "CrouchPlayer"
     */
    @Override
    public void inputResponse(String str_input) {
        super.inputResponse(str_input);

        if (str_input.equals("JumpPlayer")) {
            this.jump();
        }
        else if (str_input.equals("CrouchPlayer")) {
            //call crouch();
        }
    }

    private void jump() {
        ((PhysicsComponent)this.v_c_memberComponents.get(WALKING_HITBOX_INDEX)).setVelY(JUMP_VELOCITY);
    }
}
