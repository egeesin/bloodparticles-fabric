package com.karahanbuhan.mods.bloodparticles.api.event;

import com.karahanbuhan.mods.bloodparticles.mixin.ClientAttackEntityMixin;
import com.karahanbuhan.mods.bloodparticles.mixin.DamageLivingEntityMixin;
import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;

/**
 * Called when any LivingEntity has been damaged
 *
 * @see DamageLivingEntityMixin
 * @see ClientAttackEntityMixin
 */
public interface DamageLivingEntityCallback {
    Event<DamageLivingEntityCallback> EVENT = EventFactory.createArrayBacked(DamageLivingEntityCallback.class,
            (listeners) -> (entity, source, amount) -> {
                for (DamageLivingEntityCallback listener : listeners)
                    listener.interact(entity, source, amount);
            });

    /**
     * @param entity The living entity which has been damaged
     * @param source The cause of the damage
     * @param amount The raw amount of damage caused by the event
     */
    void interact(final LivingEntity entity, final DamageSource source, final float amount);
}