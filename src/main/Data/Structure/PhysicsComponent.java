package main.Data.Structure;


import main.Utility.HitboxAABB;

/*
Extends GameComponent
represents a "physical" hitbox that the physics engine can work with
TODO add gravity stuff
 */
public class PhysicsComponent extends GameComponent{
    private HitboxAABB hb_hitbox;
    private boolean b_updated = false; //true if updated by PhysicsEngine
    private double d_mass = 1.0; //negative mass indicates that the object hitbox is not able to be moved
    private boolean b_gravity = true; //true if gravity-related calculations are done
    private double d_velX = 0.0;
    private double d_velY = 0.0;
    private double d_terminalVelX = 32.0; //abs terminal x velocity
    private double d_terminalVelY = 32.0; //abs terminal y vel

    //cstr
    public PhysicsComponent(double d_topLeftX, double d_topLeftY, double d_width, double d_height) {
        super(gcType.PHYSICS); //creates the physics type
        this.hb_hitbox = new HitboxAABB(d_topLeftX, d_topLeftX + d_width, d_topLeftY, d_topLeftY - d_height); //makes hitbox
    }

    //alt cstr
    public PhysicsComponent(double d_topLeftX, double d_topLeftY, double d_width, double d_height, double d_mass, boolean b_grav) {
        super(gcType.PHYSICS); //creates the physics type
        this.hb_hitbox = new HitboxAABB(d_topLeftX, d_topLeftX + d_width, d_topLeftY, d_topLeftY - d_height); //makes hitbox
        this.d_mass = d_mass;
        this.b_gravity = b_grav;
    }

    //alt cstr
    public PhysicsComponent(HitboxAABB hb_hitbox) {
        super(gcType.PHYSICS);
        this.hb_hitbox = hb_hitbox;
    }

    //alt cstr
    public PhysicsComponent(HitboxAABB hb_hitbox, double d_mass, boolean b_grav) {
        super(gcType.PHYSICS);
        this.hb_hitbox = hb_hitbox;
        this.d_mass = d_mass;
        this.b_gravity = b_grav;
    }

    /*
    REQUiRES:NONE
    MODIFIES:NONE
    EFFECT:Returns an Image obj that is carried by this component
     */
    @Override
    public Object getData() {
        return this.hb_hitbox;
    }

    //mutator to set b_updated to false
    public void resetUpdated() { this.b_updated = false; }

    //mutator for PhysicsEngine to set updated
    public void setUpdated() { this.b_updated = true; }

    //accessor for b_updated
    public boolean updatedByEngine() { return this.b_updated; }

    //returns whether or not the object can be moved
    public boolean canBeMoved() { return (this.d_mass >= 0); }

    //returns the mass of the object
    public double getMass() { return this.d_mass; }

    //returns whether the object is affected by gravity
    public boolean affectedByGravity() { return this.b_gravity; }

    //returns the velocities of the object
    public double getVelX() { return this.d_velX; }
    public double getVelY() { return this.d_velY; }

    //clamps velocity values to be between positive and negative of the abs terminal velocity
    private void clampVelX() { this.d_velX = Math.min(Math.max(-this.d_terminalVelX, this.d_velX), this.d_terminalVelX); }
    private void clampVelY() { this.d_velY = Math.min(Math.max(-this.d_terminalVelY, this.d_velY), this.d_terminalVelY); }

    //takes a double and sets the velocities to that value
    //also calls clampVelX() or clampVelY()
    public void setVelX(double d_val) {
        this.d_velX = d_val;
        this.clampVelX();
    }

    public void setVelY(double d_val) {
        this.d_velY = d_val;
        this.clampVelY();
    }

    //takes a double and adds that value to the velocities
    //also calls clampVelX() or clampVelY()
    public void addVelX(double d_val) {
        this.d_velX += d_val;
        this.clampVelX();
    }

    public void addVelY(double d_val) {
        this.d_velY += d_val;
        this.clampVelY();
    }
}
