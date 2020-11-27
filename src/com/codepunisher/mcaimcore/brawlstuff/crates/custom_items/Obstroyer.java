package com.codepunisher.mcaimcore.brawlstuff.crates.custom_items;

import com.codepunisher.mcaimcore.brawlstuff.crates.CrateItem;
import com.codepunisher.mcaimcore.events.CraftSuccessEvent;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;

/**
 * Block break event for the obstroyer
 * pickaxe, which ONLY mines obsidian
 * because it has efficiency 10 on it
 *
 * Also not allowing them to use it in
 * an anvil (to avoid removing enchant)
 */
public class Obstroyer extends ItemEventHandler {
    @Override
    public void onBreak(BlockBreakEvent event) {
        Bukkit.broadcastMessage("1");
        Block block = event.getBlock();

        // Making sure the player has custom item
        Player player = event.getPlayer();
        ItemStack inHand = player.getInventory().getItemInMainHand();

        // Obstroyer enum
        CrateItem obstroyer = CrateItem.OBSTROYER;

        // If the items match
        if (obstroyer.doesMatch(inHand)) {
            // Stopping the player if they aren't mining obby
            if (block.getType() != Material.OBSIDIAN)
                event.setCancelled(true);
        }
    }

    @Override
    public void onCraft(CraftSuccessEvent event) {
        if (event.getCraftType() == CraftSuccessEvent.CraftType.ANVIL) {
            if (CrateItem.OBSIDIAN.doesMatch(event.getResult()))
                event.setCancelled(true);
        }
    }
}
