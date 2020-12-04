package com.karahanbuhan.mods.bloodparticles.common.config;

import com.karahanbuhan.mods.bloodparticles.common.config.field.BaseField;
import com.karahanbuhan.mods.bloodparticles.common.config.field.BooleanField;
import com.karahanbuhan.mods.bloodparticles.common.config.field.DoubleField;
import com.karahanbuhan.mods.bloodparticles.common.config.field.StringField;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.Set;

/**
 * Represents the configuration
 */
public class Configuration {
    private final Set<BaseField> fieldSet = new LinkedHashSet<>();

    /**
     * Gets the field by it's name, it returns null if there is no field with such name
     *
     * @param name Name of the field
     * @return The field matching name, if there is none; method will return null
     */
    public BaseField getFieldByName(String name) {
        return fieldSet.stream()
                .filter(field -> field.getName().equals(name))
                .findFirst()
                .orElse(null);
    }

    private final File file;
    private final String header;

    /**
     * Initialize a new configuration
     *
     * @param fileName The name of the configuration file, do not forget to include file extension
     * @param header   Header of the configuration file for leaving note as comment
     * @param fields   List of the fields that will be copied
     * @throws IOException Can be thrown if there is a problem in {@link Configuration#load}
     */
    public Configuration(String fileName, String header, BaseField... fields) throws IOException {
        file = new File("config/" + fileName);

        this.header = header;

        fieldSet.addAll(Arrays.asList(fields));

        load();
    }

    /**
     * Loads the configuration: if there is no config file, creates one. Also updates all the fields values from file
     *
     * @return The Configuration, it is does not create a clone instance so there is no need for this return
     */
    public Configuration load() {
        if (!file.exists())
            try {
                writeDefaultConfig();
            } catch (IOException e) {
                throw new RuntimeException("Could not write default configuration file");
            }

        // Lets use our configuration reading system with 3 data types. How lame!
        try (BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(file), StandardCharsets.UTF_8))) {
            String line;
            while ((line = br.readLine()) != null) {
                if (line.startsWith("#") || line.trim().isEmpty() || !line.contains("="))
                    continue; // Continue if the line is a comment line or empty

                String key = line.split("=")[0];
                if (key.isEmpty())
                    continue; // Continue if there is no key

                BaseField field = getFieldByName(key);
                if (field == null)
                    continue; // There is no such field that we know

                String value = line.split("=")[1].trim();
                if (String.valueOf(true).equalsIgnoreCase(value) || String.valueOf(false).equalsIgnoreCase(value)) {
                    ((BooleanField) field).changeValue(Boolean.parseBoolean(value)); // If the value is true or false, use BooleanField to check actual one later
                } else
                    try {
                        double parseDouble = Double.parseDouble(value); // We are looking for a NumberFormatException here
                        ((DoubleField) field).changeValue(Double.parseDouble(value)); // Then casting field as DoubleField to be sure
                    } catch (NumberFormatException e) {
                        // This block will be called if value is not both boolean and double so we will treat it like a string
                        // Maybe we can add more data types if we need them in future
                        ((StringField) field).changeValue(value);
                    }
            }
        } catch (IOException e) {
            throw new RuntimeException("Could not load configuration file", e);
        }

        return this; // We do not really need to return but people like this?
    }

    /**
     * Copies the given default fields if they do not exist in configuration
     */
    private void writeDefaultConfig() throws IOException {
        File dir = file.getParentFile();

        if (!dir.exists()) {
            if (!dir.mkdirs()) {
                throw new IOException("Could not create parent directories");
            }
        } else if (!dir.isDirectory()) {
            throw new IOException("The parent file is not a directory");
        }

        try (Writer writer = new FileWriter(file, false)) { // We want a clean config so put false as second argument
            if (header != null) // Write the header if it exists
                writer.write(header + "\n");

            for (BaseField field : fieldSet) {
                writer.write("\n# " + field.getDescription()); // Writes the description of field as comment line
                writer.write("\n" + field.getName() + "=" + field.getValue().toString() + "\n"); // Writes the key value in format 'key=value'
            }
        }
    }
}
