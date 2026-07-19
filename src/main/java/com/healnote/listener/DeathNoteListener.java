package com.healnote.listener;

import com.healnote.manager.BookManager;
import com.healnote.manager.CondemnationManager;
import com.healnote.manager.BanManager;
import com.healnote.manager.FateChallengeManager;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerEditBookEvent;
import org.bukkit.inventory.meta.BookMeta;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.Sound;
import org.bukkit.scheduler.BukkitTask;

import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * Handles Death Note book interactions
 */
public class DeathNoteListener implements Listener {
    private final CondemnationManager condemnationManager;
    private final BanManager banManager;
    private final FateChallengeManager fateChallengeManager;
    private final org.bukkit.plugin.java.JavaPlugin plugin;

    public DeathNoteListener(CondemnationManager condemnationManager, BanManager banManager, 
                           FateChallengeManager fateChallengeManager, org.bukkit.plugin.java.JavaPlugin plugin) {
        this.condemnationManager = condemnationManager;
        this.banManager = banManager;
        this.fateChallengeManager = fateChallengeManager;
        this.plugin = plugin;
    }

    @EventHandler
    public void onBookSign(PlayerEditBookEvent event) {
        Player player = event.getPlayer();
        BookMeta bookMeta = event.getNewBookMeta();

        // Check if it's a Death Note
        if (!BookManager.isDeathNote(event.getBook())) {
            return;
        }

        event.setCancelled(true);

        // Get the text from the book
        List<String> pages = bookMeta.getPages();
        if (pages.isEmpty()) {
            player.sendMessage("§c§lErreur: Le livre est vide!");
            return;
        }

        String content = pages.get(0);
        String[] lines = content.split("\n");

        String targetName = null;
        long durationSeconds = 0;

        // Parse the book content
        for (String line : lines) {
            if (line.startsWith("Nom:")) {
                targetName = line.substring(4).trim();
            } else if (line.startsWith("Temps:")) {
                String timeStr = line.substring(6).trim();
                durationSeconds = parseTime(timeStr);
            }
        }

        // Validate inputs
        if (targetName == null || targetName.isEmpty()) {
            player.sendMessage("§c§lErreur: Nom du joueur non spécifié!");
            return;
        }

        if (durationSeconds < 3 || durationSeconds > 3600) {
            player.sendMessage("§c§lErreur: Le temps doit être entre 3 secondes et 1 heure!");
            return;
        }

        // Check if target player exists
        Player targetPlayer = Bukkit.getPlayer(targetName);
        if (targetPlayer == null || !targetPlayer.isOnline()) {
            player.sendMessage("§c§lErreur: Le joueur " + targetName + " n'est pas connecté!");
            return;
        }

        // Check if target is already condemned
        if (condemnationManager.isCondemned(targetName)) {
            player.sendMessage("§c§lErreur: Ce joueur est déjà condamné!");
            return;
        }

        // Check if target has an active Fate Challenge
        if (fateChallengeManager.hasActiveChallenge(targetName)) {
            player.sendMessage("§c§lErreur: Ce joueur a un Défi du Destin actif!");
            return;
        }

        // Add condemnation
        if (!condemnationManager.addCondemned(targetName, durationSeconds, false)) {
            player.sendMessage("§c§lErreur: Impossible de condamner ce joueur!");
            return;
        }

        // Consume the Death Note
        if (player.getInventory().getItemInMainHand().getAmount() > 1) {
            player.getInventory().getItemInMainHand().setAmount(player.getInventory().getItemInMainHand().getAmount() - 1);
        } else {
            player.getInventory().setItemInMainHand(null);
        }

        // Start condemnation effects
        startCondemnation(targetPlayer, durationSeconds);

        // Announce to server
        Bukkit.broadcastMessage("§4§l━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━");
        Bukkit.broadcastMessage("§c§l⚠ CONDAMNATION ⚠");
        Bukkit.broadcastMessage("§c" + targetName + " a été condamné à mort!");
        Bukkit.broadcastMessage("§cTemps restant: " + formatTime(durationSeconds));
        Bukkit.broadcastMessage("§4§l━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━");

        // Play scary sound to all players
        for (Player onlinePlayer : Bukkit.getOnlinePlayers()) {
            onlinePlayer.playSound(onlinePlayer.getLocation(), Sound.ENTITY_WARDEN_HURT, 1.0f, 0.5f);
        }
    }

    private void startCondemnation(Player targetPlayer, long durationSeconds) {
        // Set time to night
        targetPlayer.getWorld().setTime(18000); // Night

        // Start thunder and lightning
        targetPlayer.getWorld().setThunderDuration((int) (durationSeconds * 20));
        targetPlayer.getWorld().setStorm(true);

        // Start the condemnation timer
        BukkitTask task = Bukkit.getScheduler().runTaskTimer(plugin, () -> {
            if (!targetPlayer.isOnline()) {
                condemnationManager.removeCondemned(targetPlayer.getName());
                return;
            }

            CondemnationManager.CondemnationData data = condemnationManager.getCondemnation(targetPlayer.getName());
            if (data == null || data.isExpired()) {
                // Kill the player
                targetPlayer.setHealth(0);
                banManager.banPlayer(targetPlayer.getName());
                Bukkit.broadcastMessage("§4" + targetPlayer.getName() + " a été exécuté par la Death Note!");
                condemnationManager.removeCondemned(targetPlayer.getName());
                return;
            }

            long timeRemaining = data.getTimeRemaining();

            // Apply effects
            targetPlayer.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, 60, 0, false, false));
            targetPlayer.addPotionEffect(new PotionEffect(PotionEffectType.NAUSEA, 60, 0, false, false));

            // Play heart beat sound
            targetPlayer.playSound(targetPlayer.getLocation(), Sound.ENTITY_WARDEN_HEARTBEAT, 0.5f, 1.0f);

            // Lightning strike
            if (Math.random() > 0.7) {
                targetPlayer.getWorld().strikeLightning(targetPlayer.getLocation());
            }

            // Last 30 seconds - deal damage
            if (timeRemaining <= 30) {
                double damage = (30 - timeRemaining) * 0.34; // Progressive damage
                targetPlayer.damage(damage);
                targetPlayer.playSound(targetPlayer.getLocation(), Sound.ENTITY_WARDEN_HURT, 1.0f, 1.0f);
            }
        }, 0L, 20L); // Every second
    }

    private long parseTime(String timeStr) {
        timeStr = timeStr.toLowerCase().trim();
        if (timeStr.endsWith("s")) {
            return Long.parseLong(timeStr.substring(0, timeStr.length() - 1));
        } else if (timeStr.endsWith("m")) {
            return Long.parseLong(timeStr.substring(0, timeStr.length() - 1)) * 60;
        } else if (timeStr.endsWith("h")) {
            return Long.parseLong(timeStr.substring(0, timeStr.length() - 1)) * 3600;
        }
        return 0;
    }

    private String formatTime(long seconds) {
        long hours = seconds / 3600;
        long minutes = (seconds % 3600) / 60;
        long secs = seconds % 60;

        if (hours > 0) {
            return hours + "h " + minutes + "m " + secs + "s";
        } else if (minutes > 0) {
            return minutes + "m " + secs + "s";
        } else {
            return secs + "s";
        }
    }
}
