package test;

import main.model.data.GameObject;
import main.model.data.GameScene;
import main.model.data.structure.*;
import main.model.data.structure.TextComponent;
import main.model.game0logic.*;
import main.model.utility.HitboxAabb;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.Vector;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class GameLogicMiscTest {

    @BeforeEach
    public void before() {

    }

    @Test
    public void test1() { //test key bindings
        KeyBindings.createKeyBindings();
        assertTrue(KeyBindings.getClickBindingFor(KeyEvent.VK_ESCAPE).getData().equals("TogglePause"));
        assertTrue(KeyBindings.getClickBindingFor(KeyEvent.VK_SPACE).getData().equals("JumpPlayer"));
        assertTrue(KeyBindings.getHoldBindingsFor(KeyEvent.VK_CONTROL).getData().equals("CrouchPlayer"));
    }

    @Test
    public void test2() { //ReplayButton test
        ReplayButton.setDefaultTexture(null);
        ReplayButton subject = new ReplayButton();
        assertTrue(subject.findFirstActiveComponentInObj(GameComponent.GcType.VISUAL_TEXTURE).getData() == null);
        UiComponent uic = (UiComponent) subject.findFirstActiveComponentInObj(GameComponent.GcType.UI);
        uic.press();
        assertTrue(subject.isPressed());
        assertTrue(subject.getPressedAndReset());
        assertTrue(!subject.isPressed());
        assertTrue(!subject.getPressedAndReset());
    }

    @Test
    public void test3() { //LandSharkText test
        LandSharkText subject = new LandSharkText();
        subject.setText("Blue birD");
        TextComponent tc = (TextComponent)subject.findFirstActiveComponentInObj(GameComponent.GcType.TEXT);
        assertTrue(tc.getData().equals("Blue birD"));
        assertTrue(tc.getColor().equals(Color.black));
        assertTrue(tc.getFont().equals(new Font("Impact", 0, 30)));
        subject.setText("new");
        assertTrue(tc.getData().equals("new"));
    }

    @Test
    public void test4() { //GameEnemy test
        VisualAnimationComponent vac = new VisualAnimationComponent(0, null, null);
        DroneEnemy.setDefaultAnimation(vac);
        GameEnemy subject = new DroneEnemy(1.0);
        SpiderEnemy.setDefaultAnimation(vac);
        GameEnemy subject2 = new SpiderEnemy(2.0);
        vac = (VisualAnimationComponent) subject.findFirstActiveComponentInObj(GameComponent.GcType.VISUAL_ANIM);
        assertTrue(vac.getTexture() == null);
        vac = (VisualAnimationComponent) subject2.findFirstActiveComponentInObj(GameComponent.GcType.VISUAL_ANIM);
        assertTrue(vac.getTexture() == null);
        test4secondPart(new GameScene(), new Vector<GameComponent>(), subject, subject2);
    }

    //tests the updateObj() in both children of GameEnemy
    public void test4secondPart(GameObject tester, Vector<GameComponent> someVector,
                                GameEnemy subject, GameEnemy subject2) {
        tester.addGameObject(subject);
        tester.addGameObject(subject2);
        tester.compileComponentList(someVector, GameComponent.GcType.PHYSICS);
        assertTrue(someVector.size() == 2);
        someVector.clear();
        PhysicsComponent pc = (PhysicsComponent)subject.findFirstActiveComponentInObj(GameComponent.GcType.PHYSICS);
        HitboxAabb hb = (HitboxAabb)pc.getData();
        hb.moveX(-hb.getRight() - 1.1);
        tester.updateObj();
        tester.compileComponentList(someVector, GameComponent.GcType.PHYSICS);
        assertTrue(someVector.size() == 1);
        someVector.clear();
        pc = (PhysicsComponent)subject2.findFirstActiveComponentInObj(GameComponent.GcType.PHYSICS);
        hb = (HitboxAabb)pc.getData();
        hb.moveX(-hb.getRight() - 1.1);
        tester.updateObj();
        tester.compileComponentList(someVector, GameComponent.GcType.PHYSICS);
        assertTrue(someVector.size() == 0);
    }

    @Test
    public void test5() { //LandSharkPlayer ground and crouch testing
        VisualAnimationComponent vac = new VisualAnimationComponent(0, null, null);
        LandSharkPlayer.setDefaultAnimation(vac);
        LandSharkPlayer subject = new LandSharkPlayer();

        subject.setTouchingGroundTrue();
        subject.updateObj();
        assertTrue(subject.isTouchingGround());
        assertTrue(!subject.isCrouching());

        subject.updateObj();
        subject.inputResponse("CrouchPlayer");
        assertTrue(!subject.isTouchingGround());
        subject.updateObj();
        assertTrue(!subject.isTouchingGround());
        assertTrue(!subject.isCrouching());

        test5secondPart(subject);
    }

    public void test5secondPart(LandSharkPlayer subject) {
        subject.setTouchingGroundTrue();
        subject.updateObj();
        subject.inputResponse("CrouchPlayer");
        subject.setTouchingGroundTrue();
        subject.updateObj();
        assertTrue(subject.isTouchingGround());
        assertTrue(subject.isCrouching());
    }

    @Test
    public void test5b() { //LandSharkPlayer input test
        VisualAnimationComponent vac = new VisualAnimationComponent(0, null, null);
        LandSharkPlayer.setDefaultAnimation(vac);
        LandSharkPlayer subject = new LandSharkPlayer();

        subject.inputResponse("JumpPlayer");
        subject.updateObj();
        assertTrue(((PhysicsComponent)subject.findFirstActiveComponentInObj(
                        GameComponent.GcType.PHYSICS)).getVelY() == 0.0);
        subject.setTouchingGroundTrue();
        subject.updateObj();
        subject.inputResponse("JumpPlayer");
        subject.updateObj();
        assertTrue(((PhysicsComponent)subject.findFirstActiveComponentInObj(
                GameComponent.GcType.PHYSICS)).getVelY() > 0.0);

        test5bsecondPart(subject);
    }

    public void test5bsecondPart(LandSharkPlayer subject) {
        subject.setTouchingGroundTrue();
        subject.updateObj();
        subject.setTouchingGroundTrue();
        subject.inputResponse("CrouchPlayer");
        subject.updateObj();
        assertTrue(subject.isCrouching());
    }
}
