package com.healnote.util;

import org.bukkit.Bukkit;

/**
 * Utility class for logging and messaging
 */
public class Logger {
    private static final String PREFIX = "§7[§5HealNote§7]§r ";

    public static void info(String message) {
        Bukkit.getLogger().info(PREFIX + message);
    }

    public static void warn(String message) {
        Bukkit.getLogger().warning(PREFIX + message);
    }

    public static void error(String message) {
        Bukkit.getLogger().severe(PREFIX + message);
    }
}
