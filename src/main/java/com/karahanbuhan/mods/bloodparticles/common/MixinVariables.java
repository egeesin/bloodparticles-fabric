package com.karahanbuhan.mods.bloodparticles.common;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;

import java.time.Instant;

/**
 * Represents mostly static variables needed for mixin
 */
public class MixinVariables {
    public static AttackData lastAttackData; // Required for communication between damage listeners

    public static class AttackData {
        public final PlayerEntity player;
        public final LivingEntity target;
        public final long attackTimestamp;
        public final float amount;

        public boolean handled = false;

        public AttackData(PlayerEntity player, LivingEntity target, long attackTimestamp, float amount) {
            this.player = player;
            this.target = target;
            this.attackTimestamp = attackTimestamp;
            this.amount = amount;
        }

        public boolean isOutdated() {
            return (Instant.now().getEpochSecond() - attackTimestamp > 1);
        }
    }
}
