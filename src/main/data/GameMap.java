package main.data;

import main.data.structure.PhysicsComponent;
import main.data.structure.VisualTextureComponent;
import main.utility.HitboxAabb;

import java.awt.*;

/*

this class extends the GameObject class
will represent a map in game
a map will have
- at least one unmoveable(by physics engine) and not affected by gravity PhysicsComponent
- at least one VisualTextureComponent

 */
public class GameMap extends GameObject {
    //cstr
    public GameMap(PhysicsComponent pc, VisualTextureComponent vtc) {
        this.addComponent(pc); //add physics
        this.addComponent(vtc); //add texture
        this.setAllTags("Map");
    }

    //alt cstr
    public GameMap(HitboxAabb hitbox, Image data) {
        this.addComponent(new PhysicsComponent(hitbox, -1.0, false)); //add physics
        this.addComponent(new VisualTextureComponent(data, new Rectangle(0, 0, 1, 1))); //add textures
        this.setAllTags("Map");
    }
}
