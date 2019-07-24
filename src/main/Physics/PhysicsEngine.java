package main.Physics;

import main.Data.Communication.CollisionDetectedRequest;
import main.Data.Communication.GameScript;
import main.Data.GameObject;
import main.Data.Structure.GameComponent;
import main.Data.Structure.PhysicsComponent;
import main.Utility.HitboxAABB;

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
        scene.compileComponentList(this.physicsComponents, GameComponent.gcType.PHYSICS); //grabs all active physicscomponents

        //gravity and position
        Iterator<GameComponent> gcIt = this.physicsComponents.iterator();
        PhysicsComponent pcOne;
        while (gcIt.hasNext()) {
            pcOne = (PhysicsComponent)gcIt.next();
            doGravityCalculations(pcOne, this.gravityX, this.gravityY, timeElapsed);
            doPositionCalculations(pcOne, timeElapsed);
        }

        //TODO maybe find a better way
        //collision detection
        int sizeOfVector = this.physicsComponents.size();
        PhysicsComponent pcTwo;
        for (int i = 0; i < sizeOfVector; i++) {
            pcOne = (PhysicsComponent) (this.physicsComponents.get(i));
            for (int j = i + 1; j < sizeOfVector; j++) {
                pcTwo = (PhysicsComponent) (this.physicsComponents.get(j));
                if (doCollisionDetection((HitboxAABB) (pcTwo.getData()), (HitboxAABB) (pcOne.getData()))) {
                    scripts.add(new CollisionDetectedRequest(pcOne, pcTwo));
                }
            }
        }
    }

    /*
    this method takes two hitboxes and checks if they are colliding
     */
    public static boolean doCollisionDetection(HitboxAABB hbOne, HitboxAABB hbTwo) {
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
    public static void doGravityCalculations(PhysicsComponent subject, double accelX, double accelY, double timeElapsed) {
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
            HitboxAABB hb = (HitboxAABB) subject.getData();
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

    public so it can be called from the main.GameLogic package
     */
    public static void doCollisionResponse(PhysicsComponent pcOne, PhysicsComponent pcTwo) {
        //testing whether the objs can be moved
        int test = 0;
        if (pcOne.canBeMoved()) {
            test++;
        }
        if (pcTwo.canBeMoved()) {
            test += 2;
        }

        double favorOnePercentage = 0.0; //how much movement will be enacted on pcOne(pcOne's weight / total weight)
        switch (test) {
            case 0: //neither can be moved
                return;
            case 1: //only pcOne can be moved
                favorOnePercentage = 1.0;
                break;
            case 2: //only pcTwo can be moved
                favorOnePercentage = 0.0;
                break;
            case 3:
            default: //both can be moved
                favorOnePercentage = pcTwo.getMass() / (pcOne.getMass() + pcTwo.getMass()); //potential divide by zero error
        }

        //all pcTwo coords - pcOne coords
        HitboxAABB hbOne = (HitboxAABB) pcOne.getData();
        HitboxAABB hbTwo = (HitboxAABB) pcTwo.getData();
        double deltaRight = hbTwo.getRight() - hbOne.getLeft(); //positive if overlapping
        double deltaLeft = hbOne.getRight() - hbTwo.getLeft(); //pos if overlapping
        double deltaDown = hbOne.getTop() - hbTwo.getBottom(); //pos if overlapping
        double deltaUp = hbTwo.getTop() - hbOne.getBottom(); //pos if overlapping

        //stores the movements that hbOne will have to undergo
        double xmove = -deltaLeft;
        double ymove = -deltaDown;

        if (Math.abs(deltaRight) < Math.abs(xmove)) {
            xmove = deltaRight;
        }
        if (Math.abs(deltaUp) < Math.abs(ymove)) {
            ymove = deltaUp;
        }

        //picks the direction with the least movement required and moves the hitboxes according to their masses
        if (Math.abs(xmove) < Math.abs(ymove)) {
            hbOne.moveX(xmove * favorOnePercentage);
            hbTwo.moveX(-xmove * (1.0 - favorOnePercentage));
            if (Math.abs(xmove) == 0.0) {
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

        //flags for update in the two components
        pcOne.setUpdated();
        pcTwo.setUpdated();
    }
}
