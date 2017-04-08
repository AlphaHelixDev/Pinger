/*
 * Copyright (c) AlphaHelix 2017
 */

package de.alphahelix.pinger;

import de.alphahelix.alphaapi.file.SimpleFile;

public class OptionsFile extends SimpleFile {
    public OptionsFile() {
        super("plugins/Pinger", "options.yml");
    }

    @Override
    public void addValues() {
        setDefault("Joinmessage.enabled", true);
        setDefault("Joinmessage.text", "&7Your current ping is &a[ping]&7ms.");

        setDefault("Ping in Tab.enabled", true);
        setDefault("Ping in Tab.color", "&a");
        setDefault("Ping in Tab.update after (seconds)", 60);

        setDefault("Messages.not online", "&c[player] &7is not online.");
        setDefault("Messages.not allowed", "&7You don't have permissions to execute this command!");

        setDefault("Messages.your current ping", "&7Your current ping is &a[ping]ms&7.");
        setDefault("Messages.your average ping", "&7Your average ping is &a[ping]ms&7.");

        setDefault("Messages.others current ping", "&7Your current ping is &a[ping]ms&7.");
        setDefault("Messages.others average ping", "&7Your average ping is &a[ping]ms&7.");
    }

    public long getPingUpdate() {
        return getLong("Ping in Tab.update after (seconds)");
    }

    public String getNotAllowed() {
        return getColorString("Messages.not allowed");
    }

    public String getNotOnline(String player) {
        return getColorString("Messages.not online").replace("[player]", player);
    }

    public String getYourCurrentPing(int ping) {
        return getColorString("Messages.your current ping").replace("[ping]", Integer.toString(ping));
    }

    public String getYourAveragePing(int ping) {
        return getColorString("Messages.your average ping").replace("[ping]", Integer.toString(ping));
    }

    public String getOthersCurrentPing(int ping) {
        return getColorString("Messages.others current ping").replace("[ping]", Integer.toString(ping));
    }

    public String getOthersAveragePing(int ping) {
        return getColorString("Messages.others average ping").replace("[ping]", Integer.toString(ping));
    }

    public String getPingInTab() {
        String color = "";

        if (getBoolean("Ping in Tab.enabled")) {
            color = getColorString("Ping in Tab.color");
        }

        return color;
    }

    public String getJoinmessage(int ping) {
        String msg = "";
        if (getBoolean("Joinmessage.enabled")) {
            msg = getColorString("Joinmessage.text").replace("[ping]", Integer.toString(ping));
        }
        return msg;
    }
}
