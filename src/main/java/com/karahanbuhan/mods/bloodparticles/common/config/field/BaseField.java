package com.karahanbuhan.mods.bloodparticles.common.config.field;

import java.util.Objects;

/**
 * Represents a field which holds a value
 */
public abstract class BaseField {
    private final String name;
    private final String description;
    private final Object defaultValue;
    
    private Object value;

    /**
     * Initializes a field with an unique name, description and a value
     */
    protected BaseField(String name, String description, Object value) {
        this.name = name;
        this.description = description;
        this.defaultValue = value;

        changeValue(value);
    }

    /**
     * Compare names of the fields for preventing duplication
     */
    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;
        BaseField field = (BaseField) object;
        return name.equals(field.name);
    }

    /**
     * Compare names of the fields for preventing duplication
     */
    @Override
    public int hashCode() {
        return Objects.hash(name);
    }

    /**
     * Changes the value stored in this field
     * <p>
     * This method is package-private so cannot be accessed accidentally
     */
    void changeValue(Object value) {
        this.value = value;
    }

    /**
     * Returns the name of this field
     */
    public String getName() {
        return name;
    }

    /**
     * Returns the description of this field
     */
    public String getDescription() {
        return description;
    }

    /**
     * Returns the default value stored in this field
     */
    public Object getDefaultValue() {
        return defaultValue;
    }

    /**
     * Returns the value stored in this field
     */
    public Object getValue() {
        return value;
    }
}
