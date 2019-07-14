package Physics;

import Data.Communication.CollisionDetectedRequest;
import Data.Communication.GameScript;
import Data.GameObject;
import Data.Structure.GameComponent;
import Data.Structure.PhysicsComponent;
import Utility.HitboxAABB;

import java.util.Iterator;
import java.util.Vector;

/*

Takes care of all physics collision detection and response
uses the PhysicsComponent class

 */
public class PhysicsEngine {
    //member vars
    private Vector<GameComponent> v_gc_physicsComponents;
    private double d_gravityX;
    private double d_gravityY;

    //cstr
    public PhysicsEngine() {
        //initializes member var
        this.v_gc_physicsComponents = new Vector<GameComponent>();
        this.d_gravityX = 0.0;
        this.d_gravityY = -24.0; //TODO add way to change
    }

    /*
    this method goes through all hitboxes in the game and check for collision between each other
    also runs gravity and position calculations on everything
    takes a scene GameObject as an input
    takes a Vector<GameScript> as an output for error requests
    takes a double as the time in seconds elapsed since last tick
     */
    public void doScenePhysics(GameObject go_scene, Vector<GameScript> v_gs_scripts, double d_timeElapsed) {
        //clears storage vector for storing new data
        this.v_gc_physicsComponents.clear();
        go_scene.compileComponentList(this.v_gc_physicsComponents, GameComponent.gcType.PHYSICS); //grabs all active physicscomponents

        //gravity and position
        Iterator<GameComponent> gc_it = this.v_gc_physicsComponents.iterator();
        PhysicsComponent pc_one;
        while (gc_it.hasNext()) {
            pc_one = (PhysicsComponent)gc_it.next();
            doGravityCalculations(pc_one, this.d_gravityX, this.d_gravityY, d_timeElapsed);
            doPositionCalculations(pc_one, d_timeElapsed);
        }

        //TODO maybe find a better way
        //collision detection
        int i_sizeOfVector = this.v_gc_physicsComponents.size();
        PhysicsComponent pc_two;
        for (int i = 0; i < i_sizeOfVector; i++) {
            pc_one = (PhysicsComponent) (this.v_gc_physicsComponents.get(i));
            for (int j = i + 1; j < i_sizeOfVector; j++) {
                pc_two = (PhysicsComponent) (this.v_gc_physicsComponents.get(j));
                if (doCollisionDetection((HitboxAABB) (pc_two.getData()), (HitboxAABB) (pc_one.getData()))) {
                    v_gs_scripts.add(new CollisionDetectedRequest(pc_one, pc_two));
                }
            }
        }
    }

    /*
    this method takes two hitboxes and checks if they are colliding
     */
    public static boolean doCollisionDetection(HitboxAABB hb_one, HitboxAABB hb_two) {
        //AABB collision detection
        if (hb_one.getLeft() <= hb_two.getRight() && hb_one.getRight() >= hb_two.getLeft()) {
            if (hb_one.getTop() >= hb_two.getBottom() && hb_one.getBottom() <= hb_two.getTop()) {
                return true;
            }
        }

        return false;
    }

    /*
    this method takes a PhysicsComponent, 2 doubles for acceleration, and a double for timeElapsed
    calculates gravity stuff
     */
    public static void doGravityCalculations(PhysicsComponent pc_subject, double d_accelX, double d_accelY, double d_timeElapsed) {
        if (pc_subject.affectedByGravity()) {
            //update gravity
            pc_subject.addVelX(d_accelX * d_timeElapsed);
            pc_subject.addVelY(d_accelY * d_timeElapsed);
        }
    }

    /*
    this method takes a PhysicsComponent, and a double for timeElapsed
    updates the position based on velocity of the obj
     */
    public static void doPositionCalculations(PhysicsComponent pc_subject, double d_timeElapsed) {
        if (pc_subject.canBeMoved()) {
            HitboxAABB hb = (HitboxAABB) pc_subject.getData();
            hb.moveX(pc_subject.getVelX() * d_timeElapsed);
            hb.moveY(pc_subject.getVelY() * d_timeElapsed);
        }
        else {
            pc_subject.setVelY(0.0);
            pc_subject.setVelX(0.0);
        }
    }

    /*
    this method takes two PhysicsComponents and does collision response on them
    takes two references to PhysicsComponents, so this method modifies them directly

    public so it can be called from the GameLogic package
     */
    public static void doCollisionResponse(PhysicsComponent pc_one, PhysicsComponent pc_two) {
        //testing whether the objs can be moved
        int i_test = 0;
        if (pc_one.canBeMoved()) {
            i_test ++;
        }
        if (pc_two.canBeMoved()) {
            i_test += 2;
        }

        double d_favorOnePercentage = 0.0; //how much movement will be enacted on pc_one(pc_one's weight / total weight)
        switch (i_test) {
            case 0: //neither can be moved
                return;
            case 1: //only pc_one can be moved
                d_favorOnePercentage = 1.0;
                break;
            case 2: //only pc_two can be moved
                d_favorOnePercentage = 0.0;;
                break;
            case 3:
            default: //both can be moved
                d_favorOnePercentage = pc_two.getMass() / (pc_one.getMass() + pc_two.getMass()); //potential divide by zero error
        }

        //all pc_two coords - pc_one coords
        HitboxAABB hb_one = (HitboxAABB) pc_one.getData();
        HitboxAABB hb_two = (HitboxAABB) pc_two.getData();
        double d_deltaRight = hb_two.getRight() - hb_one.getLeft(); //positive if overlapping
        double d_deltaLeft = hb_one.getRight() - hb_two.getLeft(); //pos if overlapping
        double d_deltaDown = hb_one.getTop() - hb_two.getBottom(); //pos if overlapping
        double d_deltaUp = hb_two.getTop() - hb_one.getBottom(); //pos if overlapping

        //stores the movements that hb_one will have to undergo
        double d_xMove = -d_deltaLeft;
        double d_yMove = -d_deltaDown;

        if (Math.abs(d_deltaRight) < Math.abs(d_xMove) ) {
            d_xMove = d_deltaRight;
        }
        if (Math.abs(d_deltaUp) < Math.abs(d_yMove)) {
            d_yMove = d_deltaUp;
        }

        //picks the direction with the least movement required and moves the hitboxes according to their masses
        if (Math.abs(d_xMove) < Math.abs(d_yMove)) {
            hb_one.moveX(d_xMove * d_favorOnePercentage);
            hb_two.moveX(-d_xMove * (1.0 - d_favorOnePercentage));
            if (Math.abs(d_xMove) == 0.0) {
                pc_one.setVelX(0.0);
                pc_two.setVelX(0.0);
            }
        }
        else {
            hb_one.moveY(d_yMove * d_favorOnePercentage);
            hb_two.moveY(-d_yMove * (1.0 - d_favorOnePercentage));
            if (Math.abs(d_yMove) == 0.0) {
                pc_one.setVelY(0.0);
                pc_two.setVelY(0.0);
            }
        }

        //flags for update in the two components
        pc_one.setUpdated();
        pc_two.setUpdated();
    }
}
