package com.karahanbuhan.mods.bloodparticles.client.listener;

import com.karahanbuhan.mods.bloodparticles.api.event.DamageLivingEntityCallback;
import com.karahanbuhan.mods.bloodparticles.client.BloodParticlesClientMod;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.WorldRenderer;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.particle.ParticleEffect;
import net.minecraft.util.math.Vec3d;

/**
 * Listens for damage to living entities and adds blood particles according to the damage source, damage dealt and entity itself
 */
public class DamageLivingEntityListener implements DamageLivingEntityCallback {
    @Override
    public void interact(LivingEntity entity, DamageSource source, float amount) {
        if (amount <= 0.0f)
            return; // Return if the damage is less than 0

        if (!BloodParticlesClientMod.isEnabled())
            return; // Return if the particles are not enabled

        if (!BloodParticlesClientMod.isDamageSourceEnabled(source.name))
            return; // Return if the damage source is not enabled in config

        MinecraftClient client = MinecraftClient.getInstance();

        // Did you know that dust particles would crash the game in this line?
        ParticleEffect particleEffect = BloodParticlesClientMod.getBloodParticleEffect(entity.getType());

        // Store particle amount so we do not break the world renderer
        double particleAmount = Integer.parseInt(client.particleManager.getDebugString());

        // If multiplier times amount exceeds limit, use the particle limit - total particles
        double bloodParticleAmount = BloodParticlesClientMod.getBloodMultiplier() * amount;

        // Hard coded vanilla particle limit is 16384 so if config value exceeds it, use it instead
        double particleLimit = BloodParticlesClientMod.getParticleLimit();
        if (particleAmount + bloodParticleAmount >= (particleLimit >= 16384 ? 16384 : particleLimit))
            return; // Return if particles will exceed the limit

        WorldRenderer renderer = MinecraftClient.getInstance().worldRenderer;
        Vec3d pos = entity.getPos().add(0, entity.getHeight() / 1.5, 0); // We add to y for almost centering blood vertically

        for (int i = 0; i < bloodParticleAmount; i++)
            renderer.addParticle(particleEffect, false, pos.x, pos.y, pos.z, 0, 0, 0);
    }
}
