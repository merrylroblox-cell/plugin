package com.healnote.manager;

import org.bukkit.Bukkit;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Warden;
import org.bukkit.scheduler.BukkitTask;

import java.util.*;

/**
 * Manages Fate Challenges (Défi du Destin)
 */
public class FateChallengeManager {
    private final Map<String, FateChallengeData> activeChallenges = new HashMap<>();
    private final Map<String, BukkitTask> challengeTasks = new HashMap<>();
    private static final long CHALLENGE_DURATION_TICKS = 54000; // 45 minutes

    public static class FateChallengeData {
        public String bannedPlayer;
        public String targetPlayer; // Player who needs to defeat the guardian
        public UUID guardianUUID;
        public long startTime;
        public boolean isActive;

        public FateChallengeData(String bannedPlayer, String targetPlayer, UUID guardianUUID) {
            this.bannedPlayer = bannedPlayer;
            this.targetPlayer = targetPlayer;
            this.guardianUUID = guardianUUID;
            this.startTime = System.currentTimeMillis();
            this.isActive = true;
        }

        public long getTimeRemaining() {
            long elapsed = (System.currentTimeMillis() - startTime) / 1000;
            long remaining = (CHALLENGE_DURATION_TICKS / 20) - elapsed;
            return Math.max(0, remaining);
        }

        public boolean isExpired() {
            return getTimeRemaining() <= 0;
        }
    }

    /**
     * Start a fate challenge
     */
    public boolean startChallenge(String bannedPlayer, String targetPlayer, UUID guardianUUID) {
        if (activeChallenges.containsKey(bannedPlayer)) {
            return false;
        }

        FateChallengeData challenge = new FateChallengeData(bannedPlayer, targetPlayer, guardianUUID);
        activeChallenges.put(bannedPlayer, challenge);
        return true;
    }

    /**
     * End a fate challenge (victory or defeat)
     */
    public void endChallenge(String bannedPlayer) {
        FateChallengeData challenge = activeChallenges.get(bannedPlayer);
        if (challenge != null) {
            challenge.isActive = false;
            
            // Kill the guardian if still alive
            Entity guardian = Bukkit.getEntity(challenge.guardianUUID);
            if (guardian != null && guardian.isValid()) {
                guardian.remove();
            }
        }
        activeChallenges.remove(bannedPlayer);
        if (challengeTasks.containsKey(bannedPlayer)) {
            challengeTasks.get(bannedPlayer).cancel();
            challengeTasks.remove(bannedPlayer);
        }
    }

    /**
     * Check if a challenge is active
     */
    public boolean hasActiveChallenge(String playerName) {
        FateChallengeData challenge = activeChallenges.get(playerName);
        if (challenge == null) {
            return false;
        }
        
        if (challenge.isExpired()) {
            endChallenge(playerName);
            return false;
        }
        return challenge.isActive;
    }

    /**
     * Get challenge data
     */
    public FateChallengeData getChallenge(String bannedPlayer) {
        return activeChallenges.get(bannedPlayer);
    }

    /**
     * Get all active challenges
     */
    public Set<String> getActiveChallenges() {
        Set<String> active = new HashSet<>(activeChallenges.keySet());
        active.removeIf(playerName -> !hasActiveChallenge(playerName));
        return active;
    }
}
