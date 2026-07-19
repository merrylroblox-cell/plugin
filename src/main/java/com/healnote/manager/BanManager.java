package com.healnote.manager;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.*;

/**
 * Manages permanent bans for HealNote V2
 */
public class BanManager {
    private final Set<String> permanentlyBanned = new HashSet<>();

    /**
     * Ban a player permanently
     */
    public void banPlayer(String playerName) {
        permanentlyBanned.add(playerName);
        
        // Kick the player if online
        Player player = Bukkit.getPlayer(playerName);
        if (player != null && player.isOnline()) {
            player.kick("§c§lVous avez été banni de ce serveur.\n§7Raison: Death Note");
        }
    }

    /**
     * Unban a player
     */
    public void unbanPlayer(String playerName) {
        permanentlyBanned.remove(playerName);
    }

    /**
     * Check if a player is banned
     */
    public boolean isBanned(String playerName) {
        return permanentlyBanned.contains(playerName);
    }

    /**
     * Get all banned players
     */
    public Set<String> getBannedPlayers() {
        return new HashSet<>(permanentlyBanned);
    }

    /**
     * Check if a banned player can join
     */
    public boolean canJoin(String playerName) {
        return !isBanned(playerName);
    }
}
