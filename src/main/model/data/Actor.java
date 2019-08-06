package main.model.data;

import main.model.data.structure.*;

import java.awt.*;

/*
This will represent any GameObject with an hitbox, hp, and textures/animation
extends GameObject
 */
public class Actor extends GameObject {
    //cstr
    //adds in order
    //physics
    //texture
    //hp
    public Actor(PhysicsComponent physics, VisualComponent visuals, HpComponent health) {
        this.addComponent(physics); //add physics
        this.addComponent(visuals); //add texture
        this.addComponent(health); //add hp
    }

    //alt cstr
    //adds in order
    //physics
    //texture
    //hp
    public Actor(double topLeftX, double topLeftY, double width, double height,
                 double mass, boolean grav, Image texture, int hp) {
        this.addComponent(new PhysicsComponent(topLeftX, topLeftY, width, height, mass, grav)); //physics
        this.addComponent(new VisualTextureComponent(texture, new Rectangle(0, 0, 1, 1))); //texture
        this.addComponent(new HpComponent(hp)); //hp
    }

    /*
    this method takes hp off of the HpComponent

    returns -1 if there is no hp
    returns 0 if the obj has 0 hp
    returns 1 if the obj has more than 0 hp
     */
    public int takeDmg(int dmg) {
        //grabs the hp
        HpComponent health = this.findHpComponent();

        if (health == null) { //null guard
            return -1;
        }

        boolean returnVal = health.takeDmg(dmg); //take dmg
        if (returnVal) { //obj is alive
            return 1;
        }

        return 0; //obj is ded
    }

    /*
    this method takes an int input and sets the hp to that value
     */
    public void setHP(int newHP) {
        //grabs the hp
        HpComponent health = this.findHpComponent();

        if (health == null) { //null guard
            return; //no hp
        }

        health.setHP(newHP); //sets the hp
    }

    //protected method to find the HpComponent quickly
    protected HpComponent findHpComponent() {
        return (HpComponent) this.findFirstActiveComponentInObj(GameComponent.GcType.HITPOINT);
    }
}
