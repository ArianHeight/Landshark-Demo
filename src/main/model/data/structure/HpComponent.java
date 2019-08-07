package model.data.structure;

/*
this class extends the GameComponent class
this component holds information about the hp of the object
 */
public class HpComponent extends GameComponent {
    private Integer hp;

    //cstr
    public HpComponent(int hitpoint) {
        super(GcType.HITPOINT);

        this.hp = hitpoint;
    }

    /*
    returns a single Integer representing the hitpoints left
     */
    @Override
    public Object getData() {
        return this.hp;
    }

    /*
    returns whether the hp value is greater than 0
     */
    public boolean isAlive() {
        return this.hp > 0;
    }

    /*
    take dmg to health
    returns whether the obj is still alive after the dmg is taken
     */
    public boolean takeDmg(int dmg) {
        this.hp -= dmg;
        return this.isAlive();
    }

    /*
    takes an int input
    sets the health value to that int value
     */
    public void setHP(int newValue) {
        this.hp = newValue;
    }
}
