package com.codepunisher.mcaimcore.api;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

import java.util.UUID;

/**
 * This api class is for
 * important stuff that doesn't
 * really have a category to call
 * home. So I just called it helper
 */
public class HelpersAPI
{
    // Returns string name of UUID if online or offline
    public static String getUUIDName(UUID uuid) {
        Player player = Bukkit.getPlayer(uuid);
        return player != null ? player.getName() : Bukkit.getOfflinePlayer(uuid).getName();
    }

    // Returns UUID from string of player
    public static UUID getStringUUID(String string) {
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
