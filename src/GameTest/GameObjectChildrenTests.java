package GameTest;

import Data.*;
import Data.Structure.GameComponent;
import Data.Structure.HPComponent;
import Data.Structure.PhysicsComponent;
import Data.Structure.VisualTextureComponent;
import Utility.HitboxAABB;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.awt.*;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class GameObjectChildrenTests {
    GameObject subject;

    @BeforeEach
    public void before() {

    }

    @Test
    public void test1() {
        HitboxAABB hb = new HitboxAABB(0.0, 1.0, 1.0, 0.0);
        subject = new GameMap(hb, null);
        assertTrue(subject != null);
        assertTrue(subject.findFirstActiveComponentInObj(GameComponent.gcType.PHYSICS).getData() == hb);
        assertTrue(subject.findFirstActiveComponentInObj(GameComponent.gcType.VISUAL_TEXTURE).getData() == null);
    }

    @Test
    public void test1b() {
        HitboxAABB hb = new HitboxAABB(0.0, 1.0, 1.0, 0.0);
        PhysicsComponent pc = new PhysicsComponent(hb, 2.0, false);
        VisualTextureComponent vtc = new VisualTextureComponent(null, new Rectangle(0, 0, 1, 1), hb, 1);
        subject = new GameMap(pc, vtc);
        assertTrue(subject != null);
        assertTrue(subject.findFirstActiveComponentInObj(GameComponent.gcType.PHYSICS) == pc);
        assertTrue(subject.findFirstActiveComponentInObj(GameComponent.gcType.VISUAL_TEXTURE) == vtc);
    }

    @Test
    public void test2() {
        subject = new GameScene();
        assertTrue(subject != null);
    }

    @Test
    public void test3() {
        HitboxAABB hb = new HitboxAABB(0.0, 1.0, 1.0, 0.0);
        PhysicsComponent pc = new PhysicsComponent(hb, 2.0, false);
        VisualTextureComponent vtc = new VisualTextureComponent(null, new Rectangle(0, 0, 1, 1), hb, 1);
        HPComponent hp = new HPComponent(5);
        subject = new Actor(pc, vtc, hp);
        assertTrue(subject != null);
        assertTrue(subject.findFirstActiveComponentInObj(GameComponent.gcType.PHYSICS) == pc);
        assertTrue(subject.findFirstActiveComponentInObj(GameComponent.gcType.VISUAL_TEXTURE) == vtc);
        assertTrue(subject.findFirstActiveComponentInObj(GameComponent.gcType.HITPOINT) == hp);

        ((Actor)subject).setHP(10);
        assertTrue(hp.getData().equals(10));
        assertTrue(((Actor)subject).takeDmg(5) == 1);
        assertTrue(hp.getData().equals(5));
        assertTrue(((Actor)subject).takeDmg(6) == 0);
        assertTrue(hp.getData().equals(-1));
    }

    @Test
    public void test4() {
        subject = new Actor(0.0, 1.0, 1.0, 1.0, 2.0, true, null, 5);
        assertTrue(subject != null);
        HitboxAABB hb = (HitboxAABB)subject.findFirstActiveComponentInObj(GameComponent.gcType.PHYSICS).getData();
        assertTrue(hb.getTop() == 1.0);
        assertTrue(hb.getLeft() == 0.0);
        assertTrue(hb.getBottom() == 0.0);
        assertTrue(hb.getRight() == 1.0);
        assertTrue(subject.findFirstActiveComponentInObj(GameComponent.gcType.HITPOINT).getData().equals(5));
        assertTrue(((VisualTextureComponent)subject.findFirstActiveComponentInObj(GameComponent.gcType.VISUAL_TEXTURE)).getTexture() == null);
    }

    @Test
    public void test5() {
        HitboxAABB hb = new HitboxAABB(0.0, 1.0, 1.0, 0.0);
        PhysicsComponent pc = new PhysicsComponent(hb, 2.0, false);
        VisualTextureComponent vtc = new VisualTextureComponent(null, new Rectangle(0, 0, 1, 1), hb, 1);
        HPComponent hp = new HPComponent(5);
        subject = new Player(pc, vtc, hp);
        assertTrue(subject.findFirstActiveComponentInObj(GameComponent.gcType.PHYSICS) == pc);
        assertTrue(subject.findFirstActiveComponentInObj(GameComponent.gcType.VISUAL_TEXTURE) == vtc);
        assertTrue(subject.findFirstActiveComponentInObj(GameComponent.gcType.HITPOINT) == hp);

        assertTrue(((Player)subject).isAlive());
        ((Player)subject).inputResponse("KillPlayer");
        assertTrue(!((Player)subject).isAlive());
    }

    @Test
    public void test5b() {
        subject = new Player(0.0, 1.0, 1.0, 1.0, 2.0, true, null, 5);
        assertTrue(subject != null);
        HitboxAABB hb = (HitboxAABB)subject.findFirstActiveComponentInObj(GameComponent.gcType.PHYSICS).getData();
        assertTrue(hb.getTop() == 1.0);
        assertTrue(hb.getLeft() == 0.0);
        assertTrue(hb.getBottom() == 0.0);
        assertTrue(hb.getRight() == 1.0);
        assertTrue(subject.findFirstActiveComponentInObj(GameComponent.gcType.HITPOINT).getData().equals(5));
        assertTrue(((VisualTextureComponent)subject.findFirstActiveComponentInObj(GameComponent.gcType.VISUAL_TEXTURE)).getTexture() == null);

    }
}
