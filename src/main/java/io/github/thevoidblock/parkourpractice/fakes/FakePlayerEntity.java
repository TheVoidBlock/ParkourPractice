package io.github.thevoidblock.parkourpractice.fakes;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerEntity;

import java.util.Objects;

public class FakePlayerEntity extends ClientPlayerEntity {
    public FakePlayerEntity(MinecraftClient client) {
        super(
                client,
                Objects.requireNonNull(client.world),
                new FakeNetworkHandler(client, Objects.requireNonNull(client.getNetworkHandler())),
                Objects.requireNonNull(client.player).getStatHandler(),
                client.player.getRecipeBook(),
                client.player.getLastPlayerInput(),
                false
        );

        ClientPlayerEntity player = client.player;
        this.input = player.input;
        this.refreshPositionAndAngles(player.getEntityPos(), player.getYaw(), player.getPitch());
        this.setVelocity(player.getVelocity());
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

        this.getHungerManager().setSaturationLevel(player.getHungerManager().getSaturationLevel());
        this.getHungerManager().setFoodLevel(player.getHungerManager().getFoodLevel());
    }

    @Override
    public boolean isCamera() {
        return true;
    }

    @Override
    public boolean isSpectator() {
        return this.noClip;
    }
}
