package com.karahanbuhan.mods.bloodparticles.client;

import com.karahanbuhan.mods.bloodparticles.api.event.DamageLivingEntityCallback;
import com.karahanbuhan.mods.bloodparticles.client.listener.DamageLivingEntityListener;
import net.fabricmc.api.ClientModInitializer;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Represents the Blood Particles core
 */
public class BloodParticlesClientMod implements ClientModInitializer {
    private static Logger logger;

    /**
     * Static class cannot be initialized
     */
    private BloodParticlesClientMod() {
        logger().warn("What have you done?");
    }

    /**
     * Called when fabric initializes Blood Particles
     *
     * This method exists only for now because we use Fabric API
     */
    @Override
    public void onInitializeClient() {
        DamageLivingEntityCallback.EVENT.register(new DamageLivingEntityListener());
    }

    /**
     * Returns the mod logger associated with the client.
     *
     * @return Logger associated with this mod
     */
    public static Logger logger() {
        if (logger == null)
            logger = LogManager.getLogger("Blood Particles");

        return logger;
    }
}
