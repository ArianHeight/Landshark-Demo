package main.Data.Communication;

import main.Data.Structure.PhysicsComponent;

/*
This class will extend the GameScript class
will represent a single request to do collision response
 */
public class CollisionResponseRequest extends GameScript {
    private PhysicsComponent pc_one;
    private PhysicsComponent pc_two;

    //cstr
    public CollisionResponseRequest(PhysicsComponent one, PhysicsComponent two) {
        super(COLLISION_RESPONSE, "");
        this.pc_one = one;
        this.pc_two = two;
    }

    //accessors
    public PhysicsComponent getOne() { return this.pc_one; }
    public PhysicsComponent getTwo() { return this.pc_two; }
}
