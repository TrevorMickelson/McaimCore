package com.codepunisher.mcaimcore.events;

import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.bukkit.inventory.ItemStack;

/**
 * Ths craft success event is ONLY called
 * in the event a user actually successfully
 * crafts an item using a CRAFTING_TABLE,
 * SMITHING_TABLE, or an ANVIL
 */
public class CraftSuccessEvent extends Event implements Cancellable {
    private static final HandlerList handlers = new HandlerList();

    private final Player player;
    private ItemStack result;
    private final int craftAmount;
    private final CraftType craftType;
    private boolean cancelled;

    public CraftSuccessEvent(Player player, ItemStack result, int craftAmount, CraftType craftType) {
        this.player = player;
        this.result = result;
        this.craftAmount = craftAmount;
        this.craftType = craftType;
    }

    public final Player getPlayer() { return this.player; }
    public final int getCraftAmount() { return this.craftAmount; }
    public final CraftType getCraftType() { return this.craftType; }

    public void setResult(ItemStack result) { this.result = result; }
    public ItemStack getResult() { return this.result; }

    @Override
    public HandlerList getHandlers() { return handlers; }

    public static HandlerList getHandlerList() { return handlers; }

    @Override
    public boolean isCancelled() {
        return cancelled;
    }

    @Override
    public void setCancelled(boolean cancelled) { this.cancelled = cancelled; }

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
