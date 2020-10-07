package events;

import models.Armor;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class ArmorEquipEvent extends Event implements Cancellable
{
    private static final HandlerList handlers = new HandlerList();

    private final Player player;
    private final Armor armor;
    private boolean cancelled;

    public ArmorEquipEvent(final Player player, final Armor armor) {
        this.player = player;
        this.armor = armor;
        this.cancelled = false;
    }

    public final Player getPlayer() { return this.player; }
    public Armor getArmor() { return this.armor; }

    @Override
    public HandlerList getHandlers() { return handlers; }

    public static HandlerList getHandlerList() { return handlers; }

    @Override
    public boolean isCancelled() {
        return cancelled;
    }

    @Override
    public void setCancelled(boolean cancelled) { this.cancelled = cancelled; }
}
