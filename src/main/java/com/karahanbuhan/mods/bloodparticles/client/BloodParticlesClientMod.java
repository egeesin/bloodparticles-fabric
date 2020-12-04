package com.karahanbuhan.mods.bloodparticles.client;

import com.karahanbuhan.mods.bloodparticles.api.event.DamageLivingEntityCallback;
import com.karahanbuhan.mods.bloodparticles.client.listener.DamageLivingEntityListener;
import com.karahanbuhan.mods.bloodparticles.common.config.Configuration;
import com.karahanbuhan.mods.bloodparticles.common.config.field.BooleanField;
import com.karahanbuhan.mods.bloodparticles.common.config.field.DoubleField;
import com.karahanbuhan.mods.bloodparticles.common.config.field.StringField;
import net.fabricmc.api.ClientModInitializer;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;

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
    private static Configuration getConfig() {
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
                        new BooleanField(BLOOD_WHEN_MELTING.name, BLOOD_WHEN_MELTING.description, false),
                        new BooleanField(BLOOD_WHEN_POISON.name, BLOOD_WHEN_POISON.description, false),
                        new BooleanField(BLOOD_WHEN_PROJECTILE.name, BLOOD_WHEN_PROJECTILE.description, true),
                        new BooleanField(BLOOD_WHEN_STARVATION.name, BLOOD_WHEN_STARVATION.description, false),
                        new BooleanField(BLOOD_WHEN_SUFFOCATION.name, BLOOD_WHEN_SUFFOCATION.description, false),
                        new BooleanField(BLOOD_WHEN_SUICIDE.name, BLOOD_WHEN_SUICIDE.description, false),
                        new BooleanField(BLOOD_WHEN_THORNS.name, BLOOD_WHEN_THORNS.description, true),
                        new BooleanField(BLOOD_WHEN_VOID.name, BLOOD_WHEN_VOID.description, false),
                        new BooleanField(BLOOD_WHEN_WITHER.name, BLOOD_WHEN_WITHER.description, true),

                        new StringField(DEFAULT_PARTICLE.name, DEFAULT_PARTICLE.description, "minecraft:block minecraft:redstone_block"),
                        new StringField(BLAZE_PARTICLE.name, BLAZE_PARTICLE.description, "minecraft:particle"),
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
}
