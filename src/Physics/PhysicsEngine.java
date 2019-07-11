package Physics;

import java.util.Iterator;
import java.util.Vector;
import java.lang.Math.*;

import Data.Communication.CollisionDetectedRequest;
import Data.Communication.GameScript;
import Data.Structure.*;
import Data.*;
import Utility.HitboxAABB;

/*

Takes care of all physics collision detection and response
uses the PhysicsComponent class

 */
public class PhysicsEngine {
    //member vars
    private Vector<GameComponent> v_gc_physicsComponents;

    //cstr
    public PhysicsEngine() {
        //initializes member var
        this.v_gc_physicsComponents = new Vector<GameComponent>();
    }

    /*
    this method goes through all hitboxes in the game and check for collision between each other
    takes a scene GameObject as an input
    takes a Vector<GameScript> as an output for error requests
     */
    public void doSceneCollisionDetection(GameObject go_scene, Vector<GameScript> v_gs_scripts) {
        //clears storage vector for storing new data
        this.v_gc_physicsComponents.clear();
        go_scene.compileComponentList(this.v_gc_physicsComponents, GameComponent.gcType.PHYSICS); //grabs all active physicscomponents

        //TODO maybe find a better way
        //collision detection
        int i_sizeOfVector = this.v_gc_physicsComponents.size();
        PhysicsComponent pc_one;
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

    private so only this class can use it XD
     */
    private static boolean doCollisionDetection(HitboxAABB hb_one, HitboxAABB hb_two) {
        //AABB collision detection
        if (hb_one.getLeft() <= hb_two.getRight() && hb_one.getRight() >= hb_two.getLeft()) {
            if (hb_one.getTop() >= hb_two.getBottom() && hb_one.getBottom() <= hb_two.getTop()) {
                return true;
            }
        }

        return false;
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
                d_favorOnePercentage = pc_one.getMass() / (pc_one.getMass() + pc_two.getMass()); //potential divide by zero error
        }

        //all pc_two coords - pc_one coords
        HitboxAABB hb_one = (HitboxAABB) pc_one.getData();
        HitboxAABB hb_two = (HitboxAABB) pc_two.getData();
        double d_deltaLeft = hb_two.getRight() - hb_one.getLeft();
        double d_deltaRight = hb_two.getLeft() - hb_one.getRight();
        double d_deltaTop = hb_two.getBottom() - hb_one.getTop();
        double d_deltaBottom = hb_two.getTop() - hb_one.getBottom();

        //TODO finish this part of the code
    }
}
