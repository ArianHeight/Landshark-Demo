package main.Utility;

/*

A utility class used by main.Physics component and PhysicsEngine to do AABB collision detection

 */
public class HitboxAABB {
    //member vars
    private double left;
    private double right;
    private double top;
    private double bottom;

    //cstr
    public HitboxAABB(double left, double right, double top, double bot) {
        //init vars
        this.left = left;
        this.right = right;
        this.top = top;
        this.bottom = bot;
    }

    //accessors
    public double getLeft() { return this.left; }
    public double getRight() { return this.right; }
    public double getTop() { return this.top; }
    public double getBottom() { return this.bottom; }

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

    //alignment methods which assume HitboxAABB input is not null
    //aligns the left side of the hitbox
    public void alignLeftX(HitboxAABB hbRef) {
        double diff = hbRef.getLeft() - this.left;
        this.moveX(diff);
    }

    //aligns the right side of the hitbox
    public void alignRightX(HitboxAABB hbRef) {
        double diff = hbRef.getRight() - this.right;
        this.moveX(diff);
    }

    //aligns the center of the hitbox on the x axis
    public void alignCenterX(HitboxAABB hbRef) {
        double diff = (hbRef.getRight() + hbRef.getLeft()) / 2.0;
        diff -= ((this.right + this.left) / 2.0);
        this.moveX(diff);
    }

    //aligns the top side of the hitbox
    public void alignTopY(HitboxAABB hbRef) {
        double diff = hbRef.getTop() - this.top;
        this.moveY(diff);
    }

    //aligns the bottom side of the hitbox
    public void alignBottomY(HitboxAABB hbRef) {
        double diff = hbRef.getBottom() - this.bottom;
        this.moveY(diff);
    }

    //aligns the center of the hitbox on the y axis
    public void alignCenterY(HitboxAABB hbRef) {
        double diff = (hbRef.getTop() + hbRef.getBottom()) / 2.0;
        diff -= ((this.top + this.bottom) / 2.0);
        this.moveY(diff);
    }
}
