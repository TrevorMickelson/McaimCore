package events;

import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class PlayerStartMovingEvent extends Event
{
    private static final HandlerList handlers = new HandlerList();

    private final Player player;

    public PlayerStartMovingEvent(Player player) { this.player = player; }

    public final Player getPlayer() { return this.player; }

    @Override
    public HandlerList getHandlers() { return handlers; }

    public static HandlerList getHandlerList() { return handlers; }
}
