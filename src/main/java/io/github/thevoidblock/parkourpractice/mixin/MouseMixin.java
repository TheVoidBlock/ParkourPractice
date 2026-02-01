package io.github.thevoidblock.parkourpractice.mixin;

import io.github.thevoidblock.parkourpractice.ParkourPractice;
import net.minecraft.client.Mouse;
import net.minecraft.client.network.ClientPlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(Mouse.class)
public class MouseMixin {
    @Redirect(method = "updateMouse", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/network/ClientPlayerEntity;changeLookDirection(DD)V"))
    public void changeLookDirection(ClientPlayerEntity instance, double cursorDeltaX, double cursorDeltaY) {
        if(ParkourPractice.ENABLED) ParkourPractice.GHOST_PLAYER.changeLookDirection(cursorDeltaX, cursorDeltaY);
        else instance.changeLookDirection(cursorDeltaX, cursorDeltaY);
    }
}
