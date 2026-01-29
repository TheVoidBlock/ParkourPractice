package io.github.thevoidblock.parkourpractice.fakes;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.input.Input;
import net.minecraft.client.network.ClientPlayerEntity;

import java.util.Objects;

public class FakePlayerEntity extends ClientPlayerEntity {
    public FakePlayerEntity(MinecraftClient client, Input input) {
        super(
                client,
                Objects.requireNonNull(client.world),
                new FakeNetworkHandler(client, Objects.requireNonNull(client.getNetworkHandler())),
                Objects.requireNonNull(client.player).getStatHandler(),
                client.player.getRecipeBook(),
                client.player.getLastPlayerInput(),
                false
        );

        this.input = input;
        this.remainingLoadTicks = 0;
    }

    @Override
    public void tick() {
        super.tick();

        ClientPlayerEntity player = client.player;
        if (player == null) return;

        this.getInventory().setSelectedSlot(player.getInventory().getSelectedSlot());

        for(int slot = 0; slot < player.getInventory().size(); ++slot) {
            this.getInventory().setStack(slot, player.getInventory().getStack(slot));
        }

        if(isGliding() && !canGlide())
            stopGliding();
    }

    @Override
    public boolean isCamera() {
        return true;
    }
}
