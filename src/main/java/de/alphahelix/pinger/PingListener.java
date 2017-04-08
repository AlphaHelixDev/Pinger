/*
 * Copyright (c) AlphaHelix 2017
 */

package de.alphahelix.pinger;

import de.alphahelix.alphaapi.listener.SimpleListener;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.scheduler.BukkitRunnable;

public class PingListener extends SimpleListener {

    @EventHandler
    public void onJoin(final PlayerJoinEvent e) {
        if (!Pinger.getOptionsFile().getJoinmessage(0).isEmpty())
            e.getPlayer().sendMessage(Pinger.getOptionsFile().getJoinmessage(PingUtil.getPing(e.getPlayer())));

        if (!Pinger.getOptionsFile().getPingInTab().isEmpty())
            new BukkitRunnable() {
                public void run() {
                    e.getPlayer().setPlayerListName(e.getPlayer().getPlayerListName() + " " + Pinger.getOptionsFile().getPingInTab() + PingUtil.getPing(e.getPlayer()));
                }
            }.runTaskTimer(Pinger.getInstance(), 40, Pinger.getOptionsFile().getPingUpdate() * 20);
    }
}
