/*
 * Copyright (c) AlphaHelix 2017
 */
package de.alphahelix.alphaapi.listener;

import de.alphahelix.alphaapi.AlphaAPI;
import org.bukkit.Bukkit;
import org.bukkit.event.Listener;

public class SimpleListener implements Listener {

    /**
     * Automatic registering {@link Listener}
     */
    public SimpleListener() {
        Bukkit.getPluginManager().registerEvents(this, AlphaAPI.getInstance());
    }
}
