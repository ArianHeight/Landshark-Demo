package main.data;

import main.data.structure.GameComponent;

import java.util.Iterator;
import java.util.Vector;

public abstract class GameObject {
    protected Vector<GameComponent> memberComponents;
    protected Vector<GameObject> memberObjects;
    private boolean delete; //if true then delete from the scene

    //cstr
    public GameObject() {
        this.memberComponents = new Vector<GameComponent>();
        this.memberObjects = new Vector<GameObject>();
        this.delete = false;
    }

    /*
    REQUIRES:component is not null
    MODIFIES:this
    EFFECT:takes a GameComponent as an input and attaches it to the object
     */
    public void addComponent(GameComponent component) {
        this.memberComponents.add(component);
    }

    /*
    REQUIRES:obj is not null
    MODIFIES:this
    EFFECT:takes a GameObject as an input and adds it to the memberObjects list
     */
    public void addGameObject(GameObject obj) {
        this.memberObjects.add(obj);
    }

    /*
    REQUIRES:A valid String
    MODIFIES:this
    EFFECT:Sets the tags for every member component to the input
     */
    public void setAllTags(String tag) {
        Iterator<GameComponent> gcIt = this.memberComponents.iterator();
        while (gcIt.hasNext()) {
            gcIt.next().setTag(tag);
        }
    }

    /*
    MODIFIES:output
    EFFECT:Takes a vector of GameComponents as input
           that vector will be used as an output queue to store all active game components of type type
           from this Object and all of its children into
           does not clear the vector output
     */
    public void compileComponentList(Vector<GameComponent> output, GameComponent.gcType type) {
        //variable to store individual GameComponents
        GameComponent gcTemp = null;

        //iterates through all components and only add the matching ones
        Iterator<GameComponent> gcIt = this.memberComponents.iterator();
        while (gcIt.hasNext()) {
            gcTemp = gcIt.next();
            if (gcTemp.getType() == type && gcTemp.isActive()) {
                output.add(gcTemp); //only add if the type matches
            }
        }

        //iterates through all GameObjects to call this method recursively
        Iterator<GameObject> goIt = this.memberObjects.iterator();
        while (goIt.hasNext()) {
            goIt.next().compileComponentList(output, type); //recursive call
        }
    }

    /*
    MODIFIES:None
    EFFECT:takes a game component type and returns the first active component of that type of this object
           returns null if nothing is found
     */
    public GameComponent findFirstActiveComponentInObj(GameComponent.gcType type) {
        //variable to store individual GameComponents
        GameComponent gcTemp = null;

        //iterates through all components and only add the matching ones
        Iterator<GameComponent> it = this.memberComponents.iterator();
        while (it.hasNext()) {
            gcTemp = it.next();
            if (gcTemp.getType() == type && gcTemp.isActive()) {
                return gcTemp;
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

    Also removes objs set up for deleting from the scene graph
     */
    public void updateObj() {
        //recursive call
        Iterator<GameObject> goIt = this.memberObjects.iterator();
        GameObject goTemp = null;
        while (goIt.hasNext()) {
            goTemp = goIt.next();
            goTemp.updateObj();

            if (goTemp.delete) {
                goIt.remove();
            }
        }
    }

    /*
    this method, when called, will set the object to be deleted
     */
    protected void setForDelete() {
        this.delete = true;
    }
}
