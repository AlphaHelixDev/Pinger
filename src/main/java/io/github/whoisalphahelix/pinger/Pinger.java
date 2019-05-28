package io.github.whoisalphahelix.pinger;

import io.github.whoisalphahelix.pinger.impl.*;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.WeakHashMap;

public final class Pinger extends JavaPlugin implements Listener {

    private static final Map<String, List<Integer>> pingMap = new WeakHashMap<>();
    private static Ping ping;
    private FileConfiguration options = getConfig();

    public static int getPing(Player p) {
        List<Integer> pingList = pingMap.getOrDefault(p.getName(), new LinkedList<>());
        int currPing = ping.getPing(p);

        if (currPing < 1500)
            pingList.add(currPing);

        pingMap.put(p.getName(), pingList);

        return currPing;
    }

    public static int getAveragePing(Player p) {
        return pingMap.containsKey(p.getName()) ?
                pingMap.get(p.getName()).stream().mapToInt(Integer::intValue).sum() / pingMap.get(p.getName()).size() :
                getPing(p);
    }

    @Override
    public void onEnable() {
        if (setupPinger())
            getLogger().info("Pinger setup was successful!");
        else {
            getLogger().severe("Failed to setup Pinger! Your server version is not compatible with this plugin!");

            Bukkit.getPluginManager().disablePlugin(this);
            return;
        }

        Bukkit.getPluginManager().registerEvents(this, this);
        getCommand("ping").setExecutor(new PingCommand(this));

        getConfig().options().copyDefaults(true);
        saveDefaultConfig();
        saveConfig();
    }

    boolean inTab() {
        return options.getBoolean("Ping_in_tab.enabled");
    }

    String getTabLayout(Player p) {
        return ChatColor.translateAlternateColorCodes('&', options.getString("Ping_in_tab.layout")
                .replace("{#player}", p.getDisplayName())
                .replace("{#ping}", Integer.toString(getPing(p))));
    }

    long getTabUpdateInterval() {
        return options.getLong("Ping_in_tab.interval");
    }

    String getNotOnlineMessage(String player) {
        return ChatColor.translateAlternateColorCodes('&', options.getString("Messages.not_online")
                .replace("{#player}", player));
    }

    String getNotAllowedMessage(String perm) {
        return ChatColor.translateAlternateColorCodes('&', options.getString("Messages.not_allowed")
                .replace("{#permission}", perm));
    }

    String getCurrentPingMessage(Player p) {
        return ChatColor.translateAlternateColorCodes('&', options.getString("Messages.current_ping")
                .replace("{#player}", p.getDisplayName())
                .replace("{#ping}", Integer.toString(getPing(p))));
    }

    String getAveragePingMessage(Player p) {
        return ChatColor.translateAlternateColorCodes('&', options.getString("Messages.average_ping")
                .replace("{#player}", p.getDisplayName())
                .replace("{#ping}", Integer.toString(getAveragePing(p))));
    }

    String getOtherCurrentPingMessage(Player other) {
        return ChatColor.translateAlternateColorCodes('&', options.getString("Messages.other_current_ping")
                .replace("{#player}", other.getDisplayName())
                .replace("{#ping}", Integer.toString(getPing(other))));
    }

    String getOtherAveragePingMessage(Player other) {
        return ChatColor.translateAlternateColorCodes('&', options.getString("Messages.other_average_ping")
                .replace("{#player}", other.getDisplayName())
                .replace("{#ping}", Integer.toString(getPing(other))));
    }

    String getJoinMessage(Player p) {
        return ChatColor.translateAlternateColorCodes('&', options.getString("Messages.join")
                .replace("{#player}", p.getPlayerListName())
                .replace("{#ping}", Integer.toString(getPing(p))));
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent e) {
        Player p = e.getPlayer();
        String msg = getJoinMessage(p);

        if (!msg.isEmpty())
            new BukkitRunnable() {
                public void run() {
                    p.sendMessage(msg);
                }
            }.runTaskLater(this, 40);

        if (inTab())
            new BukkitRunnable() {
                public void run() {
                    p.setPlayerListName(getTabLayout(p));
                }
            }.runTaskTimer(this, 40, getTabUpdateInterval() * 20);
    }

    private boolean setupPinger() {
        String version = Bukkit.getServer().getClass().getPackage().getName().split("\\.")[3];

        switch (version) {
            case "v1_8_R1":
                ping = new Ping_1_8_R1();
                break;
            case "v1_8_R2":
                ping = new Ping_1_8_R2();
                break;
            case "v1_8_R3":
                ping = new Ping_1_8_R3();
                break;
            case "v1_9_R1":
                ping = new Ping_1_9_R1();
                break;
            case "v1_9_R2":
                ping = new Ping_1_9_R2();
                break;
            case "v1_10_R1":
                ping = new Ping_1_10_R1();
                break;
            case "v1_11_R1":
                ping = new Ping_1_11_R1();
                break;
            case "v1_12_R1":
                ping = new Ping_1_12_R1();
                break;
            case "v1_13_R1":
                ping = new Ping_1_13_R1();
                break;
            case "v1_13_R2":
                ping = new Ping_1_13_R2();
                break;
            case "v1_14_R1":
                ping = new Ping_1_14_R1();
                break;
        }

        return ping != null;
    }
}
