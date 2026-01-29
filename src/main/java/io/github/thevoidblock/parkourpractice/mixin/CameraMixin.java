package io.github.thevoidblock.parkourpractice.mixin;

import io.github.thevoidblock.parkourpractice.ParkourPractice;
import net.minecraft.client.render.Camera;
import net.minecraft.entity.Entity;
import net.minecraft.world.BlockView;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import static io.github.thevoidblock.parkourpractice.ParkourPractice.FAKE_PLAYER;

@Mixin(Camera.class)
public abstract class CameraMixin {
    @Shadow
    private Entity focusedEntity;

    @Shadow public abstract void update(BlockView area, Entity focusedEntity, boolean thirdPerson, boolean inverseView, float tickProgress);

    @Inject(method = "update", at = @At("HEAD"), cancellable = true)
    public void update(BlockView area, Entity focusedEntity, boolean thirdPerson, boolean inverseView, float tickProgress, CallbackInfo ci) {
        if(!ParkourPractice.ENABLED || focusedEntity == FAKE_PLAYER) return;
        update(area, FAKE_PLAYER, thirdPerson, inverseView, tickProgress);
        this.focusedEntity = focusedEntity;
        ci.cancel();
    }

    @Redirect(method = "updateEyeHeight", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/Entity;getStandingEyeHeight()F"))
    public float updateEyeHeight(Entity instance) {
        if(ParkourPractice.ENABLED) return FAKE_PLAYER.getStandingEyeHeight();
        return instance.getStandingEyeHeight();
    }
}
