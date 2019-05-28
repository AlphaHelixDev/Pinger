/*
 * Copyright (c) AlphaHelix 2017
 */
package de.alphahelix.alphaapi.command;

import de.alphahelix.alphaapi.AlphaAPI;
import de.alphahelix.alphaapi.reflection.ReflectionUtil;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandMap;
import org.bukkit.command.CommandSender;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public abstract class SimpleCommand extends Command {

    private final AlphaAPI plugin;

    /**
     * Creates a new {@link SimpleCommand} to not manually implement the Command inside the plugin.yml
     *
     * @param command     the command name
     * @param description the description which should be printed out at '/help commandName'
     * @param aliases     the aliases which can be used to run the command as well
     */
    public SimpleCommand(String command, String description, String... aliases) {
        super(command);
        this.plugin = AlphaAPI.getInstance();

        super.setDescription(description);
        List<String> aliasList = new ArrayList<>();
        Collections.addAll(aliasList, aliases);
        super.setAliases(aliasList);

        this.register();
    }

    void register() {
        try {
            Field f = ReflectionUtil.getCraftBukkitClass("CraftServer")
                    .getDeclaredField("commandMap");
            f.setAccessible(true);

            CommandMap map = (CommandMap) f.get(Bukkit.getServer());
            map.register(plugin.getName(), this);
        } catch (Exception exc) {
            exc.printStackTrace();
        }
    }

    /**
     * Everything what should get run when the command is executed
     */
    @Override
    public abstract boolean execute(CommandSender cs, String label, String[] args);

    /**
     * Suggestions in the chat when you press TAB
     */
    @Override
    public abstract List<String> tabComplete(CommandSender cs, String label, String[] args);

    /**
     * Create a {@link String} out of a {@link String[]}
     *
     * @param args  The {@link String[]} which should be a {@link String}
     * @param start At which index of the array the {@link String} should start
     * @return the new created {@link String}
     */
    public String buildString(String[] args, int start) {
        String str = "";
        if (args.length > start) {
            str += args[start];
            for (int i = start + 1; i < args.length; i++) {
                str += " " + args[i];
            }
        }
        return str;
    }

    /**
     * Gets the plugin
     *
     * @return the defined {@link AlphaAPI}
     */
    public AlphaAPI getAPI() {
        return this.plugin;
    }
}
