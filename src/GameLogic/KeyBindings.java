package GameLogic;

import Data.Communication.GameEventRequest;
import Data.Communication.GameScript;

import java.awt.event.KeyEvent;
import java.util.HashMap;

public class KeyBindings {
    private static HashMap<Integer, GameScript> hm_kgs_clickBindings = new HashMap<Integer, GameScript>();

    /*
    this method must be called before the keybindings can be used!

    populates the hashmap to use the key bindings
     */
    public static void createKeyBindings() {
        hm_kgs_clickBindings.put(KeyEvent.VK_SPACE, new GameEventRequest("JumpPlayer"));
        hm_kgs_clickBindings.put(KeyEvent.VK_CONTROL, new GameEventRequest("CrouchPlayer"));
        hm_kgs_clickBindings.put(KeyEvent.VK_ESCAPE, new GameEventRequest("TogglePause"));
    }

    /*
    runs HashMap.get() on the input
     */
    public static GameScript getClickBindingFor(Integer i_input) {
        return hm_kgs_clickBindings.get(i_input);
    }
}
