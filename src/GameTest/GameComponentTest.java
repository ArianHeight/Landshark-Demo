package GameTest;

import Data.Structure.GameComponent;
import Data.Structure.PhysicsComponent;
import Data.Structure.VisualTextureComponent;
import Utility.HitboxAABB;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.swing.*;
import java.awt.*;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class GameComponentTest {
    GameComponent gc_subject;
    HitboxAABB hb;

    @BeforeEach
    public void before() {

    }

    @Test
    public void test1() {
        gc_subject = new PhysicsComponent(0.0, 1.0, 1.0, 1.0);
        assertTrue(gc_subject.isActive());
        gc_subject.deactivate();
        assertTrue(!gc_subject.isActive());
        gc_subject.activate();
        assertTrue(gc_subject.isActive());
        assertTrue(gc_subject.getType() == GameComponent.gcType.PHYSICS);
    }

    @Test
    public void test1b() {
        gc_subject = new PhysicsComponent(0.0, 1.0, 1.0, 1.0);
        assertTrue(((PhysicsComponent)gc_subject).canBeMoved());
        assertTrue(!((PhysicsComponent)gc_subject).updatedByEngine());
        assertTrue(((PhysicsComponent)gc_subject).getMass() == 1.0);
        ((PhysicsComponent)gc_subject).setUpdated();
        assertTrue(((PhysicsComponent)gc_subject).updatedByEngine());
        ((PhysicsComponent)gc_subject).resetUpdated();
        assertTrue(!((PhysicsComponent)gc_subject).updatedByEngine());
    }

    @Test
    public void test1c() {
        Image im = new ImageIcon("./Game/Assets/Textures/zombieKnight.png").getImage();
        Rectangle r = new Rectangle(0, 0, 1, 1);
        gc_subject = new VisualTextureComponent(im, r);
        assertTrue(gc_subject.getType() == GameComponent.gcType.VISUAL_TEXTURE);
        assertTrue(((VisualTextureComponent) gc_subject).getTexture() == im);
        assertTrue((Image)(gc_subject.getData()) == im);
        assertTrue(((VisualTextureComponent) gc_subject).getRenderPlane() == r);
    }

    @Test
    public void test2() {
        gc_subject = new PhysicsComponent(0.0, 1.0, 1.0, 1.0);
        hb = (HitboxAABB) gc_subject.getData();
        assertTrue(hb.getLeft() == 0.0);
        assertTrue(hb.getRight() == 1.0);
        assertTrue(hb.getTop() == 1.0);
        assertTrue(hb.getBottom() == 0.0);
        assertTrue(((PhysicsComponent)gc_subject).canBeMoved());
        assertTrue(!((PhysicsComponent)gc_subject).updatedByEngine());
        assertTrue(((PhysicsComponent)gc_subject).getMass() == 1.0);
    }

    @Test
    public void test3() {
        gc_subject = new PhysicsComponent(0.0, 1.0, 1.0, 1.0, 2.0);
        hb = (HitboxAABB) gc_subject.getData();
        assertTrue(hb.getLeft() == 0.0);
        assertTrue(hb.getRight() == 1.0);
        assertTrue(hb.getTop() == 1.0);
        assertTrue(hb.getBottom() == 0.0);
        assertTrue(((PhysicsComponent)gc_subject).canBeMoved());
        assertTrue(!((PhysicsComponent)gc_subject).updatedByEngine());
        assertTrue(((PhysicsComponent)gc_subject).getMass() == 2.0);
    }

    @Test
    public void test4() {
        gc_subject = new PhysicsComponent(0.0, 1.0, 1.0, 1.0, -2.0);
        hb = (HitboxAABB) gc_subject.getData();
        assertTrue(hb.getLeft() == 0.0);
        assertTrue(hb.getRight() == 1.0);
        assertTrue(hb.getTop() == 1.0);
        assertTrue(hb.getBottom() == 0.0);
        assertTrue(!((PhysicsComponent)gc_subject).canBeMoved());
        assertTrue(!((PhysicsComponent)gc_subject).updatedByEngine());
        assertTrue(((PhysicsComponent)gc_subject).getMass() == -2.0);
    }

    @Test
    public void test5() {
        gc_subject = new PhysicsComponent(new HitboxAABB(0.0, 1.0, 1.0, 0.0));
        hb = (HitboxAABB) gc_subject.getData();
        assertTrue(hb.getLeft() == 0.0);
        assertTrue(hb.getRight() == 1.0);
        assertTrue(hb.getTop() == 1.0);
        assertTrue(hb.getBottom() == 0.0);
        assertTrue(((PhysicsComponent)gc_subject).canBeMoved());
        assertTrue(!((PhysicsComponent)gc_subject).updatedByEngine());
        assertTrue(((PhysicsComponent)gc_subject).getMass() == 1.0);
    }

    @Test
    public void test6() {
        gc_subject = new PhysicsComponent(new HitboxAABB(0.0, 1.0, 1.0, 0.0), -1.0);
        hb = (HitboxAABB) gc_subject.getData();
        assertTrue(hb.getLeft() == 0.0);
        assertTrue(hb.getRight() == 1.0);
        assertTrue(hb.getTop() == 1.0);
        assertTrue(hb.getBottom() == 0.0);
        assertTrue(!((PhysicsComponent)gc_subject).canBeMoved());
        assertTrue(!((PhysicsComponent)gc_subject).updatedByEngine());
        assertTrue(((PhysicsComponent)gc_subject).getMass() == -1.0);
    }

    @Test
    public void test7() {
        gc_subject = new PhysicsComponent(new HitboxAABB(0.0, 1.0, 1.0, 0.0), 50.0);
        hb = (HitboxAABB) gc_subject.getData();
        assertTrue(hb.getLeft() == 0.0);
        assertTrue(hb.getRight() == 1.0);
        assertTrue(hb.getTop() == 1.0);
        assertTrue(hb.getBottom() == 0.0);
        assertTrue(((PhysicsComponent)gc_subject).canBeMoved());
        assertTrue(!((PhysicsComponent)gc_subject).updatedByEngine());
        assertTrue(((PhysicsComponent)gc_subject).getMass() == 50.0);
    }


}
