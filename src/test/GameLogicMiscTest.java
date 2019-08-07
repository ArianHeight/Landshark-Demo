package test;

import main.model.data.structure.GameComponent;
import main.model.data.structure.TextComponent;
import main.model.data.structure.UiComponent;
import main.model.game0logic.KeyBindings;
import main.model.game0logic.LandSharkText;
import main.model.game0logic.ReplayButton;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.awt.*;
import java.awt.event.KeyEvent;

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
    public void test4() {
        
    }
}
