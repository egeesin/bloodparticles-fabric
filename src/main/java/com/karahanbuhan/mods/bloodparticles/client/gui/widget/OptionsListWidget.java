package com.karahanbuhan.mods.bloodparticles.client.gui.widget;

import com.google.common.collect.ImmutableList;
import com.karahanbuhan.mods.bloodparticles.client.BloodParticlesClientMod;
import com.karahanbuhan.mods.bloodparticles.client.gui.BloodParticlesOptionsScreen;
import com.karahanbuhan.mods.bloodparticles.common.config.field.BaseField;
import com.karahanbuhan.mods.bloodparticles.common.config.field.BooleanField;
import com.karahanbuhan.mods.bloodparticles.common.config.field.DoubleField;
import com.karahanbuhan.mods.bloodparticles.common.config.field.StringField;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.Element;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.gui.widget.ElementListWidget;
import net.minecraft.client.gui.widget.TextFieldWidget;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;

import java.util.HashSet;
import java.util.List;

/**
 * Represent the options list widget in configuration screen
 */
public class OptionsListWidget extends ElementListWidget<OptionsListWidget.Entry> {
    private final MinecraftClient client;
    private final HashSet<OptionsListWidget.FieldEntry> entries = new HashSet<>();

    public OptionsListWidget(BloodParticlesOptionsScreen parent, MinecraftClient client) {
        super(client, parent.width + 45, parent.height, 43, parent.height - 32, 30);
        client.keyboard.setRepeatEvents(true);
        this.client = client;

        selectedField = "";

        for (BaseField field : BloodParticlesClientMod.getConfig().getFieldSet()) {
            OptionsListWidget.FieldEntry entry = new OptionsListWidget.FieldEntry(field);
            this.addEntry(entry);

            entries.add(entry);
        }
    }

    protected int getScrollbarPositionX() {
        return super.getScrollbarPositionX() + 80;
    }

    public int getRowWidth() {
        return super.getRowWidth() + 100;
    }

    public boolean isAnyResetButtonActive() {
        return entries.stream().anyMatch(entry -> !entry.textField.getText().equals(entry.field.getValue().toString()));
    }

    public void resetAllFields() {
        entries.forEach(entry -> {
            entry.resetButton.onPress();
        });
    }

    public void saveAllFields() {
        entries.forEach(entry -> {
            BaseField field = entry.field;
            String text = entry.textField.getText();

            if (field instanceof BooleanField)
                ((BooleanField) field).changeValue(Boolean.parseBoolean(text));
            else if (field instanceof DoubleField)
                ((DoubleField) field).changeValue(Double.parseDouble(text));
            else
                ((StringField) field).changeValue(text);

            BloodParticlesClientMod.saveConfig();
        });
    }

    public void tick() {
        entries.forEach(FieldEntry::tick);
    }

    public void removed() {
        entries.forEach(FieldEntry::removed);
    }

    private static String selectedField = "";

    @Environment(EnvType.CLIENT)
    public class FieldEntry extends OptionsListWidget.Entry {
        private final BaseField field;
        private final TextFieldWidget textField;
        private final ButtonWidget resetButton;
        private final Text key;

        private FieldEntry(final BaseField field) {
            this.field = field;
            this.key = Text.of(field.getName());

            this.textField = new TextFieldWidget(MinecraftClient.getInstance().textRenderer, 0, 0, 180, 20, Text.of(field.getName()));
            this.textField.setMaxLength(256);
            this.textField.setText(field.getValue().toString());

            this.resetButton = new ButtonWidget(0, 0, 50, 20, new TranslatableText("bloodparticles.config.buttons.reset"), (button) -> {
                textField.setText(field.getValue().toString());
            });
        }

        @Override
        public void render(MatrixStack matrices, int index, int y, int x, int entryWidth, int entryHeight, int mouseX, int mouseY, boolean hovered, float tickDelta) {
            TextRenderer textRenderer = OptionsListWidget.this.client.textRenderer;
            this.textField.x = x + 75;
            this.textField.y = y + 5;

            this.resetButton.x = x + 265;
            this.resetButton.y = y + 5;
            this.resetButton.active = !this.textField.getText().equals(field.getValue().toString());
            this.resetButton.render(matrices, mouseX, mouseY, tickDelta);

            drawTextWithShadow(matrices, textRenderer, this.key, x - 80, y + 12, 10526880);

            if (field.getName().equalsIgnoreCase(selectedField))
                textField.setSelected(true);
            else {
                textField.setSelectionStart(0);
                textField.setSelectionEnd(0);
                textField.setSelected(false);
            }
            this.textField.render(matrices, mouseX, mouseY, tickDelta);
        }

        public List<? extends Element> children() {
            return ImmutableList.of(this.textField, this.resetButton);
        }

        @Override
        public boolean keyPressed(int keyCode, int scanCode, int modifiers) {
            return textField.keyPressed(keyCode, scanCode, modifiers);
        }

        @Override
        public boolean charTyped(char chr, int keyCode) {
            return textField.charTyped(chr, keyCode);
        }

        @Override
        public boolean mouseClicked(double mouseX, double mouseY, int button) {
            if (this.textField.mouseClicked(mouseX, mouseY, button)) {
                selectedField = field.getName();

                return true;
            } else {
                return this.resetButton.mouseClicked(mouseX, mouseY, button);
            }
        }

        @Override
        public boolean mouseReleased(double mouseX, double mouseY, int button) {
            return this.textField.mouseReleased(mouseX, mouseY, button) || this.resetButton.mouseReleased(mouseX, mouseY, button);
        }

        public void tick() {
            textField.tick();
        }

        public void removed() {
            client.keyboard.setRepeatEvents(false);
        }
    }

    @Environment(EnvType.CLIENT)
    public abstract static class Entry extends net.minecraft.client.gui.widget.ElementListWidget.Entry<OptionsListWidget.Entry> {
        public Entry() {
        }
    }
}