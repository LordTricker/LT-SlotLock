package pl.lordtricker.ltsl.client.keybinding;

import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.option.KeyBinding;
import org.lwjgl.glfw.GLFW;
import pl.lordtricker.ltsl.client.util.ColorUtils;
import pl.lordtricker.ltsl.client.util.Messages;

public class Toggling {
    public static KeyBinding toggleFilterKey;
    public static boolean slotsEnabled = true;

    public static void init() {
        toggleFilterKey = KeyBindingHelper.registerKeyBinding(new KeyBinding(
                "Włączenie blokady slotów",
                GLFW.GLFW_KEY_G,
                "LT-Mods binds"
        ));

        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            // Obsługa wciśnięcia klawisza (CTRL+S)
            while (toggleFilterKey.wasPressed()) {
                long window = client.getWindow().getHandle();
                boolean ctrlPressed = GLFW.glfwGetKey(window, GLFW.GLFW_KEY_LEFT_CONTROL) == GLFW.GLFW_PRESS
                        || GLFW.glfwGetKey(window, GLFW.GLFW_KEY_RIGHT_CONTROL) == GLFW.GLFW_PRESS;
                if (!ctrlPressed) {
                    continue;
                }
                slotsEnabled = !slotsEnabled;
                String msgKey = slotsEnabled ? "command.slotlock.toggle.on" : "command.slotlock.toggle.off";
                String msg = Messages.get(msgKey);
                if (client.player != null) {
                    client.player.sendMessage(ColorUtils.translateColorCodes(msg), false);
                }
            }
        });
    }
}
