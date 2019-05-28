package io.github.whoisalphahelix.pinger;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class PingCommand implements CommandExecutor {

    private final Pinger pinger;

    PingCommand(Pinger pinger) {
        this.pinger = pinger;
    }

    @Override
    public boolean onCommand(CommandSender cs, Command command, String s, String[] args) {
        if (cs instanceof Player) {
            Player p = (Player) cs;

            if (args.length == 0) {
                if (p.hasPermission("pinger.see.own"))
                    p.sendMessage(pinger.getCurrentPingMessage(p));
                else
                    p.sendMessage(pinger.getNotAllowedMessage("pinger.see.own"));
                return true;
            } else if (args.length == 1) {
                if (args[0].equalsIgnoreCase("average")) {
                    if (p.hasPermission("pinger.see.own.average"))
                        p.sendMessage(pinger.getAveragePingMessage(p));
                    else
                        p.sendMessage(pinger.getNotAllowedMessage("pinger.see.own.average"));
                } else if (Bukkit.getPlayerExact(args[0]) != null) {
                    if (p.hasPermission("pinger.see.other"))
                        p.sendMessage(pinger.getOtherCurrentPingMessage(Bukkit.getPlayerExact(args[0])));
                    else
                        p.sendMessage(pinger.getNotAllowedMessage("pinger.see.other"));
                } else
                    p.sendMessage(pinger.getNotOnlineMessage(args[0]));

                return true;
            } else if (args.length == 2) {
                if (args[0].equalsIgnoreCase("average")) {

                    if (Bukkit.getPlayerExact(args[1]) != null) {
                        if (p.hasPermission("pinger.see.other.average"))
                            p.sendMessage(pinger.getOtherAveragePingMessage(Bukkit.getPlayerExact(args[1])));
                        else
                            p.sendMessage(pinger.getNotAllowedMessage("pinger.see.other.average"));
                    } else
                        p.sendMessage(pinger.getNotOnlineMessage(args[1]));

                    return true;
                }
            }

            p.sendMessage("§8--- §7Usage of §8/§bping §8---" + System.lineSeparator() +
                    "§8/§bping §8- §7Returns your own current ping" + System.lineSeparator() +
                    "§8/§bping average §8- §7Returns your own average ping" + System.lineSeparator() +
                    "§8/§bping §8<§bplayer§8> - §7Returns the players current ping" + System.lineSeparator() +
                    "§8/§bping average §8<§bplayer§8> - §7Returns the players average ping");

            return true;
        } else {
            if (args.length == 1) {
                if (Bukkit.getPlayerExact(args[0]) != null)
                    cs.sendMessage(pinger.getOtherCurrentPingMessage(Bukkit.getPlayerExact(args[0])));
                else
                    cs.sendMessage(pinger.getNotOnlineMessage(args[0]));

                return true;
            } else if (args.length == 2) {
                if (args[0].equalsIgnoreCase("average")) {
                    if (Bukkit.getPlayerExact(args[1]) != null)
                        cs.sendMessage(pinger.getOtherAveragePingMessage(Bukkit.getPlayerExact(args[1])));
                    else
                        cs.sendMessage(pinger.getNotOnlineMessage(args[1]));
                }
            }
            cs.sendMessage("§8--- §7Usage of §8/§bping §8---" + System.lineSeparator() +
                    "§8/§bping §8- §7Returns your own current ping" + System.lineSeparator() +
                    "§8/§bping average §8- §7Returns your own average ping" + System.lineSeparator() +
                    "§8/§bping §8<§bplayer§8> - §7Returns the players current ping" + System.lineSeparator() +
                    "§8/§bping average §8<§bplayer§8> - §7Returns the players average ping");
        }
        return false;
    }
}
