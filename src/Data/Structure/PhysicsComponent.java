package Data.Structure;


import Utility.HitboxAABB;

/*
Extends GameComponent
represents a "physical" hitbox that the physics engine can work with
 */
public class PhysicsComponent extends GameComponent{
    private HitboxAABB hb_hitbox;

    //cstr
    public PhysicsComponent(double d_topLeftX, double d_topLeftY, double d_width, double d_height) {
        super(gcType.PHYSICS); //creates the physics type
        this.hb_hitbox = new HitboxAABB(d_topLeftX, d_topLeftX + d_width, d_topLeftY, d_topLeftY - d_height); //makes hitbox

    }

    //alt cstr
    public PhysicsComponent(HitboxAABB hb_hitbox) {
        super(gcType.PHYSICS);
        this.hb_hitbox = hb_hitbox;
    }

    /*
    REQUiRES:NONE
    MODIFIES:NONE
    EFFECT:Returns an Image obj that is carried by this component
     */
    @Override
    public Object getData() {
        return this.hb_hitbox;
    }
}
