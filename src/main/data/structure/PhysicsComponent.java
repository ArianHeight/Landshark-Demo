package main.data.structure;


import main.utility.HitboxAabb;

/*
Extends GameComponent
represents a "physical" hitbox that the physics engine can work with
TODO add gravity stuff
 */
public class PhysicsComponent extends GameComponent {
    private HitboxAabb hitBox;
    private boolean updated = false; //true if updated by PhysicsEngine
    private double mass = 1.0; //negative mass indicates that the object hitbox is not able to be moved
    private boolean gravity = true; //true if gravity-related calculations are done
    private double velX = 0.0;
    private double velY = 0.0;
    private double terminalVelX = 32.0; //abs terminal x velocity
    private double terminalVelY = 32.0; //abs terminal y vel

    //cstr
    public PhysicsComponent(double topLeftX, double topLeftY, double width, double height) {
        super(GcType.PHYSICS); //creates the physics type
        this.hitBox = new HitboxAabb(topLeftX, topLeftX + width, topLeftY, topLeftY - height); //makes hitbox
    }

    //alt cstr
    public PhysicsComponent(double topLeftX, double topLeftY, double width, double height, double mass, boolean grav) {
        super(GcType.PHYSICS); //creates the physics type
        this.hitBox = new HitboxAabb(topLeftX, topLeftX + width, topLeftY, topLeftY - height); //makes hitbox
        this.mass = mass;
        this.gravity = grav;
    }

    //alt cstr
    public PhysicsComponent(HitboxAabb hitBox) {
        super(GcType.PHYSICS);
        this.hitBox = hitBox;
    }

    //alt cstr
    public PhysicsComponent(HitboxAabb hitBox, double mass, boolean grav) {
        super(GcType.PHYSICS);
        this.hitBox = hitBox;
        this.mass = mass;
        this.gravity = grav;
    }

    /*
    REQUiRES:NONE
    MODIFIES:NONE
    EFFECT:Returns an Image obj that is carried by this component
     */
    @Override
    public Object getData() {
        return this.hitBox;
    }

    //mutator to set updated to false
    public void resetUpdated() {
        this.updated = false;
    }

    //mutator for PhysicsEngine to set updated
    public void setUpdated() {
        this.updated = true;
    }

    //accessor for updated
    public boolean updatedByEngine() {
        return this.updated;
    }

    //returns whether or not the object can be moved
    public boolean canBeMoved() {
        return (this.mass >= 0);
    }

    //returns the mass of the object
    public double getMass() {
        return this.mass;
    }

    //returns whether the object is affected by gravity
    public boolean affectedByGravity() {
        return this.gravity;
    }

    //returns the x velocity of the object
    public double getVelX() {
        return this.velX;
    }

    //returns the y velocity of the object
    public double getVelY() {
        return this.velY;
    }

    //clamps x velocity values to be between positive and negative of the abs terminal velocity
    private void clampVelX() {
        this.velX = Math.min(Math.max(-this.terminalVelX, this.velX), this.terminalVelX);
    }

    //clamps y velocity to be between positive and negative of the abs terminal vel
    private void clampVelY() {
        this.velY = Math.min(Math.max(-this.terminalVelY, this.velY), this.terminalVelY);
    }

    //takes a double and sets the velocities to that value
    //also calls clampVelX() or clampVelY()
    public void setVelX(double val) {
        this.velX = val;
        this.clampVelX();
    }

    public void setVelY(double val) {
        this.velY = val;
        this.clampVelY();
    }

    //takes a double and adds that value to the velocities
    //also calls clampVelX() or clampVelY()
    public void addVelX(double val) {
        this.velX += val;
        this.clampVelX();
    }

    public void addVelY(double val) {
        this.velY += val;
        this.clampVelY();
    }
}
