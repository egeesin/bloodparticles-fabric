package com.karahanbuhan.mods.bloodparticles.client.gui;

import com.karahanbuhan.mods.bloodparticles.client.gui.widget.OptionsListWidget;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.options.KeyBinding;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.TranslatableText;

/**
 * Represents screen for blood particles config
 */
public class BloodParticlesOptionsScreen extends Screen {
    public KeyBinding focusedBinding;
    public long time;
    private OptionsListWidget optionsListWidget;
    private ButtonWidget resetButton;
    private Screen parent;

    public BloodParticlesOptionsScreen(Screen parent) {
        super(new TranslatableText("bloodparticles.config.title"));

        this.parent = parent;
    }

    @Override
    protected void init() {
        this.optionsListWidget = new OptionsListWidget(this, this.client);
        this.children.add(this.optionsListWidget);
        this.resetButton = this.addButton(new ButtonWidget(this.width / 2 - 155, this.height - 29, 150, 20, new TranslatableText("bloodparticles.config.buttons.resetAll"), (button) -> {
            this.optionsListWidget.resetAllFields();
        }));
        this.addButton(new ButtonWidget(this.width / 2 - 155 + 160, this.height - 29, 150, 20, new TranslatableText("bloodparticles.config.buttons.done"), (button) -> {
            this.optionsListWidget.saveAllFields();
            this.client.openScreen(this.parent);
        }));
    }

    @Override
    public void render(MatrixStack matrices, int mouseX, int mouseY, float delta) {
        this.renderBackground(matrices);

        this.optionsListWidget.render(matrices, mouseX, mouseY, delta);
        drawCenteredText(matrices, this.textRenderer, this.title, this.width / 2, 8, 16777215);
        this.resetButton.active = this.optionsListWidget.isAnyResetButtonActive();
        super.render(matrices, mouseX, mouseY, delta);
    }

    @Override
    public void tick() {
        super.tick();

        this.optionsListWidget.tick();
    }

    @Override
    public void removed() {
        super.removed();

        this.optionsListWidget.removed();
    }
}
