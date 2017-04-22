/*
 * Copyright (c) AlphaHelix 2017
 */

package de.alphahelix.pinger;

import de.alphahelix.alphaapi.listener.SimpleListener;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.scheduler.BukkitRunnable;

public class PingListener extends SimpleListener {

    @EventHandler
    public void onJoin(final PlayerJoinEvent e) {
        final Player p = e.getPlayer();

        if (!Pinger.getOptionsFile().getJoinmessage(0).isEmpty())
            p.sendMessage(Pinger.getOptionsFile().getJoinmessage(PingUtil.getPing(p)));

        if (!Pinger.getOptionsFile().getPingInTab().isEmpty())
            new BukkitRunnable() {
                public void run() {
                    p.setPlayerListName(p.getName() + " " + Pinger.getOptionsFile().getPingInTab() + PingUtil.getPing(p) + "ms");
                }
            }.runTaskTimer(Pinger.getInstance(), 40, Pinger.getOptionsFile().getPingUpdate() * 20);
    }
}
