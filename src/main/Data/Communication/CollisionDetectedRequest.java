package main.Data.Communication;

import main.Data.Structure.PhysicsComponent;

/*
this class extends the GameEventRequest
a data structure used to store two physics components
 */
public class CollisionDetectedRequest extends GameEventRequest{
    private PhysicsComponent pc_one;
    private PhysicsComponent pc_two;

    //cstr
    public CollisionDetectedRequest(PhysicsComponent one, PhysicsComponent two) {
        super("Collision");
        this.pc_one = one;
        this.pc_two = two;
    }

    //accessors
    public PhysicsComponent getOne() { return this.pc_one; }
    public PhysicsComponent getTwo() { return this.pc_two; }
}
