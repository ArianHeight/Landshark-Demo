package Utility;

/*

A utility class used by Physics component and PhysicsEngine to do AABB collision detection

 */
public class HitboxAABB {
    //member vars
    private double d_left;
    private double d_right;
    private double d_top;
    private double d_bottom;

    //cstr
    public HitboxAABB(double d_left, double d_right, double d_top, double d_bot) {
        //init vars
        this.d_left = d_left;
        this.d_right = d_right;
        this.d_top = d_top;
        this.d_bottom = d_bot;
    }

    //accessors
    public double getLeft() { return this.d_left; }
    public double getRight() { return this.d_right; }
    public double getTop() { return this.d_top; }
    public double getBottom() { return this.d_bottom; }

    //mutators

    //resets all vars to new data
    public void setNew(double d_left, double d_right, double d_top, double d_bot) {
        this.d_left = d_left;
        this.d_right = d_right;
        this.d_top = d_top;
        this.d_bottom = d_bot;
    }

    //moves the hitbox in the positive y direction by d_moveUp
    public void moveY(double d_moveUp) {
        this.d_top += d_moveUp;
        this.d_bottom += d_moveUp;
    }

    //moves the hitbox in the positive x direction by d_moveRight
    public void moveX(double d_moveRight) {
        this.d_left += d_moveRight;
        this.d_bottom += d_moveRight;
    }
}
