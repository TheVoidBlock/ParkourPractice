package io.github.thevoidblock.parkourpractice;

import io.github.thevoidblock.parkourpractice.fakes.FakePlayerEntity;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientWorldEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayConnectionEvents;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.input.Input;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.entity.Entity;
import net.minecraft.util.Identifier;
import org.lwjgl.glfw.GLFW;

import java.util.Objects;

public class ParkourPractice implements ClientModInitializer {
    public static final String MOD_ID = "parkourpractice";
    public static final KeyBinding activateBinding = KeyBindingHelper.registerKeyBinding(new KeyBinding("key.parkourpractice.activate", GLFW.GLFW_KEY_F4, new KeyBinding.Category(Identifier.of(MOD_ID, "main"))));

    public static FakePlayerEntity FAKE_PLAYER;
    public static Input INPUT;
    public static boolean ENABLED = false;

    @Override
    public void onInitializeClient() {
        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            if(activateBinding.wasPressed()) {
                if(ENABLED) disable(client);
                else enable(client);
            }
        });

        ClientPlayConnectionEvents.DISCONNECT.register((networkHandler, client) -> disable(client));
        ClientWorldEvents.AFTER_CLIENT_WORLD_CHANGE.register((client, world) -> disable(client));
    }

    private void enable(MinecraftClient client) {
        if(ENABLED) return;

        Objects.requireNonNull(client.player);
        Objects.requireNonNull(client.world);

        ENABLED = true;

        INPUT = client.player.input;
        client.player.input = new Input();

        FAKE_PLAYER = new FakePlayerEntity(client, INPUT);
        client.world.addEntity(FAKE_PLAYER);
        FAKE_PLAYER.refreshPositionAndAngles(client.player.getEntityPos(), client.player.getYaw(), client.player.getPitch());
    }

    private void disable(MinecraftClient client) {
        if(!ENABLED) return;

        Objects.requireNonNull(client.player);

        ENABLED = false;

        ((ClientWorld) FAKE_PLAYER.getEntityWorld()).removeEntity(FAKE_PLAYER.getId(), Entity.RemovalReason.DISCARDED);
        FAKE_PLAYER = null;

        client.player.input = INPUT;
    }
}
