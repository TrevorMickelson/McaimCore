package com.mcaim.core.events;

import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

/**
 * Ths craft success event is ONLY called
 * in the event a user actually successfully
 * crafts an item using a CRAFTING_TABLE,
 * SMITHING_TABLE, or an ANVIL
 */
public class CraftSuccessEvent extends CoreEvent {
    private final Player player;
    private ItemStack result;
    private final int craftAmount;
    private final CraftType craftType;
    private final Inventory inventory;

    public CraftSuccessEvent(Player player, ItemStack result, int craftAmount, CraftType craftType, Inventory inventory) {
        this.player = player;
        this.result = result;
        this.craftAmount = craftAmount;
        this.craftType = craftType;
        this.inventory = inventory;
    }

    public final Player getPlayer() { return this.player; }
    public final int getCraftAmount() { return this.craftAmount; }
    public final CraftType getCraftType() { return this.craftType; }

    public void setResult(ItemStack result) { this.result = result; }
    public ItemStack getResult() { return this.result; }
    public Inventory getInventory() { return this.inventory; }

    /**
     * These are the enumerator
     * craft types used specifically
     * for the craft success event
     *
     * Makes using the event a
     * nice buttery experience
     */
    public enum CraftType {
        CRAFTING_TABLE,
        SMITHING_TABLE,
        ANVIL
    }
}
