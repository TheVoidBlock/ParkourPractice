package io.github.thevoidblock.parkourpractice.mixin;

import dev.tr7zw.transition.mc.GeneralUtil;
import io.github.thevoidblock.parkourpractice.ParkourPractice;
import net.minecraft.entity.Entity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Pseudo;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import static io.github.thevoidblock.parkourpractice.ParkourPractice.FAKE_PLAYER;

@Pseudo
@Mixin(GeneralUtil.class)
public class GeneralUtilMixin {
    @Inject(method = "getCameraEntity", at = @At("HEAD"), cancellable = true)
    private static void getCameraEntity(CallbackInfoReturnable<Entity> cir) {
        if(ParkourPractice.ENABLED) cir.setReturnValue(FAKE_PLAYER);
    }
}
