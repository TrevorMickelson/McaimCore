package events;

import models.HitCause;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class PlayerHitEntityEvent extends Event implements Cancellable
{
    private static final HandlerList handlers = new HandlerList();

    private final Player player;
    private final Entity entity;
    private final HitCause hitCause;
    private boolean cancelled;

    public PlayerHitEntityEvent(final Player player, final Entity entity, final HitCause hitCause) {
        this.player = player;
        this.entity = entity;
        this.hitCause = hitCause;
        this.cancelled = false;
    }

    public final Player getPlayer() { return this.player; }
    public final Entity getEntity() { return this.entity; }
    public final HitCause getHitCause() { return this.hitCause; }

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
