package com.healnote.manager;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitTask;

import java.util.*;

/**
 * Manages death sentences and condemnation timers
 */
public class CondemnationManager {
    private final Map<String, CondemnationData> condemnedPlayers = new HashMap<>();
    private final Map<String, BukkitTask> activeTasks = new HashMap<>();

    public static class CondemnationData {
        public String playerName;
        public long endTime;
        public long remainingSeconds;
        public boolean isFateful; // Lié à un Défi du Destin

        public CondemnationData(String playerName, long durationSeconds, boolean isFateful) {
            this.playerName = playerName;
            this.endTime = System.currentTimeMillis() + (durationSeconds * 1000);
            this.remainingSeconds = durationSeconds;
            this.isFateful = isFateful;
        }

        public long getTimeRemaining() {
            return Math.max(0, (endTime - System.currentTimeMillis()) / 1000);
        }

        public boolean isExpired() {
            return System.currentTimeMillis() >= endTime;
        }
    }

    /**
     * Add a condemned player
     * @param playerName Name of the condemned player
     * @param durationSeconds Duration of condemnation (3s - 1h)
     * @param isFateful Whether this is linked to a Fate Challenge
     * @return true if added successfully
     */
    public boolean addCondemned(String playerName, long durationSeconds, boolean isFateful) {
        // Validate duration (3 seconds to 1 hour)
        if (durationSeconds < 3 || durationSeconds > 3600) {
            return false;
        }

        // Check if player is already condemned
        if (condemnedPlayers.containsKey(playerName)) {
            return false;
        }

        condemnedPlayers.put(playerName, new CondemnationData(playerName, durationSeconds, isFateful));
        return true;
    }

    /**
     * Check if a player is condemned
     */
    public boolean isCondemned(String playerName) {
        if (!condemnedPlayers.containsKey(playerName)) {
            return false;
        }

        CondemnationData data = condemnedPlayers.get(playerName);
        if (data.isExpired()) {
            condemnedPlayers.remove(playerName);
            return false;
        }
        return true;
    }

    /**
     * Get condemnation data
     */
    public CondemnationData getCondemnation(String playerName) {
        return condemnedPlayers.get(playerName);
    }

    /**
     * Remove a condemned player (e.g., saved by Heal Note)
     */
    public void removeCondemned(String playerName) {
        condemnedPlayers.remove(playerName);
        if (activeTasks.containsKey(playerName)) {
            activeTasks.get(playerName).cancel();
            activeTasks.remove(playerName);
        }
    }

    /**
     * Get time remaining for a condemned player
     */
    public long getTimeRemaining(String playerName) {
        if (isCondemned(playerName)) {
            return condemnedPlayers.get(playerName).getTimeRemaining();
        }
        return 0;
    }

    /**
     * Get all condemned players
     */
    public Set<String> getCondemnedPlayers() {
        Set<String> condemned = new HashSet<>(condemnedPlayers.keySet());
        condemned.removeIf(playerName -> !isCondemned(playerName));
        return condemned;
    }
}
