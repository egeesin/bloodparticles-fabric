package com.karahanbuhan.mods.bloodparticles.client;

import com.karahanbuhan.mods.bloodparticles.client.gui.BloodParticlesOptionsScreen;
import io.github.prospector.modmenu.api.ConfigScreenFactory;
import io.github.prospector.modmenu.api.ModMenuApi;

/**
 * Represents the implementation of ModMenuApi
 */
public class BloodParticlesModMenu implements ModMenuApi {
    @Override
    public ConfigScreenFactory<?> getModConfigScreenFactory() {
        return BloodParticlesOptionsScreen::new;
    }
}
