package model.data.communication;

import model.data.structure.PhysicsComponent;

/*
this class extends the GameEventRequest
a data structure used to store two physics components
 */
public class CollisionDetectedRequest extends GameEventRequest {
    private PhysicsComponent one;
    private PhysicsComponent two;

    //cstr
    public CollisionDetectedRequest(PhysicsComponent one, PhysicsComponent two) {
        super("Collision");
        this.one = one;
        this.two = two;
    }

    //accessors
    //gets first phys component
    public PhysicsComponent getOne() {
        return this.one;
    }

    //gets the second phys component
    public PhysicsComponent getTwo() {
        return this.two;
    }
}
