package com.codepunisher.mcaimcore.listeners;

import com.codepunisher.mcaimcore.events.PlayerHitEntityEvent;
import com.codepunisher.mcaimcore.models.HitCause;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.craftbukkit.v1_16_R2.entity.CraftThrownPotion;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.inventory.ItemStack;

/**
 * The purpose of this class
 * is to the make the playerhitentity
 * event function
 *
 * This event is being made to avoid
 * insane amounts of checks in the
 * entitydamagebyentityevent such as
 * projectiles and non projectiles
 */
public class EntityDamage implements Listener
{
    @EventHandler (priority = EventPriority.LOWEST)
    public void onEntityDamage(EntityDamageByEntityEvent event) {
        // Attacker
        Entity damager = event.getDamager();
        HitCause hitCause = HitCause.UNKNOWN;

        if (damager instanceof Player)
        {
            Player player = (Player) damager;

            if (!player.equals(event.getEntity())) {
                ItemStack mainItem = player.getInventory().getItemInMainHand();
                hitCause = HitCause.FIST;

                if (mainItem.getType().toString().toLowerCase().contains("sword"))
                    hitCause = HitCause.SWORD;

                if (mainItem.getType().toString().toLowerCase().contains("shovel") ||
                    mainItem.getType().toString().toLowerCase().contains("axe") ||
                    mainItem.getType().toString().toLowerCase().contains("hoe"))
                    hitCause = HitCause.TOOL;

                PlayerHitEntityEvent e = new PlayerHitEntityEvent(player, event.getEntity(), hitCause, event.getDamage());
                Bukkit.getPluginManager().callEvent(e);

                if (e.getDamage() != event.getDamage())
                    event.setDamage(e.getDamage());

                if (e.isCancelled())
                    event.setCancelled(true);

                return;
            }
        }

        if (event.getDamager() instanceof Projectile) {
            Projectile projectile = (Projectile) event.getDamager();

            if (projectile.getShooter() instanceof Player)
            {
                Player player = (Player) projectile.getShooter();

                if (!player.equals(event.getEntity())) {
                    if (projectile instanceof Arrow)
                        hitCause = HitCause.BOW;

                    if (projectile instanceof CraftThrownPotion)
                        hitCause = HitCause.POTION;

                    if (projectile instanceof Snowball)
                        hitCause = HitCause.SNOWBALL;

                    PlayerHitEntityEvent e = new PlayerHitEntityEvent(player, event.getEntity(), hitCause, event.getDamage());
                    Bukkit.getPluginManager().callEvent(e);

                    if (e.getDamage() != event.getDamage())
                        event.setDamage(e.getDamage());

                    if (e.isCancelled())
                        event.setCancelled(true);
                }
            }
        }
    }

    private boolean isAirOrNull(ItemStack itemStack) { return itemStack == null || itemStack.getType() == Material.AIR; }
}
