package com.karahanbuhan.mods.bloodparticles.client.listener;

import com.karahanbuhan.mods.bloodparticles.api.event.DamageLivingEntityCallback;
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

public class DamageLivingEntityListener implements DamageLivingEntityCallback {
    @Override
    public ActionResult interact(LivingEntity entity, DamageSource source, float amount) {
        WorldRenderer renderer = MinecraftClient.getInstance().worldRenderer;
        Vec3d position = entity.getPos();
        ParticleEffect effect = new BlockStateParticleEffect(ParticleTypes.BLOCK, Blocks.REDSTONE_BLOCK.getDefaultState());

        for (int i = 0; i < amount * 15; i++)
            renderer.addParticle(effect, false, position.x, position.y + entity.getHeight() / 1.5, position.z, 0, 0, 0);
        
        return ActionResult.PASS;
    }
}
