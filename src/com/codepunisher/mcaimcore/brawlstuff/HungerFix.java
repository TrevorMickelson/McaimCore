package com.codepunisher.mcaimcore.brawlstuff;

import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.EntityRegainHealthEvent;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import java.util.*;

/**
 * The purpose of this class is to nerf how quickly
 * players get hungry when they are not at full health
 * this is attempting to make it more like it was in 1.8
 * Because now in 1.16, it's pretty fucking aids
 */
public class HungerFix implements Listener
{
    /** Storing players that take damage (and are below full health) */
    private final List<UUID> damageTaken = new ArrayList<>();

    // Amount to multiply saturation by (when below full health)
    private final int INCREASE_AMOUNT = 10;

    // Increasing saturation on damage event
    @EventHandler (priority = EventPriority.LOWEST)
    public void onEntityDamage(EntityDamageEvent event) {
        Entity entity = event.getEntity();

        if (entity instanceof Player) {
            Player player = (Player) entity;
            increaseSaturation(player, true);
        }
    }

    // Increasing saturation on join event
    @EventHandler (priority = EventPriority.LOWEST)
    public void onJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();

        // If I actually reset the saturation here
        if (resetSaturation(player))
            return;

        increaseSaturation(player, false);
    }

    // Here I'm updating the player saturation value
    // back to normal because now their health is full
    // and I don't need it to be too high
    @EventHandler (priority = EventPriority.LOWEST)
    public void onHealthRegain(EntityRegainHealthEvent event) {
        Entity entity = event.getEntity();

        if (entity instanceof Player) {
            Player player = (Player) entity;

            // Checking if I need to remove them/update saturation value
            if (player.getHealth() + event.getAmount() >= 19) {
                resetSaturation(player);
            }
        }
    }

    // Resetting saturation if they eat food and go up to
    // max food level because now saturation can be reset
    @EventHandler (priority = EventPriority.LOWEST)
    public void onEat(FoodLevelChangeEvent event) {
        Entity entity = event.getEntity();

        if (entity instanceof Player) {
            Player player = (Player) entity;

            // Only resetting if their hunger is full
            if (player.getFoodLevel() + event.getFoodLevel() >= 20)
                resetSaturation(player);
        }
    }

    // Removing player from map and resetting saturation value
    @EventHandler (priority = EventPriority.LOWEST)
    public void onLeave(PlayerQuitEvent event) {
        resetSaturation(event.getPlayer());
    }

    // Removing player from map and resetting saturation value
    @EventHandler (priority = EventPriority.LOWEST)
    public void onDeath(EntityDeathEvent event) {
        Entity entity = event.getEntity();

        if (entity instanceof Player) {
            Player player = (Player) entity;
            resetSaturation(player);
        }
    }

    /**
     * Increasing player saturation value when
     * below full health. Then I'm storing them
     * so that I can update their saturation value
     * back to normal on health regain event
     *
     * I'm running this in various events
     *
     * @param player player to check for
     */
    private void increaseSaturation(Player player, boolean tookDamage) {
        // If player health is below max or I'm
        // already certain they took damage
        // I'm doing this because if I just check the health
        // every time, the health doesn't update fast enough
        // via the entity damage event
        if (tookDamage || player.getHealth() < 20) {
            UUID uuid = player.getUniqueId();

            // Storing player (if they aren't stored)
            if (!damageTaken.contains(uuid)) {
                damageTaken.add(uuid);

                // Not increasing if saturation is less than 1
                if (player.getSaturation() >= 1)
                    player.setSaturation(player.getSaturation() * INCREASE_AMOUNT);
            }
        }
    }

    /**
     * Resetting players saturation value
     * to what it should be by default
     *
     * @param player player to check for
     */
    private boolean resetSaturation(Player player) {
        float saturation = player.getSaturation();

        // Only updating if I need to
        if (saturation >= 15) {
            player.setSaturation(player.getSaturation() / INCREASE_AMOUNT);
            return true;
        }

        // Always making sure that I remove them here
        damageTaken.remove(player.getUniqueId());
        return false;
    }
}
