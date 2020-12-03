package com.karahanbuhan.mods.bloodparticles.api.event;

import com.karahanbuhan.mods.bloodparticles.mixin.event.DamageLivingEntityMixin;
import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.util.ActionResult;

/**
 * Called when any LivingEntity has been damaged
 *
 * @see DamageLivingEntityMixin
 */
public interface DamageLivingEntityCallback {
    Event<DamageLivingEntityCallback> EVENT = EventFactory.createArrayBacked(DamageLivingEntityCallback.class,
            (listeners) -> (entity, source, amount) -> {
                for (DamageLivingEntityCallback listener : listeners) {
                    ActionResult result = listener.interact(entity, source, amount);

                    if (result != ActionResult.PASS) {
                        return result;
                    }
                }
                
                return ActionResult.PASS;
            });

    /**
     * @param entity The living entity which has been damaged
     * @param source The cause of the damage
     * @param amount The raw amount of damage caused by the event
     * @return Result of the event
     */
    ActionResult interact(final LivingEntity entity, final DamageSource source, final float amount);
}