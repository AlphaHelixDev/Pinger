/*
 * Copyright (c) AlphaHelix 2017
 */

package de.alphahelix.pinger;

import org.bukkit.plugin.java.JavaPlugin;

public class Pinger extends JavaPlugin {

    private static Pinger instance;

    public static Pinger getInstance() {
        return instance;
    }

    @Override
    public void onEnable() {
        instance = this;
    }
}
