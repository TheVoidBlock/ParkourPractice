package io.github.thevoidblock.parkourpractice.mixin;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.llamalad7.mixinextras.sugar.Local;
import io.github.thevoidblock.parkourpractice.fakes.FakePlayerEntity;
import net.minecraft.entity.Entity;
import net.minecraft.predicate.entity.EntityPredicates;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

import java.util.function.Predicate;

@Mixin(EntityPredicates.class)
public class EntityPredicatesMixin {
    @ModifyExpressionValue(method = "canBePushedBy", at = @At(value = "INVOKE", target = "Ljava/util/function/Predicate;and(Ljava/util/function/Predicate;)Ljava/util/function/Predicate;"))
    private static Predicate<Entity> canBePushedBy(Predicate<Entity> original, @Local   (argsOnly = true) Entity entity) {
        return original.and(ignored -> !(entity instanceof FakePlayerEntity));
    }
}
