package com.codepunisher.mcaimcore.brawlstuff.crates.custom_items;

import com.codepunisher.mcaimcore.brawlstuff.crates.CrateItem;
import com.codepunisher.mcaimcore.events.CraftSuccessEvent;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.CreatureSpawner;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BlockStateMeta;

/**
 * This class handles the pickaxe of containment
 * which allows users to mine a spawner with it
 * based on a certain amount of uses
 *
 * I'm using the display name to dictate the uses
 * mostly because it's just easier that way, no
 * need to store any data anywhere
 */
public class PickaxeOfContainment extends ItemEventHandler {
    @Override
    public void onBreak(BlockBreakEvent event) {
        Block block = event.getBlock();

        CrateItem crateItem = CrateItem.PICKAXE_OF_CONTAINMENT;
        ItemStack inHand = event.getPlayer().getInventory().getItemInMainHand();

        // Making sure they have the pickaxe in hand
        if (crateItem.doesMatch(inHand)) {
            // If the user doesn't mine a spawner
            if (block.getType() != Material.SPAWNER) {
                event.setCancelled(true);
                event.getPlayer().sendMessage(ChatColor.RED + "Sorry, but my programmer only taught me how to mine spawners!");
                return;
            }

            // Setting up spawner based on mined block
            CreatureSpawner cs = (CreatureSpawner) block.getState();
            ItemStack spawner = new ItemStack(cs.getType());
            BlockStateMeta blockStateMeta = (BlockStateMeta) spawner.getItemMeta();

            if (blockStateMeta != null) {
                CreatureSpawner css = (CreatureSpawner) blockStateMeta.getBlockState();
                css.setSpawnedType(cs.getSpawnedType());
                blockStateMeta.setBlockState(css);
            }

            spawner.setItemMeta(blockStateMeta);
            block.setType(Material.AIR);
        }
    }

    @Override
    public void onCraft(CraftSuccessEvent event) {

    }
}
