package main.physics;

import main.data.communication.CollisionDetectedRequest;
import main.data.communication.GameScript;
import main.data.GameObject;
import main.data.structure.GameComponent;
import main.data.structure.PhysicsComponent;
import main.utility.HitboxAabb;

import java.util.Iterator;
import java.util.Vector;

/*

Takes care of all physics collision detection and response
uses the PhysicsComponent class

 */
public class PhysicsEngine {
    //member vars
    private Vector<GameComponent> physicsComponents;
    private double gravityX;
    private double gravityY;

    //cstr
    public PhysicsEngine() {
        //initializes member var
        this.physicsComponents = new Vector<GameComponent>();
        this.gravityX = 0.0;
        this.gravityY = -24.0; //TODO add way to change
    }

    /*
    this method goes through all hitboxes in the game and check for collision between each other
    also runs gravity and position calculations on everything
    takes a scene GameObject as an input
    takes a Vector<GameScript> as an output for error requests
    takes a double as the time in seconds elapsed since last tick
     */
    public void doScenePhysics(GameObject scene, Vector<GameScript> scripts, double timeElapsed) {
        //clears storage vector for storing new data
        this.physicsComponents.clear();
        //grabs all active physicscomponents
        scene.compileComponentList(this.physicsComponents, GameComponent.GcType.PHYSICS);

        //gravity and position
        Iterator<GameComponent> gcIt = this.physicsComponents.iterator();
        PhysicsComponent pcOne;
        while (gcIt.hasNext()) {
            pcOne = (PhysicsComponent)gcIt.next();
            doGravityCalculations(pcOne, this.gravityX, this.gravityY, timeElapsed);
            doPositionCalculations(pcOne, timeElapsed);
        }

        this.doAllCollisionDetection(scripts);
    }

    //private method to do collision detection with each obj against every other obj in this.physicsComponent
    private void doAllCollisionDetection(Vector<GameScript> scripts) {
        //collision detection
        int sizeOfVector = this.physicsComponents.size();
        PhysicsComponent pcOne;
        PhysicsComponent pcTwo;

        for (int i = 0; i < sizeOfVector; i++) {
            pcOne = (PhysicsComponent) (this.physicsComponents.get(i));

            for (int j = i + 1; j < sizeOfVector; j++) {
                pcTwo = (PhysicsComponent) (this.physicsComponents.get(j));
                if (doCollisionDetection((HitboxAabb) (pcTwo.getData()), (HitboxAabb) (pcOne.getData()))) {
                    scripts.add(new CollisionDetectedRequest(pcOne, pcTwo));
                }
            }
        }
    }

    /*
    this method takes two hitboxes and checks if they are colliding
     */
    public static boolean doCollisionDetection(HitboxAabb hbOne, HitboxAabb hbTwo) {
        //AABB collision detection
        if (hbOne.getLeft() <= hbTwo.getRight() && hbOne.getRight() >= hbTwo.getLeft()) {
            if (hbOne.getTop() >= hbTwo.getBottom() && hbOne.getBottom() <= hbTwo.getTop()) {
                return true;
            }
        }

        return false;
    }

    /*
    this method takes a PhysicsComponent, 2 doubles for acceleration, and a double for timeElapsed
    calculates gravity stuff
     */
    public static void doGravityCalculations(PhysicsComponent subject, double accelX,
                                             double accelY, double timeElapsed) {
        if (subject.affectedByGravity()) {
            //update gravity
            subject.addVelX(accelX * timeElapsed);
            subject.addVelY(accelY * timeElapsed);
        }
    }

    /*
    this method takes a PhysicsComponent, and a double for timeElapsed
    updates the position based on velocity of the obj
     */
    public static void doPositionCalculations(PhysicsComponent subject, double timeElapsed) {
        if (subject.canBeMoved()) {
            HitboxAabb hb = (HitboxAabb) subject.getData();
            hb.moveX(subject.getVelX() * timeElapsed);
            hb.moveY(subject.getVelY() * timeElapsed);
        } else {
            subject.setVelY(0.0);
            subject.setVelX(0.0);
        }
    }

    /*
    this method takes two PhysicsComponents and does collision response on them
    takes two references to PhysicsComponents, so this method modifies them directly

    public so it can be called from the main.game0logic package
     */
    public static void doCollisionResponse(PhysicsComponent pcOne, PhysicsComponent pcTwo) {
        //how much movement will be enacted on pcOne(pcOne's weight / total weight)
        double favorOnePercentage = determineMovePercentage(pcOne, pcTwo);

        if (favorOnePercentage < 0.0) { //both can't be moved
            return;
        }

        //all pcTwo coords - pcOne coords
        HitboxAabb hbOne = (HitboxAabb) pcOne.getData();
        HitboxAabb hbTwo = (HitboxAabb) pcTwo.getData();
        double deltaRight = hbTwo.getRight() - hbOne.getLeft(); //positive if overlapping
        double deltaLeft = hbOne.getRight() - hbTwo.getLeft(); //pos if overlapping
        double deltaDown = hbOne.getTop() - hbTwo.getBottom(); //pos if overlapping
        double deltaUp = hbTwo.getTop() - hbOne.getBottom(); //pos if overlapping

        //stores the movements that hbOne will have to undergo
        double xmove = getMinAbsValue(deltaRight, -deltaLeft);
        double ymove = getMinAbsValue(deltaUp, -deltaDown);

        moveHitboxes(xmove, ymove, favorOnePercentage, hbOne, hbTwo, pcOne, pcTwo);

        //flags for update in the two components
        pcOne.setUpdated();
        pcTwo.setUpdated();
    }

    private static double getMinAbsValue(double one, double two) {
        if (Math.abs(one) < Math.abs(two)) {
            return one;
        }

        return two;
    }

    //a private method used by collision response to actually move the hitboxes
    private static void moveHitboxes(double xmove, double ymove, double favorOnePercentage,
                                     HitboxAabb hbOne, HitboxAabb hbTwo,
                                     PhysicsComponent pcOne, PhysicsComponent pcTwo) {
        //picks the direction with the least movement required and moves the hitboxes according to their masses
        if (Math.abs(xmove) < Math.abs(ymove)) {
            hbOne.moveX(xmove * favorOnePercentage);
            hbTwo.moveX(-xmove * (1.0 - favorOnePercentage));
            if (Math.abs(xmove) == 0.0) { //sets velocity to 0 if touching a wall on that side
                pcOne.setVelX(0.0);
                pcTwo.setVelX(0.0);
            }
        } else {
            hbOne.moveY(ymove * favorOnePercentage);
            hbTwo.moveY(-ymove * (1.0 - favorOnePercentage));
            if (Math.abs(ymove) == 0.0) {
                pcOne.setVelY(0.0);
                pcTwo.setVelY(0.0);
            }
        }
    }

    //a private method used by the collision response method to determine how much each component moves as a percentage
    //returns a negative number if neither components can be moved
    private static double determineMovePercentage(PhysicsComponent pcOne, PhysicsComponent pcTwo) {
        //testing whether the objs can be moved
        int test = 0;
        if (pcOne.canBeMoved()) {
            test++;
        }
        if (pcTwo.canBeMoved()) {
            test += 2;
        }

        switch (test) {
            case 0: //neither can be moved
                return -1.0;
            case 1: //only pcOne can be moved
                return 1.0;
            case 2: //only pcTwo can be moved
                return 0.0;
            case 3:
            default: //both can be moved
                return (pcTwo.getMass() / (pcOne.getMass() + pcTwo.getMass())); //potential divide by zero error
        }
    }

    //sets the value for gravity on the y axis
    public void setGravityY(double newVal) {
        this.gravityY = newVal;
    }
}
