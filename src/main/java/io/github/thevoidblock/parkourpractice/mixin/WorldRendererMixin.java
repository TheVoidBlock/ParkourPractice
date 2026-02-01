package io.github.thevoidblock.parkourpractice.mixin;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.llamalad7.mixinextras.sugar.Local;
import io.github.thevoidblock.parkourpractice.ParkourPractice;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.WorldRenderer;
import net.minecraft.entity.Entity;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;

import static io.github.thevoidblock.parkourpractice.ParkourPractice.GHOST_PLAYER;

@Mixin(WorldRenderer.class)
public class WorldRendererMixin {
    @Shadow @Final private MinecraftClient client;

    @ModifyExpressionValue(method = "fillEntityRenderStates", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/render/Camera;getFocusedEntity()Lnet/minecraft/entity/Entity;"))
    private Entity fakeFocusedEntity(Entity original, @Local Entity entity) {
        return ParkourPractice.ENABLED && (entity == client.player || entity == GHOST_PLAYER) ? entity : original;
    }

    @ModifyExpressionValue(method = "fillEntityRenderStates", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/render/Camera;isThirdPerson()Z"))
    private boolean fakeThirdPersonFocusedEntity(boolean original, @Local Entity entity) {
        return original || (ParkourPractice.ENABLED && entity == client.player);
    }
}
