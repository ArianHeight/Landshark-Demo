import Data.Communication.GameScript;
import Data.GameObject;
import Data.Structure.GameComponent;
import Data.Structure.PhysicsComponent;
import IO.GameFileWriter;
import IO.IOEngine;
import Physics.PhysicsEngine;
import Utility.HitboxAABB;

import java.util.Vector;


public class Tester {
    public static void run() {
        /*GameObject obj = new GameObject();
        obj.addComponent(new PhysicsComponent(new HitboxAABB(0.0, 1.0, 1.0, 0.0)));
        GameObject obj_two = new GameObject();
        obj_two.addComponent(new PhysicsComponent(new HitboxAABB(0.5, 1.5, 1.0, 0.0)));
        GameObject obj_three = new GameObject();
        obj_three.addComponent(new PhysicsComponent(new HitboxAABB(1.2, 2.2, 2.2, 1.2)));
        Vector<GameScript> someVector = new Vector<GameScript>();
        obj.addGameObject(obj_two);
        obj.addGameObject(obj_three);

        PhysicsEngine pe = new PhysicsEngine();
        pe.doSceneCollisionDetection(obj, someVector);
        System.out.println(someVector.size());
        */
    }
}
