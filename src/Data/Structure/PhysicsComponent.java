package Data.Structure;


import Utility.HitboxAABB;

/*
Extends GameComponent
represents a "physical" hitbox that the physics engine can work with
 */
public class PhysicsComponent extends GameComponent{
    private HitboxAABB hb_hitbox;
    private boolean b_updated; //true if updated by PhysicsEngine
    private double d_mass; //negative mass indicates that the object hitbox is not able to be moved

    //cstr
    public PhysicsComponent(double d_topLeftX, double d_topLeftY, double d_width, double d_height) {
        super(gcType.PHYSICS); //creates the physics type
        this.hb_hitbox = new HitboxAABB(d_topLeftX, d_topLeftX + d_width, d_topLeftY, d_topLeftY - d_height); //makes hitbox
        this.b_updated = false;
        this.d_mass = 1.0;
    }

    //alt cstr
    public PhysicsComponent(double d_topLeftX, double d_topLeftY, double d_width, double d_height, double d_mass) {
        super(gcType.PHYSICS); //creates the physics type
        this.hb_hitbox = new HitboxAABB(d_topLeftX, d_topLeftX + d_width, d_topLeftY, d_topLeftY - d_height); //makes hitbox
        this.b_updated = false;
        this.d_mass = d_mass;
    }

    //alt cstr
    public PhysicsComponent(HitboxAABB hb_hitbox) {
        super(gcType.PHYSICS);
        this.hb_hitbox = hb_hitbox;
        this.b_updated = false;
        this.d_mass = 1.0;
    }

    //alt cstr
    public PhysicsComponent(HitboxAABB hb_hitbox, double d_mass) {
        super(gcType.PHYSICS);
        this.hb_hitbox = hb_hitbox;
        this.b_updated = false;
        this.d_mass = d_mass;
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
    public boolean canBeMoved() { return (this.d_mass < 0); }

    //returns the mass of the object
    public double getMass() { return this.d_mass; }
}
