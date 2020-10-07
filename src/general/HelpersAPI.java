package general;

import org.bukkit.Bukkit;
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

        if (player != null)
        {
            return player.getName();
        }
        else
        {
            return Bukkit.getOfflinePlayer(uuid).getName();
        }
    }

    // Returns UUID from string of player
    public static UUID getStringUUID(String string) {
        Player player = Bukkit.getPlayer(string);

        if (player != null)
        {
            return player.getUniqueId();
        }
        else
        {
            return Bukkit.getOfflinePlayer(string).getUniqueId();
        }
    }
}
