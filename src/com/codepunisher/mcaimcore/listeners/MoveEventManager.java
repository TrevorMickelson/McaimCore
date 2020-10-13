package com.codepunisher.mcaimcore.listeners;

import com.codepunisher.mcaimcore.events.PlayerStopMovingEvent;
import com.codepunisher.mcaimcore.events.PlayerStartMovingEvent;
import org.apache.commons.lang.mutable.MutableInt;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class MoveEventManager implements Listener, Runnable {

    private static final int TICKS_UNTIL_STANDING = 2;
    private static boolean registered = false;

    // This will increment by one each tick
    private final Map<Player, MutableInt> standingTickTimers;

    // This registers the class as Listener and BukkitRunnable
    public static void register(final JavaPlugin plugin) {
        if (MoveEventManager.registered) {
            return;
        }

        final MoveEventManager eventManager = new MoveEventManager();
        Bukkit.getPluginManager().registerEvents(eventManager, plugin);
        Bukkit.getScheduler().runTaskTimer(plugin, eventManager, 0, 1);
        MoveEventManager.registered = true;
    }

    // Constructor
    private MoveEventManager() {
        this.standingTickTimers = new HashMap<>();
    }

    // Util to check if player moved or if he is just looking around
    private boolean hasPositionMoved(final Location from, final Location to) {
        return from.getX() != to.getX() || from.getY() != to.getY() || from.getX() != to.getY();
    }

    // Add player timer on join
    @EventHandler
    public void onJoin(final PlayerJoinEvent event) {
        this.standingTickTimers.put(event.getPlayer(), new MutableInt(0));
    }

    // Remove player timer on quit
    @EventHandler
    public void onQuit(final PlayerQuitEvent event) {
        this.standingTickTimers.remove(event.getPlayer());
    }

    @EventHandler
    public void onMove(final PlayerMoveEvent event) {
        final Player player = event.getPlayer();
        if (this.hasPositionMoved(event.getFrom(), Objects.requireNonNull(event.getTo()))) {
            final MutableInt counter = this.standingTickTimers.get(player);
            if (counter.intValue() > MoveEventManager.TICKS_UNTIL_STANDING) {
                final PlayerStartMovingEvent startEvent = new PlayerStartMovingEvent(player);
                Bukkit.getPluginManager().callEvent(startEvent);
            }
            counter.setValue(0);
        }
    }

    // Every player timer will be incremented at the start of each tick
    @Override
    public void run() {
        for (final Map.Entry<Player, MutableInt> entry : this.standingTickTimers.entrySet()) {
            final MutableInt counter = entry.getValue();
            if (counter.intValue() == MoveEventManager.TICKS_UNTIL_STANDING) {
                final PlayerStopMovingEvent stopEvent = new PlayerStopMovingEvent(entry.getKey());
                Bukkit.getPluginManager().callEvent(stopEvent);
            }
            counter.increment();
        }
    }
}
