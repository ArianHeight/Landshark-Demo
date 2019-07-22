package test;

import main.Data.Structure.GameComponent;
import main.Data.Structure.HPComponent;
import main.Data.Structure.PhysicsComponent;
import main.Data.Structure.VisualTextureComponent;
import main.Utility.HitboxAABB;
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
        gc_subject.setTag("1");
        assertTrue(gc_subject.getTag().equals("1"));
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
    public void test1d() {
        Image im = new ImageIcon("./Game/Assets/Textures/zombieKnight.png").getImage();
        Rectangle r = new Rectangle(0, 0, 1, 1);
        HitboxAABB hb = new HitboxAABB(0.0, 1.0, 1.0, 0.0);
        gc_subject = new VisualTextureComponent(im, r, hb, 1);
        assertTrue(gc_subject.getType() == GameComponent.gcType.VISUAL_TEXTURE);
        assertTrue(((VisualTextureComponent) gc_subject).getTexture() == im);
        assertTrue((Image)(gc_subject.getData()) == im);
        assertTrue(((VisualTextureComponent) gc_subject).getRenderPlane() == r);
        assertTrue(((VisualTextureComponent) gc_subject).getWorldPosRef() == hb);
        assertTrue(((VisualTextureComponent) gc_subject).getLayer() == 1);
        ((VisualTextureComponent) gc_subject).setLayerVal(2);
        assertTrue(((VisualTextureComponent) gc_subject).getLayer() == 2);
    }

    @Test
    public void test1e() {
        gc_subject = new HPComponent(10);
        assertTrue(gc_subject.getType() == GameComponent.gcType.HITPOINT);
        assertTrue(((HPComponent)gc_subject).isAlive());
        assertTrue(!((HPComponent)gc_subject).takeDmg(10));
        ((HPComponent)gc_subject).setHP(5);
        assertTrue(((HPComponent)gc_subject).isAlive());
        assertTrue(((Integer)gc_subject.getData()).equals(5));
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
        assertTrue(((PhysicsComponent)gc_subject).affectedByGravity());
    }

    @Test
    public void test3() {
        gc_subject = new PhysicsComponent(0.0, 1.0, 1.0, 1.0, 2.0, false);
        hb = (HitboxAABB) gc_subject.getData();
        assertTrue(hb.getLeft() == 0.0);
        assertTrue(hb.getRight() == 1.0);
        assertTrue(hb.getTop() == 1.0);
        assertTrue(hb.getBottom() == 0.0);
        assertTrue(((PhysicsComponent)gc_subject).canBeMoved());
        assertTrue(!((PhysicsComponent)gc_subject).updatedByEngine());
        assertTrue(((PhysicsComponent)gc_subject).getMass() == 2.0);
        assertTrue(!((PhysicsComponent)gc_subject).affectedByGravity());
    }

    @Test
    public void test4() {
        gc_subject = new PhysicsComponent(0.0, 1.0, 1.0, 1.0, -2.0, true);
        hb = (HitboxAABB) gc_subject.getData();
        assertTrue(hb.getLeft() == 0.0);
        assertTrue(hb.getRight() == 1.0);
        assertTrue(hb.getTop() == 1.0);
        assertTrue(hb.getBottom() == 0.0);
        assertTrue(!((PhysicsComponent)gc_subject).canBeMoved());
        assertTrue(!((PhysicsComponent)gc_subject).updatedByEngine());
        assertTrue(((PhysicsComponent)gc_subject).getMass() == -2.0);
        assertTrue(((PhysicsComponent)gc_subject).affectedByGravity());
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
        assertTrue(((PhysicsComponent)gc_subject).affectedByGravity());
    }

    @Test
    public void test6() {
        gc_subject = new PhysicsComponent(new HitboxAABB(0.0, 1.0, 1.0, 0.0), -1.0, false);
        hb = (HitboxAABB) gc_subject.getData();
        assertTrue(hb.getLeft() == 0.0);
        assertTrue(hb.getRight() == 1.0);
        assertTrue(hb.getTop() == 1.0);
        assertTrue(hb.getBottom() == 0.0);
        assertTrue(!((PhysicsComponent)gc_subject).canBeMoved());
        assertTrue(!((PhysicsComponent)gc_subject).updatedByEngine());
        assertTrue(((PhysicsComponent)gc_subject).getMass() == -1.0);
        assertTrue(!((PhysicsComponent)gc_subject).affectedByGravity());
    }

    @Test
    public void test7() {
        gc_subject = new PhysicsComponent(new HitboxAABB(0.0, 1.0, 1.0, 0.0), 50.0, true);
        hb = (HitboxAABB) gc_subject.getData();
        assertTrue(hb.getLeft() == 0.0);
        assertTrue(hb.getRight() == 1.0);
        assertTrue(hb.getTop() == 1.0);
        assertTrue(hb.getBottom() == 0.0);
        assertTrue(((PhysicsComponent)gc_subject).canBeMoved());
        assertTrue(!((PhysicsComponent)gc_subject).updatedByEngine());
        assertTrue(((PhysicsComponent)gc_subject).getMass() == 50.0);
        assertTrue(((PhysicsComponent)gc_subject).affectedByGravity());
    }

    @Test
    public void test8() {
        gc_subject = new PhysicsComponent(new HitboxAABB(0.0, 1.0, 1.0, 0.0), 50.0, true);
        ((PhysicsComponent)gc_subject).setVelX(1.0);
        ((PhysicsComponent)gc_subject).setVelY(2.0);
        assertTrue(((PhysicsComponent)gc_subject).getVelX() == 1.0);
        assertTrue(((PhysicsComponent)gc_subject).getVelY() == 2.0);
        ((PhysicsComponent)gc_subject).addVelX(3.0);
        ((PhysicsComponent)gc_subject).addVelY(4.0);
        assertTrue(((PhysicsComponent)gc_subject).getVelX() == 4.0);
        assertTrue(((PhysicsComponent)gc_subject).getVelY() == 6.0);
    }
}
