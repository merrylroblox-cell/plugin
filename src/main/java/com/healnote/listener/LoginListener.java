package com.healnote.listener;

import com.healnote.manager.BanManager;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerPreLoginEvent;

/**
 * Handles player login verification for permanent bans
 */
public class LoginListener implements Listener {
    private final BanManager banManager;

    public LoginListener(BanManager banManager) {
        this.banManager = banManager;
    }

    @EventHandler
    public void onPlayerLogin(AsyncPlayerPreLoginEvent event) {
        String playerName = event.getName();

        if (banManager.isBanned(playerName)) {
            event.disallow(AsyncPlayerPreLoginEvent.Result.KICK_BANNED, 
                "\n§c§lVous avez été banni définitivement!\n§7Raison: Death Note System\n\n§aUtilisez le Deban Book pour retrouver votre chance...\n");
        }
    }
}
