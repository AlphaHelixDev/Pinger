/*
 * Copyright (c) AlphaHelix 2017
 */

package de.alphahelix.alphaapi.reflection;

public enum MinecraftVersion {

    EIGHT,
    NINE,
    TEN,
    ELEVEN;

    private static MinecraftVersion server;

    static {
        if (ReflectionUtil.getVersion().contains("1_8")) {
            server = EIGHT;
        } else if (ReflectionUtil.getVersion().contains("1_9")) {
            server = NINE;
        } else if (ReflectionUtil.getVersion().contains("1_10")) {
            server = TEN;
        } else if (ReflectionUtil.getVersion().contains("1_11")) {
            server = ELEVEN;
        }
    }

    public static MinecraftVersion getServer() {
        return server;
    }

}
