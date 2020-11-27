package com.codepunisher.mcaimcore.brawlstuff;

import com.codepunisher.mcaimcore.models.ITask;
import org.bukkit.Material;
import org.bukkit.craftbukkit.v1_16_R3.inventory.CraftInventoryEnchanting;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.enchantment.EnchantItemEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.inventory.EnchantingInventory;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

/**
 * This class makes enchanting just like it was
 * back in 1.7.10 minecraft
 *
 * Basically it removes all levels when
 * enchanting, and it doesn't force the
 * user to have lapis when enchanting
 */
public class OldEnchanting implements Listener {
    /**
     * Removing proper amount of levels
     * @param event enchant item event
     */
    @EventHandler
    public void onEnchant(EnchantItemEvent event) {
        Player player = event.getEnchanter();
        int levelCost = event.getExpLevelCost();
        int oldLevel = player.getLevel();

        // Running a delay here because other
        // wise it removes too many levels
        ITask iTask = new ITask(1) {
            @Override
            public void endTask() { player.setLevel(oldLevel - levelCost); }
        };

        // Starting delayed task
        iTask.startTask(0, false);
    }

    /**
     * Stopping user from removing lapis
     * @param event inventory click event
     */
    @EventHandler
    public void onClick(InventoryClickEvent event) {
        Inventory inv = event.getInventory();

        // Stops them from removing lapis via enchanting menu click
        if (inv instanceof CraftInventoryEnchanting) {
            ItemStack item = event.getCurrentItem();

            // Making sure clicked item exists and is lapis
            if (item == null || item.getType() != Material.LAPIS_LAZULI)
                return;

            event.setCancelled(true);
        }
    }

    /**
     * Adding lapis to menu
     * @param event inventory open event
     */
    @EventHandler
    public void onOpen(InventoryOpenEvent event) {
        Inventory inv = event.getInventory();
        if (inv instanceof EnchantingInventory)
            inv.setItem(1, new ItemStack(Material.LAPIS_LAZULI, 64));
    }

    /**
     * Removing lapis
     * @param event inventory close event
     */
    @EventHandler
    public void onClose(InventoryCloseEvent event) {
        Inventory inv = event.getInventory();

        if (inv instanceof EnchantingInventory)
            inv.setItem(1, new ItemStack(Material.AIR));
    }
}
