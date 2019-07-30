package test;

import main.data.structure.GameComponent;
import main.data.structure.HpComponent;
import main.data.structure.PhysicsComponent;
import main.data.structure.VisualTextureComponent;
import main.utility.HitboxAabb;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.swing.*;
import java.awt.*;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class GameComponentTest {
    GameComponent subject;
    HitboxAabb hb;

    @BeforeEach
    public void before() {

    }

    @Test
    public void test1() {
        subject = new PhysicsComponent(0.0, 1.0, 1.0, 1.0);
        assertTrue(subject.isActive());
        subject.deactivate();
        assertTrue(!subject.isActive());
        subject.activate();
        assertTrue(subject.isActive());
        assertTrue(subject.getType() == GameComponent.GcType.PHYSICS);
        subject.setTag("1");
        assertTrue(subject.getTag().equals("1"));
    }

    @Test
    public void test1b() {
        subject = new PhysicsComponent(0.0, 1.0, 1.0, 1.0);
        assertTrue(((PhysicsComponent)subject).canBeMoved());
        assertTrue(!((PhysicsComponent)subject).updatedByEngine());
        assertTrue(((PhysicsComponent)subject).getMass() == 1.0);
        ((PhysicsComponent)subject).setUpdated();
        assertTrue(((PhysicsComponent)subject).updatedByEngine());
        ((PhysicsComponent)subject).resetUpdated();
        assertTrue(!((PhysicsComponent)subject).updatedByEngine());
    }

    @Test
    public void test1c() {
        Image im = new ImageIcon("./Game/Assets/Textures/zombieKnight.png").getImage();
        Rectangle r = new Rectangle(0, 0, 1, 1);
        subject = new VisualTextureComponent(im, r);
        assertTrue(subject.getType() == GameComponent.GcType.VISUAL_TEXTURE);
        assertTrue(((VisualTextureComponent) subject).getTexture() == im);
        assertTrue((Image)(subject.getData()) == im);
        assertTrue(((VisualTextureComponent) subject).getRenderPlane() == r);
    }

    @Test
    public void test1d() {
        Image im = new ImageIcon("./Game/Assets/Textures/zombieKnight.png").getImage();
        Rectangle r = new Rectangle(0, 0, 1, 1);
        HitboxAabb hb = new HitboxAabb(0.0, 1.0, 1.0, 0.0);
        subject = new VisualTextureComponent(im, r, hb, 1);
        assertTrue(subject.getType() == GameComponent.GcType.VISUAL_TEXTURE);
        assertTrue(((VisualTextureComponent) subject).getTexture() == im);
        assertTrue((Image)(subject.getData()) == im);
        assertTrue(((VisualTextureComponent) subject).getRenderPlane() == r);
        assertTrue(((VisualTextureComponent) subject).getWorldPosRef() == hb);
        assertTrue(((VisualTextureComponent) subject).getLayer() == 1);
        ((VisualTextureComponent) subject).setLayerVal(2);
        assertTrue(((VisualTextureComponent) subject).getLayer() == 2);
    }

    @Test
    public void test1e() {
        subject = new HpComponent(10);
        assertTrue(subject.getType() == GameComponent.GcType.HITPOINT);
        assertTrue(((HpComponent)subject).isAlive());
        assertTrue(!((HpComponent)subject).takeDmg(10));
        ((HpComponent)subject).setHP(5);
        assertTrue(((HpComponent)subject).isAlive());
        assertTrue(((Integer)subject.getData()).equals(5));
    }

    @Test
    public void test2() {
        subject = new PhysicsComponent(0.0, 1.0, 1.0, 1.0);
        hb = (HitboxAabb) subject.getData();
        assertTrue(hb.getLeft() == 0.0);
        assertTrue(hb.getRight() == 1.0);
        assertTrue(hb.getTop() == 1.0);
        assertTrue(hb.getBottom() == 0.0);
        assertTrue(((PhysicsComponent)subject).canBeMoved());
        assertTrue(!((PhysicsComponent)subject).updatedByEngine());
        assertTrue(((PhysicsComponent)subject).getMass() == 1.0);
        assertTrue(((PhysicsComponent)subject).affectedByGravity());
    }

    @Test
    public void test3() {
        subject = new PhysicsComponent(0.0, 1.0, 1.0, 1.0, 2.0, false);
        hb = (HitboxAabb) subject.getData();
        assertTrue(hb.getLeft() == 0.0);
        assertTrue(hb.getRight() == 1.0);
        assertTrue(hb.getTop() == 1.0);
        assertTrue(hb.getBottom() == 0.0);
        assertTrue(((PhysicsComponent)subject).canBeMoved());
        assertTrue(!((PhysicsComponent)subject).updatedByEngine());
        assertTrue(((PhysicsComponent)subject).getMass() == 2.0);
        assertTrue(!((PhysicsComponent)subject).affectedByGravity());
    }

    @Test
    public void test4() {
        subject = new PhysicsComponent(0.0, 1.0, 1.0, 1.0, -2.0, true);
        hb = (HitboxAabb) subject.getData();
        assertTrue(hb.getLeft() == 0.0);
        assertTrue(hb.getRight() == 1.0);
        assertTrue(hb.getTop() == 1.0);
        assertTrue(hb.getBottom() == 0.0);
        assertTrue(!((PhysicsComponent)subject).canBeMoved());
        assertTrue(!((PhysicsComponent)subject).updatedByEngine());
        assertTrue(((PhysicsComponent)subject).getMass() == -2.0);
        assertTrue(((PhysicsComponent)subject).affectedByGravity());
    }

    @Test
    public void test5() {
        subject = new PhysicsComponent(new HitboxAabb(0.0, 1.0, 1.0, 0.0));
        hb = (HitboxAabb) subject.getData();
        assertTrue(hb.getLeft() == 0.0);
        assertTrue(hb.getRight() == 1.0);
        assertTrue(hb.getTop() == 1.0);
        assertTrue(hb.getBottom() == 0.0);
        assertTrue(((PhysicsComponent)subject).canBeMoved());
        assertTrue(!((PhysicsComponent)subject).updatedByEngine());
        assertTrue(((PhysicsComponent)subject).getMass() == 1.0);
        assertTrue(((PhysicsComponent)subject).affectedByGravity());
    }

    @Test
    public void test6() {
        subject = new PhysicsComponent(new HitboxAabb(0.0, 1.0, 1.0, 0.0), -1.0, false);
        hb = (HitboxAabb) subject.getData();
        assertTrue(hb.getLeft() == 0.0);
        assertTrue(hb.getRight() == 1.0);
        assertTrue(hb.getTop() == 1.0);
        assertTrue(hb.getBottom() == 0.0);
        assertTrue(!((PhysicsComponent)subject).canBeMoved());
        assertTrue(!((PhysicsComponent)subject).updatedByEngine());
        assertTrue(((PhysicsComponent)subject).getMass() == -1.0);
        assertTrue(!((PhysicsComponent)subject).affectedByGravity());
    }

    @Test
    public void test7() {
        subject = new PhysicsComponent(new HitboxAabb(0.0, 1.0, 1.0, 0.0), 50.0, true);
        hb = (HitboxAabb) subject.getData();
        assertTrue(hb.getLeft() == 0.0);
        assertTrue(hb.getRight() == 1.0);
        assertTrue(hb.getTop() == 1.0);
        assertTrue(hb.getBottom() == 0.0);
        assertTrue(((PhysicsComponent)subject).canBeMoved());
        assertTrue(!((PhysicsComponent)subject).updatedByEngine());
        assertTrue(((PhysicsComponent)subject).getMass() == 50.0);
        assertTrue(((PhysicsComponent)subject).affectedByGravity());
    }

    @Test
    public void test8() {
        subject = new PhysicsComponent(new HitboxAabb(0.0, 1.0, 1.0, 0.0), 50.0, true);
        ((PhysicsComponent)subject).setVelX(1.0);
        ((PhysicsComponent)subject).setVelY(2.0);
        assertTrue(((PhysicsComponent)subject).getVelX() == 1.0);
        assertTrue(((PhysicsComponent)subject).getVelY() == 2.0);
        ((PhysicsComponent)subject).addVelX(3.0);
        ((PhysicsComponent)subject).addVelY(4.0);
        assertTrue(((PhysicsComponent)subject).getVelX() == 4.0);
        assertTrue(((PhysicsComponent)subject).getVelY() == 6.0);
    }
}
