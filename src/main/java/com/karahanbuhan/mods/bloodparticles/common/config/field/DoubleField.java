package com.karahanbuhan.mods.bloodparticles.common.config.field;

/**
 * Represents a field which holds a double type value
 */
public class DoubleField extends BaseField {
    /**
     * Initializes a field with an unique name, description and a double value
     */
    public DoubleField(String name, String description, double value) {
        super(name, description, value);
    }

    /**
     * Changes the value stored in this field
     */
    public void changeValue(double value) {
        super.changeValue(value);
    }

    @Override
    public Double getDefaultValue() {
        return (Double) super.getDefaultValue();
    }

    @Override
    public Double getValue() {
        return (Double) super.getValue();
    }
}
