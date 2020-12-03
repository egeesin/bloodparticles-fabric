package com.karahanbuhan.mods.bloodparticles.client;

import com.karahanbuhan.mods.bloodparticles.api.event.DamageLivingEntityCallback;
import com.karahanbuhan.mods.bloodparticles.client.listener.DamageLivingEntityListener;
import net.fabricmc.api.ClientModInitializer;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class BloodParticlesClientMod implements ClientModInitializer {
    private static Logger logger;

    @Override
    public void onInitializeClient() {
        DamageLivingEntityCallback.EVENT.register(new DamageLivingEntityListener());
    }

    /**
     * Returns the mod logger associated with the client.
     *
     * @return Logger associated with this mod
     */
    public static Logger getLogger() {
        if (logger == null)
            logger = LogManager.getLogger("BloodParticles");

        return logger;
    }
}
