package com.codepunisher.mcaimcore.brawlstuff.crates;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

/**
 * Handles actually opening the crate
 */
public class OpenListener implements Listener {
    @EventHandler
    public void onCrateOpen(PlayerInteractEvent event) {
        ItemStack crateKey = event.getItem();

        // Making sure player is right clicking a block with a trip wire hook
        if (crateKey == null /*|| crateKey.getType() != Material.TRIPWIRE_HOOK*/ || event.getAction() != Action.RIGHT_CLICK_BLOCK)
            return;

        // Giving key (for now)
        if (crateKey.getType() == Material.DIAMOND) {
            Inventory inv = event.getPlayer().getInventory();
            inv.addItem(CrateType.TEAM_CHALLENGES.getKey(1));
            inv.addItem(CrateType.KOTH.getKey(1));
            inv.addItem(CrateType.WEEKLY.getKey(1));
            return;
        }

        // Making sure player is right clicking a chest
        Block block = event.getClickedBlock();
        if (block == null || !block.getType().toString().contains("CHEST"))
            return;

        // Getting crate type
        CrateType crateType = getCrateType(crateKey);

        // Making sure it exists
        if (crateType != null) {
            // Rewarding player
            crateType.giveRandomReward(event.getPlayer());
        }
    }

    // Getting the crate type in players hand
    // Returns null if the item is not a crate key
    private CrateType getCrateType(ItemStack item) {
        for (CrateType crateType : CrateType.values()) {
            if (crateType.doesMatch(item))
                return crateType;
        }

        return null;
    }
}
