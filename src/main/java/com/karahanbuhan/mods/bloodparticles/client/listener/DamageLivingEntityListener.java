package com.karahanbuhan.mods.bloodparticles.client.listener;

import com.google.common.collect.HashMultimap;
import com.karahanbuhan.mods.bloodparticles.api.event.DamageLivingEntityCallback;
import com.karahanbuhan.mods.bloodparticles.client.BloodParticlesClientMod;
import com.karahanbuhan.mods.bloodparticles.client.ReferenceVariables;
import com.karahanbuhan.mods.bloodparticles.common.config.Configuration;
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

import java.util.HashSet;
import java.util.Map;

import static com.karahanbuhan.mods.bloodparticles.client.ReferenceVariables.*;

/**
 * Listens for damage to living entities and adds blood particles according to the damage source, damage dealt and entity itself
 */
public class DamageLivingEntityListener implements DamageLivingEntityCallback {
    @Override
    public ActionResult interact(LivingEntity entity, DamageSource source, float amount) {
        Configuration config = BloodParticlesClientMod.getConfig();

        if (!getEnabledDamageSources(config).contains(source.name))
            return ActionResult.PASS; // Return if the damage source is not enabled in config

        WorldRenderer renderer = MinecraftClient.getInstance().worldRenderer;
        Vec3d position = entity.getPos();
        ParticleEffect effect = new BlockStateParticleEffect(ParticleTypes.BLOCK, Blocks.REDSTONE_BLOCK.getDefaultState());

        for (int i = 0; i < amount * 15; i++)
            renderer.addParticle(effect, false, position.x, position.y + entity.getHeight() / 1.5, position.z, 0, 0, 0);

        return ActionResult.PASS;
    }

    /**
     * Returns the enabled damage sources in config
     *
     * @param config The configuration for enabled causes
     * @return Set of enabled damage sources
     */
    private HashSet<String> getEnabledDamageSources(Configuration config) {
        HashMultimap<ReferenceVariables, String> map = HashMultimap.create(); // There may be duplicate keys so we use Multimap
        map.put(BLOOD_WHEN_ATTACK, "string");
        map.put(BLOOD_WHEN_ATTACK, "mob");
        map.put(BLOOD_WHEN_ATTACK, "player");
        map.put(BLOOD_WHEN_CACTUS, "cactus");
        map.put(BLOOD_WHEN_CRAMMING, "cramming");
        map.put(BLOOD_WHEN_CUSTOM_DAMAGE, "generic"); // Not sure about this one...
        map.put(BLOOD_WHEN_DRAGON_BREATH, "dragonBreath");
        map.put(BLOOD_WHEN_DROWNING, "drown");
        map.put(BLOOD_WHEN_DRYOUT, "dryout");
        map.put(BLOOD_WHEN_EXPLOSION, "explosion");
        map.put(BLOOD_WHEN_EXPLOSION, "explosion.player");
        map.put(BLOOD_WHEN_FALL, "fall");
        map.put(BLOOD_WHEN_FIRE, "inFire");
        map.put(BLOOD_WHEN_FIRE, "onFire");
        map.put(BLOOD_WHEN_FLY_INTO_WALL, "flyIntoWall");
        map.put(BLOOD_WHEN_HOT_FLOOR, "hotFloor");
        map.put(BLOOD_WHEN_LAVA, "lava");
        map.put(BLOOD_WHEN_LIGHTNING, "lightningBolt");
        map.put(BLOOD_WHEN_MAGIC, "magic");
        map.put(BLOOD_WHEN_POISON, "poison");
        map.put(BLOOD_WHEN_PROJECTILE, "arrow");
        map.put(BLOOD_WHEN_PROJECTILE, "trident");
        map.put(BLOOD_WHEN_PROJECTILE, "fireworks");
        map.put(BLOOD_WHEN_PROJECTILE, "fireball");
        map.put(BLOOD_WHEN_PROJECTILE, "witherSkull");
        map.put(BLOOD_WHEN_PROJECTILE, "thrown");
        map.put(BLOOD_WHEN_PROJECTILE, "indirectMagic");
        map.put(BLOOD_WHEN_STARVATION, "starve");
        map.put(BLOOD_WHEN_SUFFOCATION, "fallingBlock");
        map.put(BLOOD_WHEN_THORNS, "thorns");
        map.put(BLOOD_WHEN_VOID, "outOfWorld");
        map.put(BLOOD_WHEN_WITHER, "wither");

        HashSet<String> enabledDamageSources = new HashSet<>();
        for (Map.Entry<ReferenceVariables, String> entry : map.entries())
            if ((Boolean) config.getFieldByName(entry.getKey().name).getValue())
                enabledDamageSources.add(entry.getValue()); // If the reference is enabled, add it's value which are in map object

        return enabledDamageSources;
    }
}
