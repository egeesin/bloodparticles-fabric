package com.karahanbuhan.mods.bloodparticles.client.listener;

import com.karahanbuhan.mods.bloodparticles.api.event.DamageLivingEntityCallback;
import com.karahanbuhan.mods.bloodparticles.client.BloodParticlesClientMod;
import net.minecraft.block.Blocks;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.WorldRenderer;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.particle.BlockStateParticleEffect;
import net.minecraft.particle.ParticleEffect;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.util.ActionResult;
import net.minecraft.util.math.Vec3d;

/**
 * Listens for damage to living entities and adds blood particles according to the damage source, damage dealt and entity itself
 */
public class DamageLivingEntityListener implements DamageLivingEntityCallback {
    @Override
    public ActionResult interact(LivingEntity entity, DamageSource source, float amount) {
        if (BloodParticlesClientMod.isEnabled())
            return ActionResult.PASS; // Return if the particles are not enabled

        if (!BloodParticlesClientMod.isDamageSourceEnabled(source.name))
            return ActionResult.PASS; // Return if the damage source is not enabled in config

        WorldRenderer renderer = MinecraftClient.getInstance().worldRenderer;
        Vec3d position = entity.getPos();
        ParticleEffect effect = new BlockStateParticleEffect(ParticleTypes.BLOCK, Blocks.REDSTONE_BLOCK.getDefaultState());

        // If multiplier times amount exceeds limit, use the particle limit
        double particleAmount = Math.min(BloodParticlesClientMod.getBloodMultiplier() * amount, BloodParticlesClientMod.getParticleLimit());

        for (int i = 0; i < particleAmount; i++)
            renderer.addParticle(effect, false, position.x, position.y + entity.getHeight() / 1.5, position.z, 0, 0, 0);

        return ActionResult.PASS;
    }
}
