package main.model.data.communication;

import main.model.data.structure.PhysicsComponent;

/*
This class will extend the GameScript class
will represent a single request to do collision response
TODO do abstraction w/ collisionDetectedRequest
 */
public class CollisionResponseRequest extends GameScript {
    private PhysicsComponent one;
    private PhysicsComponent two;

    //cstr
    public CollisionResponseRequest(PhysicsComponent one, PhysicsComponent two) {
        super(COLLISION_RESPONSE, "");
        this.one = one;
        this.two = two;
    }

    //accessors
    public PhysicsComponent getOne() {
        return this.one;
    }

    public PhysicsComponent getTwo() {
        return this.two;
    }
}