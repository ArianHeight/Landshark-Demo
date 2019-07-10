package Physics;

import java.util.Vector;
import Data.Structure.*;
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
}
