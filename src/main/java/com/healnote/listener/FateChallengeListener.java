package com.healnote.listener;

import com.healnote.manager.FateChallengeManager;
import com.healnote.manager.BanManager;
import org.bukkit.Bukkit;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Warden;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;

/**
 * Handles Fate Challenge events (Guardian defeat)
 */
public class FateChallengeListener implements Listener {
    private final FateChallengeManager fateChallengeManager;
    private final BanManager banManager;

    public FateChallengeListener(FateChallengeManager fateChallengeManager, BanManager banManager) {
        this.fateChallengeManager = fateChallengeManager;
        this.banManager = banManager;
    }

    @EventHandler
    public void onGuardianDeath(EntityDeathEvent event) {
        Entity entity = event.getEntity();

        // Check if it's a Warden (potential guardian)
        if (!(entity instanceof Warden)) {
            return;
        }

        // Find which challenge this guardian belongs to
        for (String bannedPlayer : fateChallengeManager.getActiveChallenges()) {
            FateChallengeManager.FateChallengeData challenge = fateChallengeManager.getChallenge(bannedPlayer);
            if (challenge != null && challenge.guardianUUID.equals(entity.getUniqueId())) {
                // Guardian defeated!
                handleVictory(bannedPlayer, challenge);
                return;
            }
        }
    }

    private void handleVictory(String bannedPlayer, FateChallengeManager.FateChallengeData challenge) {
        // Unban the player
        banManager.unbanPlayer(bannedPlayer);
        fateChallengeManager.endChallenge(bannedPlayer);

        // Announce victory
        Bukkit.broadcastMessage("§a§l━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━");
        Bukkit.broadcastMessage("§a§l✓ VICTOIRE ✓");
        Bukkit.broadcastMessage("§a" + challenge.targetPlayer + " a vaincu le Gardien du Destin!");
        Bukkit.broadcastMessage("§a" + bannedPlayer + " a été débanni!");
        Bukkit.broadcastMessage("§a§l━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━");
    }
}
