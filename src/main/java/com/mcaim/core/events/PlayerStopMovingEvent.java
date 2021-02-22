package com.mcaim.core.events;

import org.bukkit.entity.Player;

public class PlayerStopMovingEvent extends CoreEvent {
    private final Player player;

    public PlayerStopMovingEvent(Player player) { this.player = player; }

    public final Player getPlayer() { return this.player; }
}
