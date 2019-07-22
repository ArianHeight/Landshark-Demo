package main.Data.Structure;

/*
this class extends the GameComponent class
this component holds information about the hp of the object
 */
public class HPComponent extends GameComponent {
    private Integer i_hp;

    //cstr
    public HPComponent(int i_hitpoint) {
        super(gcType.HITPOINT);

        this.i_hp = i_hitpoint;
    }

    /*
    returns a single Integer representing the hitpoints left
     */
    @Override
    public Object getData() {
        return this.i_hp;
    }

    /*
    returns whether the hp value is greater than 0
     */
    public boolean isAlive() {
        return this.i_hp > 0;
    }

    /*
    take dmg to health
    returns whether the obj is still alive after the dmg is taken
     */
    public boolean takeDmg(int i_dmg) {
        this.i_hp -= i_dmg;
        return this.isAlive();
    }

    /*
    takes an int input
    sets the health value to that int value
     */
    public void setHP(int i_newValue) {
        this.i_hp = i_newValue;
    }
}
