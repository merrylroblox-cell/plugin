package com.healnote;

import org.bukkit.plugin.java.JavaPlugin;

public class HealNotePlugin extends JavaPlugin {

    @Override
    public void onEnable() {
        getLogger().info("========================================");
        getLogger().info("HealNote V2 - Starting");
        getLogger().info("========================================");
        
        // TODO: Initialize managers and listeners
        getLogger().info("HealNote V2 loaded successfully!");
    }

    @Override
    public void onDisable() {
        getLogger().info("========================================");
        getLogger().info("HealNote V2 - Disabling");
        getLogger().info("========================================");
        // TODO: Cleanup
    }
}
