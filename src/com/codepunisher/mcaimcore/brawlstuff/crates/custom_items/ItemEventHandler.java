package com.codepunisher.mcaimcore.brawlstuff.crates.custom_items;

import com.codepunisher.mcaimcore.events.CraftSuccessEvent;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;

/**
 * The class handler for the
 * listeners regarding custom items
 */
public abstract class ItemEventHandler implements Listener {
    @EventHandler
    public abstract void onBreak(BlockBreakEvent event);

    @EventHandler
    public abstract void onCraft(CraftSuccessEvent event);
}
