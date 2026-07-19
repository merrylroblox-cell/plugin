package com.healnote;

import com.healnote.manager.*;
import com.healnote.listener.*;
import org.bukkit.plugin.java.JavaPlugin;

/**
 * HealNote V2 - Main plugin class
 * A Death Note inspired plugin with special books and permanent ban system
 */
public class HealNotePlugin extends JavaPlugin {
    private CondemnationManager condemnationManager;
    private BanManager banManager;
    private FateChallengeManager fateChallengeManager;
    private BookManager bookManager;
    private CraftManager craftManager;

    @Override
    public void onEnable() {
        getLogger().info("========================================");
        getLogger().info("HealNote V2 - Starting v2.0.0");
        getLogger().info("========================================");

        try {
            // Initialize managers
            condemnationManager = new CondemnationManager();
            banManager = new BanManager();
            fateChallengeManager = new FateChallengeManager();
            bookManager = new BookManager(this);
            craftManager = new CraftManager(this);

            getLogger().info("✓ Managers initialized");

            // Register listeners
            getServer().getPluginManager().registerEvents(new DeathNoteListener(
                condemnationManager, banManager, fateChallengeManager, bookManager, this
            ), this);
            getLogger().info("✓ Death Note listener registered");

            getServer().getPluginManager().registerEvents(new HealNoteListener(
                condemnationManager, banManager, bookManager
            ), this);
            getLogger().info("✓ Heal Note listener registered");

            getServer().getPluginManager().registerEvents(new DebanBookListener(
                banManager, fateChallengeManager, bookManager, this
            ), this);
            getLogger().info("✓ Deban Book listener registered");

            getServer().getPluginManager().registerEvents(new FateChallengeListener(
                fateChallengeManager, banManager
            ), this);
            getLogger().info("✓ Fate Challenge listener registered");

            getServer().getPluginManager().registerEvents(new LoginListener(banManager), this);
            getLogger().info("✓ Login listener registered");

            getLogger().info("========================================");
            getLogger().info("HealNote V2 loaded successfully!");
            getLogger().info("========================================");
            getLogger().info("");
            getLogger().info("Features enabled:");
            getLogger().info("  • Death Note - Condemn players to death");
            getLogger().info("  • Heal Note - Save condemned players");
            getLogger().info("  • Deban Book - Challenge the Fate Guardian");
            getLogger().info("  • Permanent Ban System");
            getLogger().info("  • Fate Challenge (Défi du Destin)");
            getLogger().info("  • PersistentDataContainer Security");
            getLogger().info("");

        } catch (Exception e) {
            getLogger().severe("Failed to enable HealNote V2!");
            e.printStackTrace();
            getServer().getPluginManager().disablePlugin(this);
        }
    }

    @Override
    public void onDisable() {
        getLogger().info("========================================");
        getLogger().info("HealNote V2 - Disabling");
        getLogger().info("========================================");

        try {
            // Cleanup if needed
            if (condemnationManager != null) {
                getLogger().info("✓ Condemnation manager cleaned up");
            }
            if (banManager != null) {
                getLogger().info("✓ Ban manager cleaned up");
            }
            if (fateChallengeManager != null) {
                getLogger().info("✓ Fate Challenge manager cleaned up");
            }

            getLogger().info("========================================");
            getLogger().info("HealNote V2 disabled successfully!");
            getLogger().info("========================================");

        } catch (Exception e) {
            getLogger().severe("Error during plugin shutdown!");
            e.printStackTrace();
        }
    }

    // Getters for managers
    public CondemnationManager getCondemnationManager() {
        return condemnationManager;
    }

    public BanManager getBanManager() {
        return banManager;
    }

    public FateChallengeManager getFateChallengeManager() {
        return fateChallengeManager;
    }

    public BookManager getBookManager() {
        return bookManager;
    }

    public CraftManager getCraftManager() {
        return craftManager;
    }
}
