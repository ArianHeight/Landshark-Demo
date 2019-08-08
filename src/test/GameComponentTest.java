package test;

import model.data.structure.*;
import model.data.structure.TextComponent;
import model.utility.HitboxAabb;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.swing.*;
import java.awt.*;
import java.util.Vector;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class GameComponentTest {
    GameComponent subject;
    HitboxAabb hb;

    @BeforeEach
    public void before() {

    }

    @Test
    public void test1() { //physics comp cstr test
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
    public void test1b() { //physics comp cstr test
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
    public void test1c() { //visual texture comp cstr test
        Image im = new ImageIcon("./Game/Assets/Textures/zombieKnight.png").getImage();
        Rectangle r = new Rectangle(0, 0, 1, 1);
        subject = new VisualTextureComponent(im, r);
        VisualComponent vc = (VisualTextureComponent)subject;
        assertTrue(subject.getType() == GameComponent.GcType.VISUAL_TEXTURE);
        assertTrue(vc.getTexture() == im);
        assertTrue((Image)(subject.getData()) == im);
        assertTrue(vc.getRenderPlane() == r);
    }

    @Test
    public void test1d() { //visual texture comp cstr test
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
    public void test1e() { //hp comp test
        subject = new HpComponent(10);
        assertTrue(subject.getType() == GameComponent.GcType.HITPOINT);
        assertTrue(((HpComponent)subject).isAlive());
        assertTrue(!((HpComponent)subject).takeDmg(10));
        ((HpComponent)subject).setHP(5);
        assertTrue(((HpComponent)subject).isAlive());
        assertTrue(((Integer)subject.getData()).equals(5));
    }

    @Test
    public void test1f() { //animation component cstr test?
        subject = new VisualAnimationComponent(0.1, null, null);
        assertTrue(subject.getType() == GameComponent.GcType.VISUAL_ANIM);
        VisualComponent vc = (VisualAnimationComponent)subject;
        assertTrue(vc.getLayer() == 1);
        vc.setLayerVal(0);
        assertTrue(vc.getLayer() == 0);
        assertTrue(vc.getTexture() == null);
        assertTrue(vc.getWorldPosRef() == null);
        assertTrue(vc.getRenderPlane() == null);
    }

    @Test
    public void test1g() { //invalid framepause animation comp cstr
        subject = new VisualAnimationComponent(0, null, null);
        assertTrue(subject.getType() == GameComponent.GcType.VISUAL_ANIM);
        Image im = new ImageIcon("./Game/Assets/Textures/zombieKnight.png").getImage();
        ((VisualAnimationComponent)subject).addSprite(im);
        Image im2 = new ImageIcon("./Game/Assets/Textures/zombieKnight.png").getImage();
        ((VisualAnimationComponent)subject).addSprite(im2);
        assertTrue(((VisualAnimationComponent)subject).getCurrentSprite(0.1) == im);
        assertTrue(((VisualAnimationComponent)subject).getCurrentSprite(1.0) == im2);
        assertTrue(((VisualAnimationComponent)subject).getCurrentSprite(1.1) == im);
        assertTrue(((Vector<Image>)subject.getData()).size() == 2);
    }

    @Test
    public void test1h() { //uiComponent cstr & method test
        subject = new UiComponent(null);
        assertTrue(subject.getType() == GameComponent.GcType.UI);
        assertTrue(subject.getData() == null);
        assertTrue(((UiComponent)subject).getPressedState() == false);
        ((UiComponent)subject).press();
        assertTrue(((UiComponent)subject).getPressedState() == true);
        ((UiComponent)subject).press();
        assertTrue(((UiComponent)subject).getPressedState() == true);
        ((UiComponent)subject).resetState();
        assertTrue(((UiComponent)subject).getPressedState() == false);
    }

    @Test
    public void test1i() { //text component test
        Font font = new Font("arial", 0, 12);
        subject = new TextComponent("hi", font, 15, 20, Color.black);
        assertTrue(((TextComponent)subject).getFont() == font);
        assertTrue(((TextComponent)subject).getData().equals("hi"));
        assertTrue(((TextComponent)subject).getColor().equals(Color.black));
        assertTrue(((TextComponent)subject).getX() == 15);
        assertTrue(((TextComponent)subject).getY() == 20);
    }

    @Test
    public void test2() { //physics comp method test
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
    public void test3() { //phsics comp method test 2
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
    public void test4() { //physics comp method test 3
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
    public void test5() { //physics comp method test 4
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
    public void test6() { //physics comp method test 4
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
    public void test7() { //physics comp method test 5
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
    public void test8() { //physisc comp velocity test
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
