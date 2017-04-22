/*
 * Copyright (c) AlphaHelix 2017
 */
package de.alphahelix.alphaapi.file;

import de.alphahelix.alphaapi.AlphaAPI;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.scheduler.BukkitRunnable;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class SimpleFile extends YamlConfiguration {

    private File source = null;

    /**
     * Create a new {@link SimpleFile} inside the given path with the name 'name'
     *
     * @param path the path where the {@link File} should be created in
     * @param name the name which the {@link File} should have
     */
    public SimpleFile(String path, String name) {
        new File(path).mkdirs();
        source = new File(path, name);
        createIfNotExist();
        addValues();
    }

    /**
     * Create a new {@link SimpleFile} inside the plugin path with the name 'name'
     *
     * @param name the name which the file should have
     */
    public SimpleFile(String name) {
        source = new File(AlphaAPI.getInstance().getDataFolder().getPath(), name);
        createIfNotExist();
        addValues();
    }

    /**
     * Convert a normal {@link File} into a {@link SimpleFile}
     *
     * @param f the old File which you want to convert
     */
    public SimpleFile(File f) {
        source = f;
        createIfNotExist();
        addValues();
    }

    /**
     * Finish the setup of the {@link SimpleFile}
     */
    private void finishSetup() {
        try {
            load(source);
        } catch (Exception ignored) {

        }
    }

    /**
     * Overridden method to add new standart values to a config
     */
    public void addValues() {

    }

    /**
     * Create a new {@link SimpleFile} if it's not existing
     */
    private void createIfNotExist() {
        options().copyDefaults(true);
        if (source == null || !source.exists()) {
            try {
                assert source != null;
                source.createNewFile();
            } catch (IOException e) {
                new BukkitRunnable() {
                    @Override
                    public void run() {
                        try {
                            source.createNewFile();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }.runTaskLaterAsynchronously(AlphaAPI.getInstance(), 20);
            }
        }
        finishSetup();
    }

    /**
     * Get a colored {@link String}
     *
     * @param path the path inside this {@link SimpleFile}
     * @return the {@link String} with Colors
     */
    public String getColorString(String path) {
        if (!contains(path))
            return "";

        try {
            String toReturn = getString(path);
            return ChatColor.translateAlternateColorCodes('&', toReturn);
        } catch (Exception e) {
            return "";
        }
    }

    /**
     * Get a colored {@link ArrayList} out of this {@link SimpleFile}
     *
     * @param path the path inside this {@link SimpleFile}
     * @return the {@link ArrayList} with Colors
     */
    public ArrayList<String> getColorStringList(String path) {
        if (!configContains(path)) return new ArrayList<>();
        if (!isList(path)) return new ArrayList<>();

        try {
            ArrayList<String> tR = new ArrayList<>();
            for (String str : getStringList(path)) {
                tR.add(ChatColor.translateAlternateColorCodes('&', str));
            }
            return tR;
        } catch (Exception e) {
            return new ArrayList<>();
        }
    }

    /**
     * Checks if this {@link SimpleFile} contains a specific {@link String}
     *
     * @param toCheck {@link String} which might be inside this {@link SimpleFile}
     * @return whether or not this {@link SimpleFile} contains the {@link String}
     */
    public boolean configContains(String toCheck) {
        boolean cContains = false;

        for (String key : getKeys(true)) {
            if (key.equalsIgnoreCase(toCheck))
                cContains = true;
        }

        return cContains;
    }

    /**
     * Save and load this {@link SimpleFile}
     */
    public void save() {
        try {
            if (source == null) return;
            save(source);
        } catch (IOException ignored) {
            ignored.printStackTrace();
        }
    }

    /**
     * Add a new value to this {@link SimpleFile}
     *
     * @param path  where the value should be saved at
     * @param value the value which you want to save
     */
    public void setDefault(String path, Object value) {
        if (path.contains("§")) {
            path = path.replaceAll("§", "&");
        }
        if (value instanceof String)
            value = ((String) value).replaceAll("§", "&");

        addDefault(path, value);

        save();
    }
}