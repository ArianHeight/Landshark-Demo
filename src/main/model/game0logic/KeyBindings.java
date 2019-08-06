package main.model.game0logic;

import main.model.data.communication.GameEventRequest;
import main.model.data.communication.GameScript;

import java.awt.event.KeyEvent;
import java.util.HashMap;

public class KeyBindings {
    private static HashMap<Integer, GameScript> clickBindings = new HashMap<Integer, GameScript>();
    private static HashMap<Integer, GameScript> holdBindings = new HashMap<Integer, GameScript>();

    /*
    this method must be called before the keybindings can be used!

    populates the hashmap to use the key bindings
     */
    public static void createKeyBindings() {
        clickBindings.put(KeyEvent.VK_SPACE, new GameEventRequest("JumpPlayer"));
        clickBindings.put(KeyEvent.VK_ESCAPE, new GameEventRequest("TogglePause"));

        holdBindings.put(KeyEvent.VK_CONTROL, new GameEventRequest("CrouchPlayer"));
    }

    /*
    runs HashMap.get() on the input
     */
    public static GameScript getClickBindingFor(Integer input) {
        return clickBindings.get(input);
    }

    /*
    runs HashMap.get() on the input
     */
    public static GameScript getHoldBindingsFor(Integer input) {
        return holdBindings.get(input);
    }
}
