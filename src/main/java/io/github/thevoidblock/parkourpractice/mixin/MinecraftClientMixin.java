package io.github.thevoidblock.parkourpractice.mixin;

import io.github.thevoidblock.parkourpractice.ParkourPractice;
import net.minecraft.client.MinecraftClient;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(MinecraftClient.class)
public class MinecraftClientMixin {
    @Inject(method = "doAttack", at = @At("HEAD"), cancellable = true)
    public void doAttack(CallbackInfoReturnable<Boolean> cir) {
        if(ParkourPractice.ENABLED) cir.setReturnValue(false);
    }

    @Inject(method = "doItemUse", at = @At("HEAD"), cancellable = true)
    public void doItemUse(CallbackInfo ci) {
        if(ParkourPractice.ENABLED) ci.cancel();
    }

    @Inject(method = "handleBlockBreaking", at = @At("HEAD"), cancellable = true)
    public void handleBlockBreaking(boolean breaking, CallbackInfo ci) {
        if(ParkourPractice.ENABLED) ci.cancel();
    }
}
