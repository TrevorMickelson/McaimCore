package com.mcaim.core.util;

import net.minecraft.server.v1_16_R3.EntityHuman;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class PlayerUtil {
    // Get uuid of online or offline player
    public static UUID getUUID(String name) {
        Player player = Bukkit.getPlayer(name);
        return player != null ? player.getUniqueId() : Bukkit.getOfflinePlayer(name).getUniqueId();
    }

    // Get name of online or offline player
    public static String getName(UUID uuid) {
        Player player = Bukkit.getPlayer(uuid);
        return player != null ? player.getName() : Bukkit.getOfflinePlayer(uuid).getName();
    }

    /**
     * Gives online or offline
     * player an item stack
     *
     * Takes max stack size into consideration
     */
    public static void giveItem(UUID uuid, ItemStack itemStack, int... amount) {
        Player player = Bukkit.getPlayer(uuid);

        // This allows the "amount" input to work
        // but not go past max stack sizes on items
        List<ItemStack> items = new ArrayList<>();

        // ONLY making this check if the max stack size is 1
        // If attempting to give the player more
        // then the actual item stack max size
        if (amount.length > 0 && amount[0] > itemStack.getMaxStackSize() && itemStack.getMaxStackSize() == 1) {
            for (int i = 1; i <= amount[0]; i++) {
                // Adding new item to list every time
                // the max stack size is reached
                if (i % itemStack.getMaxStackSize() == 0)
                    items.add(itemStack);
            }
        } else {
            itemStack.setAmount(amount.length == 0 ? itemStack.getAmount() : amount[0]);
            items.add(itemStack);
        }

        // Online player
        if (player != null) {
            items.forEach((item) -> {
                Map<Integer, ItemStack> map = player.getInventory().addItem(itemStack);

                if (!map.isEmpty())
                    player.getWorld().dropItemNaturally(player.getLocation(), itemStack);
            });
        } else {
            // Offline player
            OfflinePlayerWrapper playerWrapper = new OfflinePlayerWrapper(uuid);
            EntityHuman entityHuman = playerWrapper.getEntityHuman();
            Inventory inventory = entityHuman.inventory.getOwner().getInventory();

            // Not making a full inventory check here because
            // if they're offline, I can't drop item on ground
            items.forEach((item) -> { inventory.addItem(itemStack); });
            playerWrapper.saveData();
        }
    }
}
