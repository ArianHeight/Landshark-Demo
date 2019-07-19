package GameLogic;

import Data.Player;
import Data.Structure.HPComponent;
import Data.Structure.PhysicsComponent;
import Data.Structure.VisualTextureComponent;
import Utility.HitboxAABB;

import javax.swing.*;
import java.awt.*;

public class LandSharkPlayer extends Player {
    private final static int WALKING_HITBOX_INDEX = 0;
    private final static int DEFAULT_TEXTURE_INDEX = 1;
    private final static int HP_INDEX = 2;
    private final static int CROUCHING_HITBOX_INDEX = 3;
    private final static double JUMP_VELOCITY = 12.0;

    private boolean b_crouchCalled; //whether or not crouch() has been called
    private boolean thisFrameOnGround;
    private boolean touchingGround;

    //cstr TODO some stuff w/ file loading and such
    public LandSharkPlayer() {
        super(new PhysicsComponent(2.0, 3.0, 2.0, 1.0, 1.0, true),
                new VisualTextureComponent(new ImageIcon("./Game/Assets/Textures/shark1.png").getImage(), new Rectangle(0, 0, 64, 64), new HitboxAABB(0.0, 3.0, 1.5, 0.0), 0),
                new HPComponent(1));
        this.addComponent(new PhysicsComponent(0.0, 0.5, 2.0,0.5, 1.0, true)); //crouch hitbox
        this.memberComponents.get(CROUCHING_HITBOX_INDEX).deactivate();
        this.setAllTags("Player");
        this.b_crouchCalled = false;
        this.thisFrameOnGround = false;
        this.touchingGround = false;
    }

    /*
    REQUIRES:A valid String
    MODIFIES:this
    EFFECT:Calls Player.inputResponse with parameter of str_input
           calls this.jump() if str_input equals "JumpPlayer"
           calls this.crouch() if str_input equals "CrouchPlayer"
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

    //makes the player jump in the air if touching the ground
    private void jump() {
        if (this.touchingGround) {
            ((PhysicsComponent) this.memberComponents.get(WALKING_HITBOX_INDEX)).setVelY(JUMP_VELOCITY);
        }
    }

    //self-explaining
    public void setTouchingGroundTrue() {
        this.thisFrameOnGround = true;
    }

    /*
    this method updates the visual draw box to the actual hitbox

    also update obj for deleting
     */
    @Override
    public void updateObj() {
        //TODO temp code will change with crouch
        HitboxAABB hb_target = ((VisualTextureComponent)this.memberComponents.get(DEFAULT_TEXTURE_INDEX)).getWorldPosRef();
        HitboxAABB hb_source = (HitboxAABB)this.memberComponents.get(WALKING_HITBOX_INDEX).getData();

        hb_target.alignBottomY(hb_source);
        hb_target.alignRightX(hb_source);

        if (this.thisFrameOnGround) {
            this.touchingGround = true;
        }
        else {
            this.touchingGround = false;
        }
        this.thisFrameOnGround = false;

        if (!this.findHPComponent().isAlive()) {
            this.setForDelete();
        }

        super.updateObj();
    }
}
