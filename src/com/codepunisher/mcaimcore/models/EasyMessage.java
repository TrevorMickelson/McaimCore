package com.codepunisher.mcaimcore.models;

import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.Sound;
import org.bukkit.entity.Player;

import java.util.UUID;

/**
 * This class handles easy player messaging
 *
 * I made this class because I got SERIOUSLY
 * FUCKING TIRED OF DOING THIS MANUALLY
 */
public class EasyMessage
{
    private final Player player;                                    // Player to message

    // Constructor
    public EasyMessage(Player player) {
        this.player = player;
    }

    // Messaging single player
    public void send(String type, String message, Sound... sounds) {
        switch (type.toUpperCase()) {
            case "ACTIONBAR":
                player.spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacyText(ChatColor.translateAlternateColorCodes('&', message)));
                break;

            case "CHAT":
                player.sendMessage(ChatColor.translateAlternateColorCodes('&', message));
                break;

            case "TITLE":
                player.sendTitle(ChatColor.translateAlternateColorCodes('&', message), "", 1, 60, 1);
                break;
        }

        if (sounds.length > 0) {
            for (Sound sound : sounds)
                player.playSound(player.getLocation(), sound, 1.0f, 1.0f);
        }
    }

    // Returns string name of UUID if online or offline
    public String getUUIDName(UUID uuid) {
        Player player = Bukkit.getPlayer(uuid);
        return player != null ? player.getName() : Bukkit.getOfflinePlayer(uuid).getName();
    }

    // Returns UUID from string of player
    public UUID getStringUUID(String string) {
        Player player = Bukkit.getPlayer(string);

        if (player != null) {
            return player.getUniqueId();
        } else {
            // Only creating UUID object of the user has played before
            OfflinePlayer offlinePlayer = Bukkit.getOfflinePlayer(string);
            return offlinePlayer.hasPlayedBefore() ? offlinePlayer.getUniqueId() : null;
        }
    }
}
