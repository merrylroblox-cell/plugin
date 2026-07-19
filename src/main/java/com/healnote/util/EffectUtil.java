package com.healnote.util;

import org.bukkit.Particle;
import org.bukkit.World;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.entity.Player;

/**
 * Utility class for visual and audio effects
 */
public class EffectUtil {
    
    /**
     * Play condemnation effect at location
     */
    public static void playCondemnationEffect(Location loc) {
        World world = loc.getWorld();
        if (world != null) {
            // Red particles
            world.spawnParticle(Particle.REDSTONE, loc, 20, 0.5, 0.5, 0.5);
            // Lightning
            world.strikeLightning(loc);
        }
    }

    /**
     * Play healing effect at location
     */
    public static void playHealingEffect(Location loc) {
        World world = loc.getWorld();
        if (world != null) {
            // Green particles
            world.spawnParticle(Particle.HAPPY_VILLAGER, loc, 30, 0.5, 0.5, 0.5);
        }
    }

    /**
     * Play fate challenge effect
     */
    public static void playFateChallengeEffect(Location loc) {
        World world = loc.getWorld();
        if (world != null) {
            // Purple particles
            world.spawnParticle(Particle.SOUL, loc, 50, 1, 1, 1);
            world.strikeLightning(loc);
        }
    }
}
