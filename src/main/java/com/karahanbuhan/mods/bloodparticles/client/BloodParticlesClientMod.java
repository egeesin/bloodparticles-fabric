package com.karahanbuhan.mods.bloodparticles.client;

import com.google.common.collect.HashMultimap;
import com.karahanbuhan.mods.bloodparticles.api.event.DamageLivingEntityCallback;
import com.karahanbuhan.mods.bloodparticles.client.listener.DamageLivingEntityListener;
import com.karahanbuhan.mods.bloodparticles.common.config.Configuration;
import com.karahanbuhan.mods.bloodparticles.common.config.field.BooleanField;
import com.karahanbuhan.mods.bloodparticles.common.config.field.DoubleField;
import com.karahanbuhan.mods.bloodparticles.common.config.field.StringField;
import net.fabricmc.api.ClientModInitializer;
import net.minecraft.entity.EntityType;
import net.minecraft.particle.*;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.util.Map;

import static com.karahanbuhan.mods.bloodparticles.client.ReferenceVariables.*;

/**
 * Represents the Blood Particles core
 */
public class BloodParticlesClientMod implements ClientModInitializer {
    private static Configuration config;
    private static Logger logger;

    /**
     * Called when fabric initializes Blood Particles
     * <p>
     * This method exists only for now because we use Fabric API
     */
    @Override
    public void onInitializeClient() {
        getConfig(); // Lets get config to be sure it is generated

        DamageLivingEntityCallback.EVENT.register(new DamageLivingEntityListener());
    }

    /**
     * Returns the mod configuration associated with the client
     *
     * @return Config associated with this mod
     */
    public static Configuration getConfig() {
        if (config == null) // Initialize the config if it does not exist/loaded yet
            try {
                config = new Configuration("bloodparticles.cfg",
                        "# This is the configuration file for Blood Particles.\n" +
                                "#\n" +
                                "# You can find information on editing this file and all the available options here:\n" +
                                "# https://github.com/karahanbuhan/bloodparticles-fabric/wiki/Configuration-File\n" +
                                "#\n" +
                                "# You can delete this file to reset all the fields.",

                        new BooleanField(IS_ENABLED.name, IS_ENABLED.description, true),
                        new DoubleField(BLOOD_MULTIPLIER.name, BLOOD_MULTIPLIER.description, 15.0),
                        new DoubleField(PARTICLE_LIMIT.name, PARTICLE_LIMIT.description, 1250.0),

                        new BooleanField(BLOOD_WHEN_ATTACK.name, BLOOD_WHEN_ATTACK.description, true),
                        new BooleanField(BLOOD_WHEN_CACTUS.name, BLOOD_WHEN_CACTUS.description, true),
                        new BooleanField(BLOOD_WHEN_CRAMMING.name, BLOOD_WHEN_CRAMMING.description, true),
                        new BooleanField(BLOOD_WHEN_CUSTOM_DAMAGE.name, BLOOD_WHEN_CUSTOM_DAMAGE.description, true),
                        new BooleanField(BLOOD_WHEN_DRAGON_BREATH.name, BLOOD_WHEN_DRAGON_BREATH.description, false),
                        new BooleanField(BLOOD_WHEN_DROWNING.name, BLOOD_WHEN_DROWNING.description, false),
                        new BooleanField(BLOOD_WHEN_DRYOUT.name, BLOOD_WHEN_DRYOUT.description, false),
                        new BooleanField(BLOOD_WHEN_EXPLOSION.name, BLOOD_WHEN_EXPLOSION.description, true),
                        new BooleanField(BLOOD_WHEN_FALL.name, BLOOD_WHEN_FALL.description, true),
                        new BooleanField(BLOOD_WHEN_FIRE.name, BLOOD_WHEN_FIRE.description, false),
                        new BooleanField(BLOOD_WHEN_FLY_INTO_WALL.name, BLOOD_WHEN_FLY_INTO_WALL.description, true),
                        new BooleanField(BLOOD_WHEN_HOT_FLOOR.name, BLOOD_WHEN_HOT_FLOOR.description, false),
                        new BooleanField(BLOOD_WHEN_LAVA.name, BLOOD_WHEN_LAVA.description, false),
                        new BooleanField(BLOOD_WHEN_LIGHTNING.name, BLOOD_WHEN_LIGHTNING.description, false),
                        new BooleanField(BLOOD_WHEN_MAGIC.name, BLOOD_WHEN_MAGIC.description, false),
                        new BooleanField(BLOOD_WHEN_POISON.name, BLOOD_WHEN_POISON.description, false),
                        new BooleanField(BLOOD_WHEN_PROJECTILE.name, BLOOD_WHEN_PROJECTILE.description, true),
                        new BooleanField(BLOOD_WHEN_STARVATION.name, BLOOD_WHEN_STARVATION.description, false),
                        new BooleanField(BLOOD_WHEN_SUFFOCATION.name, BLOOD_WHEN_SUFFOCATION.description, false),
                        new BooleanField(BLOOD_WHEN_THORNS.name, BLOOD_WHEN_THORNS.description, true),
                        new BooleanField(BLOOD_WHEN_VOID.name, BLOOD_WHEN_VOID.description, false),
                        new BooleanField(BLOOD_WHEN_WITHER.name, BLOOD_WHEN_WITHER.description, true),

                        new StringField(DEFAULT_PARTICLE.name, DEFAULT_PARTICLE.description, "minecraft:block minecraft:redstone_block"),
                        new StringField(BLAZE_PARTICLE.name, BLAZE_PARTICLE.description, "minecraft:flame"),
                        new StringField(ENDERMAN_PARTICLE.name, ENDERMAN_PARTICLE.description, "minecraft:block minecraft:purple_concrete"),
                        new StringField(ENDER_DRAGON_PARTICLE.name, ENDERMAN_PARTICLE.description, "minecraft:block minecraft:purple_concrete"),
                        new StringField(MAGMA_CUBE_PARTICLE.name, MAGMA_CUBE_PARTICLE.description, "minecraft:block minecraft:magma_block"),
                        new StringField(PHANTOM_PARTICLE.name, PHANTOM_PARTICLE.description, "minecraft:block minecraft:gray_stained_glass"),
                        new StringField(SLIME_PARTICLE.name, SLIME_PARTICLE.description, "minecraft:item_slime"),
                        new StringField(ZOMBIFIED_PIGLIN_PARTICLE.name, ZOMBIFIED_PIGLIN_PARTICLE.description, "minecraft:block minecraft:lime_terracotta")
                );
            } catch (IOException e) {
                throw new RuntimeException("Could not get config", e);
            }
        else
            config.load(); // Load config everytime when accessed

        return config;
    }

    /**
     * Returns the mod logger associated with the client
     *
     * @return Logger associated with this mod
     */
    public static Logger getLogger() {
        if (logger == null)
            logger = LogManager.getLogger("Blood Particles");

        return logger;
    }

    /**
     * Return the blood particle effect for a specific entity type
     *
     * @param type The entity type which will bleed
     * @return The blood particle effect of the entity
     */
    public static ParticleEffect getBloodParticleEffect(EntityType<?> type) {
        String id = Registry.ENTITY_TYPE.getId(type).toString();

        String particle = (String) config.getFieldByName(id).getValue(); // No worries! You can cast null to any reference
        if (particle == null)
            particle = (String) config.getFieldByName(DEFAULT_PARTICLE.name).getValue();

        ParticleType<?> particleType = Registry.PARTICLE_TYPE.get(Identifier.tryParse(particle.split(" ")[0]));
        if (particle.split(" ").length == 2) {
            Identifier particleId = Identifier.tryParse(particle.split(" ")[1]); // This is required for block or item id
            if (particleType == ParticleTypes.BLOCK)
                return new BlockStateParticleEffect(ParticleTypes.BLOCK, Registry.BLOCK.get(particleId).getDefaultState());
            else if (particleType == ParticleTypes.ITEM)
                return new ItemStackParticleEffect(ParticleTypes.ITEM, Registry.ITEM.get(particleId).getDefaultStack());
        }
        return (ParticleEffect) particleType;
    }

    /**
     * Returns the blood multiplier
     *
     * @return Blood multiplier as double type
     */
    public static double getBloodMultiplier() {
        return ((Double) config.getFieldByName(BLOOD_MULTIPLIER.name).getValue());
    }

    /**
     * Returns the particle limit
     *
     * @return Maximum particle limit as double type
     */
    public static double getParticleLimit() {
        return ((Double) config.getFieldByName(PARTICLE_LIMIT.name).getValue());
    }

    /**
     * Checks if particles are enabled
     *
     * @return Whether the particles are enabled or not
     */
    public static boolean isEnabled() {
        return ((Boolean) config.getFieldByName(IS_ENABLED.name).getValue());
    }

    /**
     * Checks if a damage source is enabled
     *
     * @param source The source that will be checked if is enabled
     * @return Whether the damage source is enabled or not
     */
    public static boolean isDamageSourceEnabled(String source) {
        HashMultimap<ReferenceVariables, String> map = HashMultimap.create(); // There may be duplicate keys so we use Multimap
        map.put(BLOOD_WHEN_ATTACK, "sting");
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

        for (Map.Entry<ReferenceVariables, String> entry : map.entries())
            if ((Boolean) config.getFieldByName(entry.getKey().name).getValue())
                return true; // Return true if the reference is enabled

        return false;
    }
}
