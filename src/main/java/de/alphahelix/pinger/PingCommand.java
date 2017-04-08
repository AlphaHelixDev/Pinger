/*
 * Copyright (c) AlphaHelix 2017
 */

package de.alphahelix.pinger;

import de.alphahelix.alphaapi.command.SimpleCommand;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.List;

public class PingCommand extends SimpleCommand {
    public PingCommand() {
        super("ping", "Check the ping", "pong");
    }

    @Override
    public boolean execute(CommandSender cs, String label, String[] args) {

        if (cs instanceof Player) {
            Player p = (Player) cs;

            if (args.length == 0) {
                if (p.hasPermission("pinger.see.own")) {
                    p.sendMessage(Pinger.getOptionsFile().getYourCurrentPing(PingUtil.getPing(p)));
                    return true;
                }
            } else if (args.length == 1) {
                if (args[0].equalsIgnoreCase("average")) {
                    if (p.hasPermission("pinger.see.own.average")) {
                        p.sendMessage(Pinger.getOptionsFile().getYourAveragePing(PingUtil.getAveragePing(p)));
                    } else {
                        p.sendMessage(Pinger.getOptionsFile().getNotAllowed());
                    }
                    return true;
                } else if (Bukkit.getPlayerExact(args[0]) != null) {
                    if (p.hasPermission("pinger.see.other")) {
                        p.sendMessage(Pinger.getOptionsFile().getOthersCurrentPing(PingUtil.getPing(Bukkit.getPlayerExact(args[0]))));
                    } else {
                        p.sendMessage(Pinger.getOptionsFile().getNotAllowed());
                    }
                    return true;
                } else {
                    p.sendMessage(Pinger.getOptionsFile().getNotOnline(args[0]));
                    return true;
                }
            } else if (args.length == 2) {
                if (args[0].equalsIgnoreCase("average")) {
                    if (Bukkit.getPlayerExact(args[1]) != null) {
                        if (p.hasPermission("pinger.see.other.average")) {
                            p.sendMessage(Pinger.getOptionsFile().getOthersAveragePing(PingUtil.getAveragePing(Bukkit.getPlayerExact(args[1]))));
                        } else {
                            p.sendMessage(Pinger.getOptionsFile().getNotAllowed());
                        }
                    } else {
                        p.sendMessage(Pinger.getOptionsFile().getNotOnline(args[1]));
                    }
                    return true;
                }
            }
        } else {
            if (args.length == 1) {
                if (Bukkit.getPlayerExact(args[0]) != null) {
                    cs.sendMessage(Pinger.getOptionsFile().getOthersCurrentPing(PingUtil.getPing(Bukkit.getPlayerExact(args[0]))));
                    return true;
                } else {
                    cs.sendMessage(Pinger.getOptionsFile().getNotOnline(args[0]));
                }
            } else if (args.length == 2) {
                if (args[0].equalsIgnoreCase("average")) {
                    if (Bukkit.getPlayerExact(args[1]) != null) {
                        cs.sendMessage(Pinger.getOptionsFile().getOthersAveragePing(PingUtil.getAveragePing(Bukkit.getPlayerExact(args[1]))));
                    } else {
                        cs.sendMessage(Pinger.getOptionsFile().getNotOnline(args[1]));
                    }
                    return true;
                }
            }
        }


        return false;
    }

    @Override
    public List<String> tabComplete(CommandSender cs, String label, String[] args) {
        return null;
    }
}
