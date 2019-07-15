package Data;

import Data.Structure.GameComponent;

import java.util.Iterator;
import java.util.Vector;

public abstract class GameObject {
    protected Vector<GameComponent> v_c_memberComponents;
    protected Vector<GameObject> v_go_memberObjects;

    //cstr
    public GameObject() {
        this.v_c_memberComponents = new Vector<GameComponent>();
        this.v_go_memberObjects = new Vector<GameObject>();
    }

    /*
    REQUIRES:gc_component is not null
    MODIFIES:this
    EFFECT:takes a GameComponent as an input and attaches it to the object
     */
    public void addComponent(GameComponent gc_component) {
        this.v_c_memberComponents.add(gc_component);
    }

    /*
    REQUIRES:go_obj is not null
    MODIFIES:this
    EFFECT:takes a GameObject as an input and adds it to the memberObjects list
     */
    public void addGameObject(GameObject go_obj) {
        this.v_go_memberObjects.add(go_obj);
    }

    /*
    REQUIRES:A valid String
    MODIFIES:this
    EFFECT:Sets the tags for every member component to the str_input
     */
    public void setAllTags(String str_tag) {
        Iterator<GameComponent> gc_it = this.v_c_memberComponents.iterator();
        while (gc_it.hasNext()) {
            gc_it.next().setTag(str_tag);
        }
    }

    /*
    MODIFIES:v_gc_output
    EFFECT:Takes a vector of GameComponents as input
           that vector will be used as an output queue to store all active game components of type gct_type
           from this Object and all of its children into
           does not clear the vector output
     */
    public void compileComponentList(Vector<GameComponent> v_gc_output, GameComponent.gcType gct_type) {
        //variable to store individual GameComponents
        GameComponent gc_temp = null;

        //iterates through all components and only add the matching ones
        Iterator<GameComponent> gct_it = this.v_c_memberComponents.iterator();
        while (gct_it.hasNext()) {
            gc_temp = gct_it.next();
            if (gc_temp.getType() == gct_type && gc_temp.isActive()) {
                v_gc_output.add(gc_temp); //only add if the type matches
            }
        }

        //iterates through all GameObjects to call this method recursively
        Iterator<GameObject> go_it = this.v_go_memberObjects.iterator();
        while (go_it.hasNext()) {
            go_it.next().compileComponentList(v_gc_output, gct_type); //recursive call
        }
    }

    /*
    MODIFIES:None
    EFFECT:takes a game component type and returns the first active component of that type of this object
           returns null if nothing is found
     */
    public GameComponent findFirstActiveComponentInObj(GameComponent.gcType gct_type) {
        //variable to store individual GameComponents
        GameComponent gc_temp = null;

        //iterates through all components and only add the matching ones
        Iterator<GameComponent> gct_it = this.v_c_memberComponents.iterator();
        while (gct_it.hasNext()) {
            gc_temp = gct_it.next();
            if (gc_temp.getType() == gct_type && gc_temp.isActive()) {
                return gc_temp;
            }
        }

        return null; //non found
    }

    /*
    this method takes no inputs and gives no outut
    it runs an updateObj() call on the object itself, followed by a recursive call to
    every child object

    it is up to the child objects to implement their own update code and call super.updateObj() at the end of it
    as GameObject.updateObj() is the recursive call to all child objects
     */
    public void updateObj() {
        //recursive call
        Iterator<GameObject> go_it = this.v_go_memberObjects.iterator();
        while (go_it.hasNext()) {
            go_it.next().updateObj();
        }
    }
}
