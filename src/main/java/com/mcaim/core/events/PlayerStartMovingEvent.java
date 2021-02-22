package com.mcaim.core.events;

import org.bukkit.entity.Player;

public class PlayerStartMovingEvent extends CoreEvent {
    private final Player player;

    public PlayerStartMovingEvent(Player player) { this.player = player; }

    public final Player getPlayer() { return this.player; }
}
