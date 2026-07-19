package com.healnote.listener;

import com.healnote.manager.BookManager;
import com.healnote.manager.CondemnationManager;
import com.healnote.manager.BanManager;
import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerEditBookEvent;
import org.bukkit.inventory.meta.BookMeta;

import java.util.List;

/**
 * Handles Heal Note book interactions
 */
public class HealNoteListener implements Listener {
    private final CondemnationManager condemnationManager;
    private final BanManager banManager;
    private final BookManager bookManager;

    public HealNoteListener(CondemnationManager condemnationManager, BanManager banManager, BookManager bookManager) {
        this.condemnationManager = condemnationManager;
        this.banManager = banManager;
        this.bookManager = bookManager;
    }

    @EventHandler
    public void onBookSign(PlayerEditBookEvent event) {
        Player player = event.getPlayer();
        BookMeta bookMeta = event.getNewBookMeta();

        // Check if it's a Heal Note using BookManager
        if (!bookManager.isHealNote(event.getBook())) {
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

        // Check if target is condemned
        if (!condemnationManager.isCondemned(targetName)) {
            player.sendMessage("§c§lErreur: Ce joueur n'est pas condamné!");
            return;
        }

        // Save the condemned player
        Player savedPlayer = Bukkit.getPlayer(targetName);
        if (savedPlayer != null && savedPlayer.isOnline()) {
            condemnationManager.removeCondemned(targetName);
            
            // Remove potion effects
            savedPlayer.getActivePotionEffects().clear();
            
            // Announce
            Bukkit.broadcastMessage("§2§l──────────────────────────────────────────");
            Bukkit.broadcastMessage("§a§l✓ SALUT ✓");
            Bukkit.broadcastMessage("§a" + targetName + " a été sauvé par la Heal Note!");
            Bukkit.broadcastMessage("§a§lLe sacrifie du sauveur a eu lieu...");
            Bukkit.broadcastMessage("§2§l──────────────────────────────────────────");
            
            // Play healing sound to saved player
            savedPlayer.playSound(savedPlayer.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 1.0f, 1.0f);
        }

        // Kill the user of Heal Note
        player.setHealth(0);
        banManager.banPlayer(player.getName());

        // Announce to server
        Bukkit.broadcastMessage("§c" + player.getName() + " a été banni pour avoir utilisé la Heal Note!");

        // Consume the Heal Note
        if (player.getInventory().getItemInMainHand().getAmount() > 1) {
            player.getInventory().getItemInMainHand().setAmount(player.getInventory().getItemInMainHand().getAmount() - 1);
        } else {
            player.getInventory().setItemInMainHand(null);
        }
    }
}
