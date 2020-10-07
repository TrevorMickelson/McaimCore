package listeners;

import events.ArmorEquipEvent;
import events.ArmorRemoveEvent;
import main.CoreMain;
import models.Armor;
import models.ArmorTier;
import models.ArmorType;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockDispenseArmorEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerItemBreakEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.*;

/**
 * This class listens for the armorequipevent
 * and the armorremoveevent
 */
public class ArmorListener implements Listener
{
    // Instance of main class
    private final CoreMain plugin = CoreMain.getInstance();

    // Armor slots
    private final int[] armorSlots = { 36, 37, 38, 39 };

    // For respawning on armor remove cancel
    private HashMap<UUID, List<ItemStack>> respawnUser = new HashMap<>();

    // ----- ARMOR EQUIP/REMOVE ----- //
    @EventHandler (priority = EventPriority.HIGHEST, ignoreCancelled = true)
    public void armorClick(InventoryClickEvent event) {
        // Stopping if event is cancelled
        if (event.isCancelled())
            return;

        Player player = (Player) event.getWhoClicked();
        Inventory inventory = event.getClickedInventory();
        ItemStack item = event.getCurrentItem();
        ItemStack cursorItem = event.getCursor();

        if (item != null && inventory != null && inventory.equals(player.getInventory()) && player.getOpenInventory().getType().toString().equalsIgnoreCase("CRAFTING"))
        {
            // ----- ARMOR REMOVE ------ //
            if (isArmor(item)) {
                for (int slot : armorSlots) {
                    if (event.getSlot() == slot) {
                        Armor armor = new Armor(item, getArmorType(item), getArmorTier(item));
                        ArmorRemoveEvent e = new ArmorRemoveEvent(player, armor);
                        Bukkit.getPluginManager().callEvent(e);

                        if (e.isCancelled())
                            event.setCancelled(true);
                        return;
                    }
                }
            }

            // ----- ARMOR EQUIP ------ //
            if (event.getClick() == ClickType.SHIFT_LEFT || event.getClick() == ClickType.SHIFT_RIGHT) {
                if (isArmor(item))
                {
                    // If can apply armor piece
                    boolean canApply = false;

                    if (item.getType().toString().toLowerCase().contains("helmet")) {
                        if (player.getInventory().getHelmet() == null)
                            canApply = true;
                    } else if (item.getType().toString().toLowerCase().contains("chestplate")) {
                        if (player.getInventory().getChestplate() == null)
                            canApply = true;
                    } else if (item.getType().toString().toLowerCase().contains("leggings")) {
                        if (player.getInventory().getLeggings() == null)
                            canApply = true;
                    } else {
                        if (player.getInventory().getBoots() == null)
                            canApply = true;
                    }

                    if (canApply) {
                        Armor armor = new Armor(item, getArmorType(item), getArmorTier(item));
                        ArmorEquipEvent e = new ArmorEquipEvent(player, armor);
                        Bukkit.getPluginManager().callEvent(e);

                        if (e.isCancelled())
                            event.setCancelled(true);
                    }

                    return;
                }
            }

            // ----- ARMOR EQUIP ------ //
            for (int slot : armorSlots) {
                if (event.getSlot() == slot) {
                    // If player is applying via normal
                    if (event.getClick() == ClickType.LEFT || event.getClick() == ClickType.RIGHT) {
                        if (isArmor(cursorItem)) {
                            assert cursorItem != null;
                            Armor armor = new Armor(cursorItem, getArmorType(cursorItem), getArmorTier(cursorItem));
                            ArmorEquipEvent e = new ArmorEquipEvent(player, armor);
                            Bukkit.getPluginManager().callEvent(e);

                            if (e.isCancelled())
                                event.setCancelled(true);
                        }
                        return;
                    }

                    // If player is applying via key switching
                    if (event.getClick() == ClickType.NUMBER_KEY) {
                        if (item.getType().equals(Material.AIR)) {
                            new BukkitRunnable()
                            {
                                @Override
                                public void run()
                                {
                                    // If specific slot has armor on it
                                    if (player.getInventory().getItem(event.getSlot()) != null) {
                                        Armor armor = new Armor(player.getInventory().getItem(event.getSlot()), getArmorType(Objects.requireNonNull(player.getInventory().getItem(event.getSlot()))), getArmorTier(Objects.requireNonNull(player.getInventory().getItem(event.getSlot()))));
                                        ArmorEquipEvent e = new ArmorEquipEvent(player, armor);
                                        Bukkit.getPluginManager().callEvent(e);

                                        if (e.isCancelled())
                                            event.setCancelled(true);
                                    }
                                }

                            }.runTaskLater(plugin, 0L);
                        }
                    }
                }
            }
        }
    }

    // ----- ARMOR EQUIP ----- //
    @EventHandler (priority = EventPriority.HIGHEST)
    public void armorInteract(PlayerInteractEvent event) {
        Player player = event.getPlayer();

        if (isArmor(player.getInventory().getItemInMainHand()) || isArmor(player.getInventory().getItemInOffHand())) {
            ItemStack item = player.getInventory().getItemInMainHand().getType() != Material.AIR ? player.getInventory().getItemInMainHand() : player.getInventory().getItemInOffHand();
            if (event.getAction().equals(Action.RIGHT_CLICK_AIR) || event.getAction().equals(Action.RIGHT_CLICK_BLOCK)) {
                // If can apply armor piece
                boolean canApply = false;

                if (item.getType().toString().toLowerCase().contains("helmet")) {
                    if (player.getInventory().getHelmet() == null)
                        canApply = true;
                } else if (item.getType().toString().toLowerCase().contains("chestplate")) {
                    if (player.getInventory().getChestplate() == null)
                        canApply = true;
                } else if (item.getType().toString().toLowerCase().contains("leggings")) {
                    if (player.getInventory().getLeggings() == null)
                        canApply = true;
                } else {
                    if (player.getInventory().getBoots() == null)
                        canApply = true;
                }

                if (canApply) {
                    Armor armor = new Armor(item, getArmorType(item), getArmorTier(item));
                    ArmorEquipEvent e = new ArmorEquipEvent(player, armor);
                    Bukkit.getPluginManager().callEvent(e);

                    if (e.isCancelled())
                        event.setCancelled(true);
                }
            }
        }
    }

    // ----- ARMOR EQUIP ----- //
    @EventHandler
    public void dispenserArmor(BlockDispenseArmorEvent event) {
        if (event.getTargetEntity() instanceof Player) {
            Player player = (Player) event.getTargetEntity();
            ItemStack item = event.getItem();
            Armor armor = new Armor(item, getArmorType(item), getArmorTier(item));
            ArmorEquipEvent e = new ArmorEquipEvent(player, armor);
            Bukkit.getPluginManager().callEvent(e);

            if (e.isCancelled())
                event.setCancelled(true);
        }
    }

    // ----- ARMOR REMOVE ----- //
    @EventHandler
    public void armorBreak(PlayerItemBreakEvent event) {
        Player player = event.getPlayer();
        ItemStack item = event.getBrokenItem();

        if (isArmor(item))
        {
            ArmorType type = getArmorType(item);
            Armor armor = new Armor(item, type, getArmorTier(item));
            ArmorRemoveEvent e = new ArmorRemoveEvent(player, armor);
            Bukkit.getPluginManager().callEvent(e);

            if (e.isCancelled()) {
                ItemStack i = item.clone();
                i.setAmount(1);
                i.setDurability((short) (i.getDurability() - 1));

                if (type == ArmorType.HELMET) {
                    player.getInventory().setHelmet(i);
                } else if (type == ArmorType.CHESTPLATE){
                    player.getInventory().setChestplate(i);
                } else if (type == ArmorType.LEGGINGS){
                    player.getInventory().setLeggings(i);
                } else if (type == ArmorType.BOOTS  ) {
                    player.getInventory().setBoots(i);
                }
            }
        }
    }

    // ----- ARMOR REMOVE ----- //
    @EventHandler
    public void onDeath(PlayerDeathEvent event) {
        Player player = event.getEntity();
        if (event.getKeepInventory())
            return;

        List<ItemStack> items = new ArrayList<>();

        for (ItemStack item : player.getInventory().getArmorContents()) {
            if (item != null && item.getType() != Material.AIR) {
                Armor armor = new Armor(item, getArmorType(item), getArmorTier(item));
                ArmorRemoveEvent e = new ArmorRemoveEvent(player, armor);
                Bukkit.getPluginManager().callEvent(e);

                if (e.isCancelled()) {
                    items.add(item);
                    event.getDrops().remove(item);
                }
            }
        }

        if (!items.isEmpty()) {
            this.respawnUser.put(player.getUniqueId(), items);
        }
    }

    // ----- ARMOR REMOVE ----- //
    @EventHandler
    public void onRespawn(PlayerRespawnEvent event) {
        Player player = event.getPlayer();

        if (this.respawnUser.containsKey(player.getUniqueId())) {
            List<ItemStack> items = this.respawnUser.get(player.getUniqueId());

            for (ItemStack item : items) {
                if (getArmorType(item) == ArmorType.PLAYER_HEAD || getArmorType(item) == ArmorType.HELMET) {
                    player.getInventory().setHelmet(item);
                } else if (getArmorType(item) == ArmorType.CHESTPLATE) {
                    player.getInventory().setChestplate(item);
                } else if (getArmorType(item) == ArmorType.LEGGINGS) {
                    player.getInventory().setLeggings(item);
                } else {
                    player.getInventory().setBoots(item);
                }
            }

            this.respawnUser.remove(player.getUniqueId());
        }
    }

    /**
     * Utility methods specific to this class
     */
    // Determines if an item stack is armor
    private boolean isArmor(ItemStack itemStack) {
        String[] armorNames = { "PLAYER_HEAD", "HELMET", "CHESTPLATE", "LEGGINGS", "BOOTS" };

        for (String string : armorNames) {
            if (itemStack.getType().toString().toUpperCase().contains(string))
                return true;
        }

        return false;
    }

    // Gets armor type
    private ArmorType getArmorType(ItemStack itemStack) {
        if (itemStack.getType().toString().toLowerCase().contains("player_head")) {
            return ArmorType.PLAYER_HEAD;
        } else if (itemStack.getType().toString().toLowerCase().contains("helmet")) {
            return ArmorType.HELMET;
        } else if (itemStack.getType().toString().toLowerCase().contains("chestplate")) {
            return ArmorType.CHESTPLATE;
        } else if (itemStack.getType().toString().toLowerCase().contains("leggings")) {
            return ArmorType.LEGGINGS;
        } else {
            return ArmorType.BOOTS;
        }
    }

    // Gets armor tier
    private ArmorTier getArmorTier(ItemStack itemStack) {
        if (itemStack.getType().toString().toLowerCase().contains("player_head"))
            return ArmorTier.SKULL;

        if (itemStack.getType().toString().toLowerCase().contains("leather"))
            return ArmorTier.LEATHER;

        if (itemStack.getType().toString().toLowerCase().contains("chain"))
            return ArmorTier.CHAIN;

        if (itemStack.getType().toString().toLowerCase().contains("iron"))
            return ArmorTier.IRON;

        if (itemStack.getType().toString().toLowerCase().contains("gold"))
            return ArmorTier.GOLD;

        if (itemStack.getType().toString().toLowerCase().contains("diamond"))
            return ArmorTier.DIAMOND;

        if (itemStack.getType().toString().toLowerCase().contains("netherite"))
            return ArmorTier.NETHERITE;

        return null;
    }
}
