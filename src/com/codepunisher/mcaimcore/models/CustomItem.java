package com.codepunisher.mcaimcore.models;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

/**
 * Class for easy custom item creation
 */
public class CustomItem
{
    private final ItemStack item;                                   // The actual item stack
    private Material material;                                      // Material of item stack
    private final String name;                                      // Name on the item stack
    private final List<String> lore;                                // Lore on the item stack

    /**
     * Constructor - NORMAL ITEM
     *
     * @param material material of item
     * @param name name of item
     * @param lore lore on item
     */
    public CustomItem(Material material, String name, String... lore) {
        this.material = material;
        this.name = name;
        this.lore = Arrays.asList(lore);

        ItemStack item = new ItemStack(material);
        ItemMeta meta = item.getItemMeta();

        if (meta != null) {
            if (name != null) {
                meta.setDisplayName(ChatColor.translateAlternateColorCodes('&', name));
            }

            if (lore.length > 0) {
                List<String> newLore = new ArrayList<>();

                for (String string : lore)
                    newLore.add(ChatColor.translateAlternateColorCodes('&', string));

                meta.setLore(newLore);
            }
        }

        item.setItemMeta(meta);
        this.item = item;
    }

    /**
     * Creating custom item
     * based on an already existing item stack
     */
    public CustomItem(ItemStack item, String name, String... lore) {
        this.name = name;
        this.lore = Arrays.asList(lore);
        ItemMeta meta = item.getItemMeta();

        if (meta != null) {
            if (name != null) {
                meta.setDisplayName(ChatColor.translateAlternateColorCodes('&', name));
            }

            if (lore.length > 0) {
                List<String> newLore = new ArrayList<>();

                for (String string : lore)
                    newLore.add(ChatColor.translateAlternateColorCodes('&', string));

                meta.setLore(newLore);
            }
        }

        item.setItemMeta(meta);
        this.item = item;
    }

    /**
     * Constructor - SKULL ITEM
     *
     * @param uuid uuid of player
     * @param name name of item
     * @param lore lore on item
     */
    public CustomItem(UUID uuid, String name, String... lore) {
        this.name = name;
        this.lore = Arrays.asList(lore);
        this.material = Material.PLAYER_HEAD;
        ItemStack item = new ItemStack(Material.PLAYER_HEAD);
        SkullMeta skullMeta = (SkullMeta) item.getItemMeta();

        if (skullMeta != null) {
            Player player = Bukkit.getPlayer(uuid);

            if (player != null) {
                skullMeta.setOwner(player.getName());
            } else {
                skullMeta.setOwner(Bukkit.getOfflinePlayer(uuid).getName());
            }

            if (name != null)
                skullMeta.setDisplayName(ChatColor.translateAlternateColorCodes('&', name));

            if (lore.length > 0) {
                List<String> newLore = new ArrayList<>();

                for (String string : lore)
                    newLore.add(ChatColor.translateAlternateColorCodes('&', string));

                skullMeta.setLore(newLore);
            }
        }

        item.setItemMeta(skullMeta);
        this.item = item;
    }

    /**
     * -------- GETTERS AND SETTERS --------
     */
    public ItemStack getItem() { return this.item; }
    public Material getMaterial() { return this.material; }
    public String getName() { return this.name; }
    public List<String> getLore() { return this.lore; }
}
