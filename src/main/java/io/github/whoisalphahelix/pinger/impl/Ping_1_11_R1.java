package io.github.whoisalphahelix.pinger.impl;

import io.github.whoisalphahelix.pinger.Ping;
import org.bukkit.craftbukkit.v1_11_R1.entity.CraftPlayer;
import org.bukkit.entity.Player;

public class Ping_1_11_R1 implements Ping {
    @Override
    public int getPing(Player p) {
        return ((CraftPlayer) p).getHandle().ping;
    }
}
