package com.karahanbuhan.mods.bloodparticles.mixin.event;

import com.karahanbuhan.mods.bloodparticles.api.event.DamageLivingEntityCallback;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.util.ActionResult;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

/**
 * Mixin class for injecting {@link DamageLivingEntityCallback} into LivingEntity on damage()
 */
@Mixin(LivingEntity.class)
public class DamageLivingEntityMixin {
    @Inject(method = "damage(Lnet/minecraft/entity/damage/DamageSource;F)Z",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/LivingEntity;applyDamage(Lnet/minecraft/entity/damage/DamageSource;F)V"),
            cancellable = true)
    private void onDamageLivingEntity(DamageSource source, float amount, final CallbackInfoReturnable<Boolean> info) {
        ActionResult result = DamageLivingEntityCallback.EVENT.invoker().interact((LivingEntity) (Object) this, source, amount);

        if (result == ActionResult.FAIL) {
            info.cancel();
        }
    }
}
