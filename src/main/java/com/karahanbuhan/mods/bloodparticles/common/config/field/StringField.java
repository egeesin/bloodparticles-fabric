package com.karahanbuhan.mods.bloodparticles.common.config.field;

/**
 * Represents a field which holds a string type value
 */
public class StringField extends BaseField {
    /**
     * Initializes a field with an unique name, description and a string value
     */
    public StringField(String name, String description, String value) {
        super(name, description, value);
    }

    /**
     * Changes the value stored in this field
     */
    public void changeValue(String value) {
        super.changeValue(value);
    }

    @Override
    public String getDefaultValue() {
        return (String) super.getDefaultValue();
    }

    @Override
    public String getValue() {
        return (String) super.getValue();
    }
}
