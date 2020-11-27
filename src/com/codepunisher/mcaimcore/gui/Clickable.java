package com.codepunisher.mcaimcore.gui;

import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.ItemStack;

/**
 * This is an interface for clickable buttons via a menu
 * this makes it very easy to add clickable buttons
 */
public interface Clickable {
    void onClick(int slot, ItemStack cursor, ClickType clickType);
}
