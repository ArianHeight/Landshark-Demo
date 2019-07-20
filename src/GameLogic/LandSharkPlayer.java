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
    private final static int CROUCHING_TEXTURE_INDEX = 4;
    private final static double JUMP_VELOCITY = 12.0;

    private boolean crouchCalled; //whether or not crouch() has been called
    private boolean crouching; //whether or not player is crouching
    private boolean thisFrameOnGround;
    private boolean touchingGround;

    private int activeHitbox;
    private int activeAnimation;

    //cstr TODO some stuff w/ file loading and such
    //TODO make an interface for ANIMATION AND TEXTURE to implement
    public LandSharkPlayer() {
        super(new PhysicsComponent(2.0, 3.0, 2.0, 1.0, 1.0, true),
                new VisualTextureComponent(new ImageIcon("./Game/Assets/Textures/shark1.png").getImage(), new Rectangle(0, 0, 64, 64), new HitboxAABB(0.0, 3.0, 1.5, 0.0), 0),
                new HPComponent(1));
        this.addComponent(new PhysicsComponent(2.0, 2.5, 2.0,0.5, 1.0, true)); //crouch hitbox
        this.memberComponents.get(CROUCHING_HITBOX_INDEX).deactivate();
        this.addComponent(new VisualTextureComponent(new ImageIcon("./Game/Assets/Textures/crouchShark1.png").getImage(),
                                                     new Rectangle(),
                                                     new HitboxAABB(0.0, 3.0, 0.75, 0.0), 0));
        this.memberComponents.get(CROUCHING_TEXTURE_INDEX).deactivate();
        this.setAllTags("Player");

        this.crouching = false;
        this.crouchCalled = false;
        this.thisFrameOnGround = false;
        this.touchingGround = false;

        this.activeHitbox = WALKING_HITBOX_INDEX;
        this.activeAnimation = DEFAULT_TEXTURE_INDEX;
    }

    /*
    REQUIRES:A valid String
    MODIFIES:this
    EFFECT:Calls Player.inputResponse with parameter of input
           calls this.jump() if input equals "JumpPlayer"
           calls this.crouch() if input equals "CrouchPlayer"
     */
    @Override
    public void inputResponse(String input) {
        super.inputResponse(input);

        if (input.equals("JumpPlayer")) {
            this.jump();
        }
        else if (input.equals("CrouchPlayer")) {
            this.crouch();
        }
    }

    //makes the player jump in the air if touching the ground
    private void jump() {
        if (this.touchingGround) {
            ((PhysicsComponent) this.memberComponents.get(WALKING_HITBOX_INDEX)).setVelY(JUMP_VELOCITY);
        }
    }

    //makes the player crouch if touching the ground
    private void crouch() {
        if (this.touchingGround) {
            this.memberComponents.get(DEFAULT_TEXTURE_INDEX).deactivate();
            this.memberComponents.get(WALKING_HITBOX_INDEX).deactivate();
            this.memberComponents.get(CROUCHING_HITBOX_INDEX).activate();
            this.memberComponents.get(CROUCHING_TEXTURE_INDEX).activate();

            this.activeHitbox = CROUCHING_HITBOX_INDEX;
            this.activeAnimation = CROUCHING_TEXTURE_INDEX;
            this.crouchCalled = true;
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
        HitboxAABB target = ((VisualTextureComponent)this.memberComponents.get(this.activeAnimation)).getWorldPosRef();
        HitboxAABB source = (HitboxAABB)this.memberComponents.get(this.activeHitbox).getData();
        target.alignBottomY(source);
        target.alignRightX(source);

        if (this.thisFrameOnGround) {
            this.touchingGround = true;
        }
        else {
            this.touchingGround = false;
        }
        this.thisFrameOnGround = false;

        if (this.crouchCalled) {
            this.crouching = true;
            this.crouchCalled = false; //resets crouch state
        }
        else if (this.crouching) {
            this.memberComponents.get(DEFAULT_TEXTURE_INDEX).activate();
            this.memberComponents.get(WALKING_HITBOX_INDEX).activate();
            this.memberComponents.get(CROUCHING_HITBOX_INDEX).deactivate();
            this.memberComponents.get(CROUCHING_TEXTURE_INDEX).deactivate();

            this.activeHitbox = WALKING_HITBOX_INDEX;
            this.activeAnimation = DEFAULT_TEXTURE_INDEX;
            this.crouching = false; //resets crouching
        }

        if (!this.findHPComponent().isAlive()) {
            this.setForDelete();
        }

        super.updateObj();
    }
}
