package com.codepunisher.mcaimcore.brawlstuff;

import com.codepunisher.mcaimcore.events.CraftSuccessEvent;
import com.codepunisher.mcaimcore.models.ITask;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.CraftItemEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.Objects;

public class CraftFix implements Listener {
    /**
     * Handles the craft item checker
     *
     * The amount of items crafted changes
     * based on how the user actually tries
     * to remove the item from the crafting
     * menu (which is why I have so many checks)
     *
     * @param event craft item event
     */
    @EventHandler(priority = EventPriority.HIGHEST)
    public void onCraft(CraftItemEvent event) {
        Player player = (Player) event.getWhoClicked();
        Inventory inv = event.getInventory();
        ItemStack result = event.getRecipe().getResult();

        int amount = 0;
        // How the user tries to remove item
        switch (event.getClick()) {
            case NUMBER_KEY:
                ItemStack hotBarItem = player.getInventory().getItem(event.getHotbarButton());
                amount = hotBarItem == null || hotBarItem.getType() == Material.AIR ? result.getAmount() : 0;
                break;

            case LEFT:
            case RIGHT:
            case DROP:
            case CONTROL_DROP:
                amount = result.getAmount();
                break;

            case SWAP_OFFHAND:
                amount = player.getInventory().getItemInOffHand().getType() == Material.AIR ? result.getAmount() : 0;
                break;

            case SHIFT_LEFT:
            case SHIFT_RIGHT:
                // Determining this by getting
                // the minimum stack value
                int min = Integer.MAX_VALUE;
                for (int i = 0; i < inv.getContents().length; i++) {
                    ItemStack item = inv.getItem(i);
                    if (item == null || item.getType() == Material.AIR)
                        continue;

                    if (item.getAmount() < min && i != 0)
                        min = item.getAmount();
                }

                // Getting the integer space amount of room
                // item stack can fit into the players inventory
                int spaceForItem = 0;
                for (int i = 0; i < player.getInventory().getContents().length; i++) {
                    ItemStack item = player.getInventory().getItem(i);
                    if ((item == null || item.getType() == Material.AIR) && i <= 35) {
                        spaceForItem += result.getMaxStackSize();
                        continue;
                    }

                    if (item != null && item.getType() == result.getType()) {
                        if (item.getAmount() < item.getMaxStackSize())
                            spaceForItem += item.getMaxStackSize() - item.getAmount();
                    }
                }

                // This is the max amount of items that could be potentially crafted
                int maxAmount = min * result.getAmount();
                amount = spaceForItem == 0 ? 0 : Math.min(maxAmount, spaceForItem);
                break;
            default:
                break;
        }

        // Calling event (if they craft)
        if (amount > 0) {
            final CraftSuccessEvent successEvent = new CraftSuccessEvent(player, result, amount, CraftSuccessEvent.CraftType.CRAFTING_TABLE);
            Bukkit.getPluginManager().callEvent(successEvent);

            // Making it cancellable
            if (successEvent.isCancelled())
                event.setCancelled(true);

            // Updating item (if I need to)
            if (!result.equals(successEvent.getResult()))
                inv.setItem(2, successEvent.getResult());

        }
    }

    /**
     * This click event handles smithing tables
     * and anvils for the craft success event
     *
     * @param event inventory click event
     */
    @EventHandler (priority = EventPriority.HIGHEST)
    public void onClick(InventoryClickEvent event) {
        Player player = (Player) event.getWhoClicked();
        Inventory inv = event.getClickedInventory();

        // Makings sure inv isn't null and the
        // click isn't inside players inventory
        if (inv == null || inv.equals(player.getInventory()))
            return;

        // Clicked item
        ItemStack clickedItem = event.getCurrentItem();

        // Making sure clicked item exists
        if (clickedItem == null || clickedItem.getType() == Material.AIR)
            return;

        // Smithing table craft success checker
        if (inv.getType() == InventoryType.SMITHING) {
            if (event.getSlot() != 2)
                return;

            // Nether ingot item
            ItemStack netherIngot = inv.getItem(1);

            // Making sure the item... you know... exists
            if (netherIngot == null || netherIngot.getType() == Material.AIR)
                return;

            // The amount of ingots it should cost
            // FYI: this is a custom handler, because
            // crafting netherite armor is being made
            // more difficult on the raid server
            // (that's why this exists)
            int amount = 0;
            switch (clickedItem.getType()) {
                case NETHERITE_HELMET:
                    amount = 5;
                    break;

                case NETHERITE_CHESTPLATE:
                    amount = 8;
                    break;

                case NETHERITE_LEGGINGS:
                    amount = 7;
                    break;

                case NETHERITE_BOOTS:
                    amount = 4;
                    break;

                case NETHERITE_PICKAXE:
                case NETHERITE_AXE:
                    amount = 3;
                    break;

                case NETHERITE_HOE:
                case NETHERITE_SWORD:
                    amount = 2;
                    break;

                case NETHERITE_SHOVEL:
                    amount = 1;
                    break;
            }

            // Stopping if the amount is 0
            // AKA nothing is being crafted
            if (amount <= 0)
                return;

            // If the user doesn't have enough ingots
            if (netherIngot.getAmount() < amount) {
                event.setCancelled(true);
                player.sendMessage(ChatColor.RED + "You need " + amount + " Netherite Ingots to make this!");
            } else {
                // Calling the event
                final CraftSuccessEvent successEvent = new CraftSuccessEvent(player, clickedItem, clickedItem.getAmount(), CraftSuccessEvent.CraftType.SMITHING_TABLE);
                Bukkit.getPluginManager().callEvent(successEvent);

                // Not continuing if it gets cancelled
                if (successEvent.isCancelled()) {
                    event.setCancelled(true);
                    player.updateInventory();
                    return;
                }

                // Updating item (if I need to)
                if (!clickedItem.equals(successEvent.getResult()))
                    inv.setItem(2, successEvent.getResult());

                // I have a delay here because other wise
                // when removing the item stack from the
                // second slot, the item doesn't actually
                // get crafted (just put a delay to band-aid it)
                final int finalAmount = amount;
                ITask iTask = new ITask(0) {
                    @Override
                    public void endTask() {
                        ItemStack newItem = netherIngot.getAmount() == finalAmount ? new ItemStack(Material.AIR) : new ItemStack(netherIngot.getType(), netherIngot.getAmount() - (finalAmount - 1));
                        inv.setItem(1, newItem);
                    }
                };

                // Starting task
                iTask.startTask(0, false);
            }

            return;
        }

        // Anvil checker
        if (inv.getType() == InventoryType.ANVIL) {
            if (event.getSlot() != 2)
                return;

            // The first/last item in anvil
            ItemStack firstItem = inv.getItem(0);
            ItemStack result = inv.getItem(2);

            // Making sure items exist
            if (firstItem == null || result == null)
                return;

            // Right here I'm just making sure they don't
            // try to rename colored items in an anvil
            if (firstItem.hasItemMeta() && Objects.requireNonNull(firstItem.getItemMeta()).hasDisplayName() &&
                    result.hasItemMeta() && Objects.requireNonNull(result.getItemMeta()).hasDisplayName()) {
                // String name of first name
                String itemName = firstItem.getItemMeta().getDisplayName();

                // if the item is a colored item
                if (itemName.contains("§")) {
                    // If the first name is not equal to the second name
                    // (this implies they tried to change it)
                    if (!itemName.equalsIgnoreCase(result.getItemMeta().getDisplayName())) {
                        player.closeInventory();
                        player.updateInventory();
                        player.sendMessage(ChatColor.RED + "You can't rename oclored items!");
                        return;
                    }
                }
            }

            // Calling craft success event
            final CraftSuccessEvent successEvent = new CraftSuccessEvent(player, result, result.getAmount(), CraftSuccessEvent.CraftType.ANVIL);
            Bukkit.getPluginManager().callEvent(successEvent);

            // Making it cancellable (gotta close with anvils)
            if (successEvent.isCancelled()) {
                player.closeInventory();
                player.updateInventory();
            }

            // Updating item (if I need to)
            if (!result.equals(successEvent.getResult()))
                inv.setItem(2, successEvent.getResult());
        }
    }
}
