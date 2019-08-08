package model.game0logic;

import model.data.Player;
import model.data.structure.HpComponent;
import model.data.structure.PhysicsComponent;
import model.data.structure.VisualAnimationComponent;
import model.data.structure.VisualComponent;
import model.utility.HitboxAabb;

import java.awt.*;

public class LandSharkPlayer extends Player {
    private static final int WALKING_HITBOX_INDEX = 0;
    private static final int DEFAULT_TEXTURE_INDEX = 1;
    private static final int HP_INDEX = 2;
    private static final int CROUCHING_HITBOX_INDEX = 3;
    private static final int CROUCHING_TEXTURE_INDEX = 4;
    private static final double JUMP_VELOCITY = 12.0;
    private static VisualAnimationComponent standardAnimation = null;
    private static VisualAnimationComponent crouchAnimation = null;

    private boolean crouchCalled; //whether or not crouch() has been called
    private boolean crouching; //whether or not player is crouching
    private boolean thisFrameOnGround;
    private boolean touchingGround;

    private int activeHitbox;
    private int activeAnimation;

    //cstr
    public LandSharkPlayer() {
        super(new PhysicsComponent(2.0, 3.0, 2.0, 1.0, 1.0, true),
                standardAnimation.makeCpy(new Rectangle(), new HitboxAabb(0.0, 3.0, 1.5, 0.0), 0),
                new HpComponent(1));
        //crouch hitbox
        this.addComponent(new PhysicsComponent(2.0, 2.5, 2.0,0.5, 1.0, true));
        this.memberComponents.get(CROUCHING_HITBOX_INDEX).deactivate();
        this.addComponent(crouchAnimation.makeCpy(new Rectangle(),
                        new HitboxAabb(0.0, 3.0, 0.75, 0.0), 0));
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
        } else if (input.equals("CrouchPlayer")) {
            this.crouch();
        }
    }

    /*
    this method sets the default animations
     */
    public static void setDefaultAnimation(VisualAnimationComponent anim) {
        standardAnimation = anim;
    }

    /*
    this method sets the crouch animations
     */
    public static void setCrouchAnimation(VisualAnimationComponent anim) {
        crouchAnimation = anim;
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

    public boolean isTouchingGround() {
        return this.touchingGround;
    }

    public boolean isCrouching() {
        return this.crouching;
    }

    /*
    this method updates the visual draw box to the actual hitbox

    also update obj for deleting
     */
    @Override
    public void updateObj() {
        this.updateVisualHitbox();
        this.updateGroundStatus();
        this.updateCrouchStatus();

        if (!this.findHpComponent().isAlive()) {
            this.setForDelete();
        }

        super.updateObj();
    }

    //updates and aligns the visual rendering hitbox(first active) to be with the physical hitbox in worldspace
    private void updateVisualHitbox() {
        HitboxAabb target = ((VisualComponent)this.memberComponents.get(this.activeAnimation)).getWorldPosRef();
        HitboxAabb source = (HitboxAabb)this.memberComponents.get(this.activeHitbox).getData();
        target.alignBottomY(source);
        target.alignRightX(source);
    }

    //updates whether or not this player is touching the ground
    private void updateGroundStatus() {
        if (this.thisFrameOnGround) {
            this.touchingGround = true;
        } else {
            this.touchingGround = false;
        }
        this.thisFrameOnGround = false;
    }

    //Activate and de-activates the components depending on the crouch state
    //updates the crouch state as well
    private void updateCrouchStatus() {
        if (this.crouchCalled) {
            this.crouching = true;
            this.crouchCalled = false; //resets crouch state
        } else if (this.crouching) {
            this.memberComponents.get(DEFAULT_TEXTURE_INDEX).activate();
            this.memberComponents.get(WALKING_HITBOX_INDEX).activate();
            this.memberComponents.get(CROUCHING_HITBOX_INDEX).deactivate();
            this.memberComponents.get(CROUCHING_TEXTURE_INDEX).deactivate();

            this.activeHitbox = WALKING_HITBOX_INDEX;
            this.activeAnimation = DEFAULT_TEXTURE_INDEX;
            this.crouching = false; //resets crouching
        }
    }
}
