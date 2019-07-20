package test;

import Data.GameObject;
import Data.GameScene;
import Data.Structure.GameComponent;
import Data.Structure.PhysicsComponent;
import Data.Structure.VisualTextureComponent;
import Utility.HitboxAABB;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.awt.*;
import java.util.Vector;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class GameObjectTest {
    private GameObject obj1;
    private GameObject obj2;
    private GameObject obj3;
    private GameObject obj4;
    private GameComponent gc1;
    private GameComponent gc2;
    private GameComponent gc3;
    private Vector<GameComponent> someVector;

    @BeforeEach
    public void before() {
        gc1 = new PhysicsComponent(new HitboxAABB(0.0, 1.0, 1.0, 0.0));
        gc2 = new VisualTextureComponent(null, new Rectangle(0, 0, 1, 1));
        gc3 = new PhysicsComponent(new HitboxAABB(1.2, 2.2, 2.2, 1.2));
        obj1 = new GameScene();
        obj2 = new GameScene();
        obj3 = new GameScene();
        obj4 = new GameScene();
        obj1.addGameObject(obj2);
        obj1.addGameObject(obj3);
        obj1.addComponent(gc1);
        obj1.addComponent(gc2);
        obj2.addComponent(gc2);
        obj3.addComponent(gc3);
        someVector = new Vector<GameComponent>();
    }

    @Test
    public void test1() {
        obj1.compileComponentList(someVector, GameComponent.gcType.PHYSICS);
        assertTrue(someVector.size() == 2);
    }

    @Test
    public void test2() {
        gc1.deactivate();
        obj1.compileComponentList(someVector, GameComponent.gcType.PHYSICS);
        assertTrue(someVector.size() == 1);
    }

    @Test
    public void test3() {
        obj1.compileComponentList(someVector, GameComponent.gcType.VISUAL_TEXTURE);
        assertTrue(someVector.size() == 2);
    }

    @Test
    public void test4() {
        gc2.deactivate();
        obj1.compileComponentList(someVector, GameComponent.gcType.VISUAL_TEXTURE);
        assertTrue(someVector.size() == 0);
    }

    @Test
    public void test5() {
        obj4.compileComponentList(someVector, GameComponent.gcType.VISUAL_TEXTURE);
        assertTrue(someVector.size() == 0);
    }

    @Test
    public void test6() {
        assertTrue(obj1.findFirstActiveComponentInObj(GameComponent.gcType.PHYSICS) == gc1);
        gc1.deactivate();
        assertTrue(obj1.findFirstActiveComponentInObj(GameComponent.gcType.PHYSICS) == null);
    }

    @Test
    public void test7() {
        obj1.setAllTags("1");
        obj2.setAllTags("2");
        obj3.setAllTags("3");
        assertTrue(gc1.getTag().equals("1"));
        assertTrue(gc2.getTag().equals("2"));
        assertTrue(gc3.getTag().equals("3"));
    }
}
