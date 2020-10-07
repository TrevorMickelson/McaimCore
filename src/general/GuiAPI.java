package general;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;

import java.util.Arrays;
import java.util.UUID;

/**
 * Class makes GUIs easier
 */
public class GuiAPI
{
    // Create custom item stack
    public static ItemStack customItem(Material material, String name, String[] lore, ItemFlag flag, boolean hasGlow) {
        ItemStack item = new ItemStack(material);
        ItemMeta meta = item.getItemMeta();

        if (meta != null)
        {
            if (name != null)
                meta.setDisplayName(name);

            if (lore != null)
                meta.setLore(Arrays.asList(lore));

            if (flag != null)
                meta.addItemFlags(flag);

            if (hasGlow) {
                meta.addEnchant(Enchantment.DURABILITY, 1, true);
                meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
            }
        }

        item.setItemMeta(meta);
        return item;
    }

    // Returns player skull
    public static ItemStack getSkull(UUID uuid) {
        ItemStack item = new ItemStack(Material.PLAYER_HEAD);
        SkullMeta skullMeta = (SkullMeta) item.getItemMeta();

        if (skullMeta != null)
        {
            Player player = Bukkit.getPlayer(uuid);

            if (player != null) {
                skullMeta.setOwner(player.getName());
            } else {
                skullMeta.setOwner(Bukkit.getOfflinePlayer(uuid).getName());
            }
        }

        item.setItemMeta(skullMeta);
        return item;
    }
}
