package Data;

import java.util.Iterator;
import java.util.Vector;
import Data.Structure.*;

public class GameObject {
    protected Vector<GameComponent> v_c_memberComponents;
    protected Vector<GameObject> v_go_memberObjects;

    //cstr
    public GameObject() {
        this.v_c_memberComponents = new Vector<GameComponent>();
        this.v_go_memberObjects = new Vector<GameObject>();
    }

    /*
    takes a GameComponent as an input and attaches it to the object
     */
    public void addComponent(GameComponent gc_component) {
        this.v_c_memberComponents.add(gc_component);
    }

    /*
    takes a GameObject as an input and adds it to the memberObjects list
     */
    public void addGameObject(GameObject go_obj) {
        this.v_go_memberObjects.add(go_obj);
    }

    /*
    Takes a vector of GameComponents as input
    that vector will be used as an output queue to store all game components of type gct_type into

    does not clear the vector output
     */
    public void compileComponentList(Vector<GameComponent> v_gc_output, GameComponent.gcType gct_type) {
        //variable to store individual GameComponents
        GameComponent gc_temp = null;

        //iterates through all components and only add the matching ones
        Iterator<GameComponent> gct_it = this.v_c_memberComponents.iterator();
        while (gct_it.hasNext()) {
            gc_temp = gct_it.next();
            if (gc_temp.getType() == gct_type) {
                v_gc_output.add(gc_temp); //only add if the type matches
            }
        }

        //iterates through all GameObjects to call this method recursively
        Iterator<GameObject> go_it = this.v_go_memberObjects.iterator();
        while (go_it.hasNext()) {
            go_it.next().compileComponentList(v_gc_output, gct_type); //recursive call
        }
    }
}
