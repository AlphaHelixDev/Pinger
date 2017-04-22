/*
 * Copyright (c) AlphaHelix 2017
 */

package de.alphahelix.alphaapi;

import org.bukkit.plugin.java.JavaPlugin;

public class AlphaAPI extends JavaPlugin {

    private static AlphaAPI instance;

    public static AlphaAPI getInstance() {
        return instance;
    }

    @Override
    public void onEnable() {
        instance = this;
    }
}
