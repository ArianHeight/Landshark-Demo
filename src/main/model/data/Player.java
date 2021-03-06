package model.data;

import model.data.structure.HpComponent;
import model.data.structure.PhysicsComponent;
import model.data.structure.VisualComponent;

import java.awt.*;

/*
This will be the class which represents a Player
extends the Actor class
implements the ControlInterface interface
 */
public class Player extends Actor implements ControlInterface {
    //cstr
    public Player(PhysicsComponent phys, VisualComponent visuals, HpComponent health) {
        super(phys, visuals, health);
        this.setAllTags("Player");
    }

    //alt cstr
    public Player(double topLeftX, double topLeftY, double width, double height,
                  double mass, boolean grav, Image texture, int hp) {
        super(topLeftX, topLeftY, width, height, mass, grav, texture, hp);
        this.setAllTags("Player");
    }

    /*
    returns whether or not the player is still alive
     */
    @Override
    public boolean isAlive() {
        return this.findHpComponent().isAlive();
    }

    /*
    REQUIRES:A valid String
    MODIFIES:this
    EFFECT:If the input equals "Die", then set player's hp to 0
     */
    @Override
    public void inputResponse(String input) {
        if (input.equals("KillPlayer")) {
            this.setHP(0); //kills the player
        }
    }
}
