package com.healnote.listener;

import com.healnote.manager.BookManager;
import com.healnote.manager.BanManager;
import com.healnote.manager.FateChallengeManager;
import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.entity.Warden;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerEditBookEvent;
import org.bukkit.inventory.meta.BookMeta;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.List;

/**
 * Handles Deban Book interactions
 */
public class DebanBookListener implements Listener {
    private final BanManager banManager;
    private final FateChallengeManager fateChallengeManager;
    private final BookManager bookManager;
    private final JavaPlugin plugin;

    public DebanBookListener(BanManager banManager, FateChallengeManager fateChallengeManager, 
                           BookManager bookManager, JavaPlugin plugin) {
        this.banManager = banManager;
        this.fateChallengeManager = fateChallengeManager;
        this.bookManager = bookManager;
        this.plugin = plugin;
    }

    @EventHandler
    public void onBookSign(PlayerEditBookEvent event) {
        Player player = event.getPlayer();
        BookMeta bookMeta = event.getNewBookMeta();

        // Check if it's a Deban Book using BookManager
        if (!bookManager.isDebanBook(event.getBook())) {
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

        // Parse the book content
        for (String line : lines) {
            if (line.startsWith("Nom:")) {
                targetName = line.substring(4).trim();
                break;
            }
        }

        // Validate inputs
        if (targetName == null || targetName.isEmpty()) {
            player.sendMessage("§c§lErreur: Nom du joueur non spécifié!");
            return;
        }

        // Check if target is banned
        if (!banManager.isBanned(targetName)) {
            player.sendMessage("§c§lErreur: Ce joueur n'est pas banni!");
            return;
        }

        // Start the Fate Challenge
        startFateChallenge(player, targetName);

        // Consume the Deban Book
        if (player.getInventory().getItemInMainHand().getAmount() > 1) {
            player.getInventory().getItemInMainHand().setAmount(player.getInventory().getItemInMainHand().getAmount() - 1);
        } else {
            player.getInventory().setItemInMainHand(null);
        }
    }

    private void startFateChallenge(Player player, String bannedPlayerName) {
        // Spawn the Fate Guardian (Warden)
        Warden guardian = player.getWorld().spawn(player.getLocation().add(0, 2, 0), Warden.class);
        guardian.setCustomName("§5§lGardien du Destin");
        guardian.setCustomNameVisible(true);

        // Enhance the guardian
        guardian.setMaxHealth(384); // 6x normal Warden (64 * 6)
        guardian.setHealth(384);
        guardian.getAttribute(org.bukkit.attribute.Attribute.GENERIC_ATTACK_DAMAGE).setBaseValue(32); // 4x normal
        guardian.getAttribute(org.bukkit.attribute.Attribute.GENERIC_MOVEMENT_SPEED).setBaseValue(0.4);

        // Start the challenge
        fateChallengeManager.startChallenge(bannedPlayerName, player.getName(), guardian.getUniqueId());

        // Announce
        Bukkit.broadcastMessage("§5§l──────────────────────────────────────────");
        Bukkit.broadcastMessage("§5§l⚔ DÉFI DU DESTIN ⚔");
        Bukkit.broadcastMessage("§5Le Gardien du Destin est apparu!");
        Bukkit.broadcastMessage("§5" + player.getName() + " a 45 minutes pour le vaincre.");
        Bukkit.broadcastMessage("§5Si réussi: " + bannedPlayerName + " sera débanni.");
        Bukkit.broadcastMessage("§5Si échoué: " + bannedPlayerName + " reste banni définitivement.");
        Bukkit.broadcastMessage("§5§l──────────────────────────────────────────");

        // Play epic sound
        for (Player onlinePlayer : Bukkit.getOnlinePlayers()) {
            onlinePlayer.playSound(onlinePlayer.getLocation(), Sound.ENTITY_WARDEN_EMERGE, 1.0f, 1.0f);
        }
    }
}
