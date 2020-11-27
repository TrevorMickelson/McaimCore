package com.codepunisher.mcaimcore.brawlstuff;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityRegainHealthEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

/**
 * This class handles the soup pvp
 * poggers bruhhh poggerssss!!!
 */
public class SoupHeal implements Listener
{
    // Max player health
    private static final int MAX_HEALTH = 20;

    // Max food level
    private static final int MAX_FOOD_LEVEL = 20;

    // Restore values
    private static final double HEALTH_RESTORE = 7;
    private static final int FOOD_RESTORE = 7;
    private static final float SATURATION_RESTORE = 7.2F;

    /**
     * This method handles the literal
     * souping that goes down
     *
     * @param event player interact event
     */
    @EventHandler (priority = EventPriority.HIGHEST)
    public void onInteract(PlayerInteractEvent event) {
        if (event.getAction() == Action.RIGHT_CLICK_AIR || event.getAction() == Action.RIGHT_CLICK_BLOCK) {
            ItemStack item = event.getItem();

            // Making sure item exists
            if (item != null) {
                if (item.getType() == Material.MUSHROOM_STEW) {
                    // Canceling event
                    event.setCancelled(true);

                    Player player = event.getPlayer();
                    double health = player.getHealth();
                    boolean updated = false;

                    // If I need to heal them
                    if (health < MAX_HEALTH) {
                        // Calling the regain health event
                        EntityRegainHealthEvent healthEvent = new EntityRegainHealthEvent(player, HEALTH_RESTORE, EntityRegainHealthEvent.RegainReason.CUSTOM);

                        if (!healthEvent.isCancelled()) {
                            updated = true;
                            player.setHealth(health + HEALTH_RESTORE > MAX_HEALTH ? MAX_HEALTH : health + healthEvent.getAmount());
                        }

                        Bukkit.getPluginManager().callEvent(healthEvent);
                    }

                    // If I need to feed them, I'm feeding them yo
                    if (player.getFoodLevel() < MAX_FOOD_LEVEL) {
                        player.setFoodLevel(player.getFoodLevel() + FOOD_RESTORE);
                        player.setSaturation(player.getSaturation() + SATURATION_RESTORE);
                        updated = true;
                    }

                    if (updated) {
                        item.setType(Material.BOWL);
                        player.playSound(player.getLocation(), Sound.ENTITY_PLAYER_BURP, 1.0f, 1.0f);
                    }
                }

                if (item.getType() == Material.DIAMOND)
                    event.getPlayer().setFlySpeed(1);

                if (item.getType() == Material.EMERALD)
                    event.getPlayer().setFlySpeed((float) 0.1);
            }
        }
    }
}
