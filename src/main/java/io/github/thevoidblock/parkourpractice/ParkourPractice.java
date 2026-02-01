package io.github.thevoidblock.parkourpractice;

import io.github.thevoidblock.parkourpractice.fakes.FakePlayerEntity;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientWorldEvents;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayConnectionEvents;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.input.Input;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.entity.Entity;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Objects;

public class ParkourPractice implements ClientModInitializer {
    public static final String MOD_ID = "parkourpractice";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

    public static FakePlayerEntity GHOST_PLAYER;
    public static boolean ENABLED = false;

    @Override
    public void onInitializeClient() {
        KeyBindings.register();

        ClientPlayConnectionEvents.DISCONNECT.register((networkHandler, client) -> disable(client));
        ClientWorldEvents.AFTER_CLIENT_WORLD_CHANGE.register((client, world) -> disable(client));

        LOGGER.info("{} initialized", MOD_ID);
    }

    protected static void sendToggleNotification(MinecraftClient client, String module, boolean value) {
        Objects.requireNonNull(client.player).sendMessage(
                Text.translatable("text.parkourpractice." + module)
                        .append(" ")
                        .append((value ?
                                Text.translatable("text.parkourpractice.on") :
                                Text.translatable("text.parkourpractice.off"))
                                .formatted(value ? Formatting.GREEN : Formatting.RED)),
                true
        );
    }

    protected static void enable(MinecraftClient client) {
        if(ENABLED) return;

        Objects.requireNonNull(client.player);
        Objects.requireNonNull(client.world);

        ENABLED = true;

        GHOST_PLAYER = new FakePlayerEntity(client);
        client.world.addEntity(GHOST_PLAYER);

        client.player.input = new Input();
    }

    protected static void disable(MinecraftClient client) {
        if(!ENABLED) return;

        Objects.requireNonNull(client.player);

        ENABLED = false;

        ((ClientWorld) GHOST_PLAYER.getEntityWorld()).removeEntity(GHOST_PLAYER.getId(), Entity.RemovalReason.DISCARDED);

        client.player.input = GHOST_PLAYER.input;
        GHOST_PLAYER = null;
    }
}
