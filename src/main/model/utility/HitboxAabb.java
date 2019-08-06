package main.model.utility;

/*

A utility class used by main.model.physics component and PhysicsEngine to do AABB collision detection

 */
public class HitboxAabb {
    //member vars
    private double left;
    private double right;
    private double top;
    private double bottom;

    //cstr
    public HitboxAabb(double left, double right, double top, double bot) {
        //init vars
        this.left = left;
        this.right = right;
        this.top = top;
        this.bottom = bot;
    }

    //accessors
    //gets the x value of the left side of the hitbox
    public double getLeft() {
        return this.left;
    }

    //gets the x value of the right side of the hitbox
    public double getRight() {
        return this.right;
    }

    //gets the y value of the top side of the hitbox
    public double getTop() {
        return this.top;
    }

    //gets the y value of the bottom side of the hitbox
    public double getBottom() {
        return this.bottom;
    }

    //mutators

    //resets all vars to new data
    public void setNew(double left, double right, double top, double bot) {
        this.left = left;
        this.right = right;
        this.top = top;
        this.bottom = bot;
    }

    //moves the hitbox in the positive y direction by moveUp
    public void moveY(double moveUp) {
        this.top += moveUp;
        this.bottom += moveUp;
    }

    //moves the hitbox in the positive x direction by moveRight
    public void moveX(double moveRight) {
        this.left += moveRight;
        this.right += moveRight;
    }

    //alignment methods which assume HitboxAabb input is not null
    //aligns the left side of the hitbox
    public void alignLeftX(HitboxAabb hbRef) {
        double diff = hbRef.getLeft() - this.left;
        this.moveX(diff);
    }

    //aligns the right side of the hitbox
    public void alignRightX(HitboxAabb hbRef) {
        double diff = hbRef.getRight() - this.right;
        this.moveX(diff);
    }

    //aligns the center of the hitbox on the x axis
    public void alignCenterX(HitboxAabb hbRef) {
        double diff = (hbRef.getRight() + hbRef.getLeft()) / 2.0;
        diff -= ((this.right + this.left) / 2.0);
        this.moveX(diff);
    }

    //aligns the top side of the hitbox
    public void alignTopY(HitboxAabb hbRef) {
        double diff = hbRef.getTop() - this.top;
        this.moveY(diff);
    }

    //aligns the bottom side of the hitbox
    public void alignBottomY(HitboxAabb hbRef) {
        double diff = hbRef.getBottom() - this.bottom;
        this.moveY(diff);
    }

    //aligns the center of the hitbox on the y axis
    public void alignCenterY(HitboxAabb hbRef) {
        double diff = (hbRef.getTop() + hbRef.getBottom()) / 2.0;
        diff -= ((this.top + this.bottom) / 2.0);
        this.moveY(diff);
    }
}
