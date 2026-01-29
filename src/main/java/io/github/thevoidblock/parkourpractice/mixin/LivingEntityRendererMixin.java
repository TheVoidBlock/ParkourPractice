package io.github.thevoidblock.parkourpractice.mixin;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import io.github.thevoidblock.parkourpractice.ParkourPractice;
import net.minecraft.client.render.entity.LivingEntityRenderer;
import net.minecraft.entity.Entity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

import static io.github.thevoidblock.parkourpractice.ParkourPractice.FAKE_PLAYER;

@Mixin(LivingEntityRenderer.class)
public class LivingEntityRendererMixin {
    @ModifyExpressionValue(method = "hasLabel(Lnet/minecraft/entity/LivingEntity;D)Z", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/MinecraftClient;getCameraEntity()Lnet/minecraft/entity/Entity;"))
    protected Entity hasLabel(Entity original) {
        return ParkourPractice.ENABLED ? FAKE_PLAYER : original;
    }
}
