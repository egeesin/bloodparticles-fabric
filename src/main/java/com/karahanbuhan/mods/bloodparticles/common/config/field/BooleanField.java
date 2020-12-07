package com.karahanbuhan.mods.bloodparticles.common.config.field;

/**
 * Represents a field which holds a boolean type value
 */
public class BooleanField extends BaseField {
    /**
     * Initializes a field with an unique name, description and a boolean value
     */
    public BooleanField(String name, String description, boolean value) {
        super(name, description, value);
    }

    /**
     * Changes the value stored in this field
     */
    public void changeValue(boolean value) {
        super.changeValue(value);
    }

    @Override
    public Boolean getValue() {
        return (Boolean) super.getValue();
    }
}
