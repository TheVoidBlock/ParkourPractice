package io.github.thevoidblock.parkourpractice;

import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import org.lwjgl.glfw.GLFW;

import static io.github.thevoidblock.parkourpractice.ParkourPractice.*;

public class KeyBindings {
    private static final KeyBinding.Category CATEGORY = new KeyBinding.Category(Identifier.of(MOD_ID, "main"));

    public static final KeyBinding ACTIVATE = registerKeybind("activate", GLFW.GLFW_KEY_F4);
    public static final KeyBinding RESET = registerKeybind("reset", GLFW.GLFW_KEY_F6);
    public static final KeyBinding FLIGHT = registerKeybind("flight", GLFW.GLFW_KEY_UNKNOWN);
    public static final KeyBinding NO_CLIP = registerKeybind("no_clip", GLFW.GLFW_KEY_UNKNOWN);

    public static void register() {
        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            if(client.player == null) return;

            if(ACTIVATE.wasPressed()) {
                if(ENABLED) ParkourPractice.disable(client);
                else ParkourPractice.enable(client);
                ParkourPractice.sendToggleNotification(client, "ghost-player", ENABLED);
            }

            if(RESET.wasPressed() && ENABLED) {
                ParkourPractice.disable(client);
                ParkourPractice.enable(client);
                client.player.sendMessage(Text.translatable("text.parkourpractice.reset"), true);
            }

            if(FLIGHT.wasPressed() && ENABLED) {
                boolean flying = GHOST_PLAYER.getAbilities().allowFlying = !GHOST_PLAYER.getAbilities().allowFlying;
                GHOST_PLAYER.getAbilities().flying &= flying;
                ParkourPractice.sendToggleNotification(client, "flight", flying);
            }

            if(NO_CLIP.wasPressed() && ENABLED) {
                GHOST_PLAYER.noClip = !GHOST_PLAYER.noClip;
                ParkourPractice.sendToggleNotification(client, "no_clip", GHOST_PLAYER.noClip);
            }
        });
    }

    private static KeyBinding registerKeybind(String name, int key) {
        return KeyBindingHelper.registerKeyBinding(new KeyBinding("key.parkourpractice." + name, key, CATEGORY));
    }
}
