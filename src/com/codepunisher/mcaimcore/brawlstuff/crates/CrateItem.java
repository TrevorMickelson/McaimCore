package com.codepunisher.mcaimcore.brawlstuff.crates;

import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.enchantments.EnchantmentWrapper;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

/**
 * Enum class to hold all of the
 * crate items (the rewards)
 */
public enum CrateItem {
    // Team challenge rewards
    OBSTROYER(rewardItem(Material.DIAMOND_PICKAXE, "§5§lObstroyer", new String[] { "efficiency:10", "unbreaking:3" }, 1), 5),
    PICKAXE_OF_CONTAINMENT(rewardItem(Material.GOLDEN_PICKAXE, "§6§lPickaxe of Containment §8* §f3", null,1), 3),
    TREE_FELLER(rewardItem(Material.DIAMOND_AXE, "§a§lTree Feller", null, 1), 6),
    AUTO_SMELTER(rewardItem(Material.DIAMOND_PICKAXE, "§c§lAuto Smelter", null, 1), 7),
    GAPPLES(rewardItem(Material.ENCHANTED_GOLDEN_APPLE, null, null, 2), 10),
    DIAMONDS(rewardItem(Material.DIAMOND, null, null, 32), 12),
    NETHERITE_INGOTS(rewardItem(Material.NETHERITE_INGOT, null, null, 16), 10),
    BEACON(rewardItem(Material.BEACON, null, null, 1), 11),
    DRAGON_EGG(rewardItem(Material.ENCHANTED_GOLDEN_APPLE, null, null, 3), 11),
    OBSIDIAN(rewardItem(Material.OBSIDIAN, null, null, 576), 9),
    SPAWN_EGGS(
        new ItemStack[] {
            rewardItem(Material.MOOSHROOM_SPAWN_EGG, null, null, 1),
            rewardItem(Material.SKELETON_HORSE_SPAWN_EGG, null, null, 2),
            rewardItem(Material.VILLAGER_SPAWN_EGG, null, null, 2),
        }, 7
    ),

    // Koth rewards
    KOTH_HELMET(rewardItem(Material.DIAMOND_HELMET, "§8[§c§lKoTH Helmet§8]",
        new String[] { "protection:3", "unbreaking:3" }, 1), 5),

    KOTH_CHESTPLATE(rewardItem(Material.DIAMOND_CHESTPLATE, "§8[§c§lKoTH Chestplate§8]",
        new String[] { "protection:3", "unbreaking:3" }, 1), 5),

    KOTH_LEGGINGS(rewardItem(Material.DIAMOND_LEGGINGS, "§8[§c§lKoTH Leggings§8]",
        new String[] { "protection:3", "unbreaking:3" }, 1), 5),

    KOTH_BOOTS(rewardItem(Material.DIAMOND_BOOTS, "§8[§c§lKoTH Boots§8]",
        new String[] { "protection:3", "unbreaking:3" }, 1), 5),

    KOTH_SWORD(rewardItem(Material.DIAMOND_SWORD, "§8[§c§lKoTH Sword§8]",
        new String[] { "sharpness:4", "unbreaking:3", "fire_aspect:2", "looting:3" }, 1), 5),

    KOTH_PICKAXE(rewardItem(Material.DIAMOND_PICKAXE, "§8[§c§lKoTH Pickaxe§8]",
        new String[] { "efficiency:4", "unbreaking:3", "fortune:3" }, 1), 5),

    KOTH_AXE(rewardItem(Material.DIAMOND_AXE, "§8[§c§lKoTH Axe§8]",
        new String[] { "sharpness:4", "unbreaking:3", "efficiency:5", "fire_aspect:1", "looting:3" }, 1), 5),

    KOTH_SHOVEL(rewardItem(Material.DIAMOND_SHOVEL, "§8[§c§lKoTH Shovel§8]",
        new String[] { "efficiency:4", "unbreaking:3", "fortune:3" }, 1), 5),

    KOTH_BOWS(
        new ItemStack[] {
            rewardItem(Material.BOW, "§8[§c§lKoTH Bow§8]",
                new String[] { "power:4", "unbreaking:3", "punch:1", "flame:1", "infinity:1" }, 1),

            rewardItem(Material.CROSSBOW, "§8[§c§lKoTH Crossbow§8]",
                new String[] { "quick_charge:3", "unbreaking:3", "power:4", "flame:1", "infinity:1" }, 1),
        }, 5
    ),

    MENDING_BOOKS(rewardItem(Material.ENCHANTED_BOOK, null, new String[] { "mending:1" }, 2), 5),

    // Weekly rewards
    WEEKLY_ARMOR(
        new ItemStack[] {
            rewardItem(Material.NETHERITE_HELMET, "§e§k;;§r §6§lG§e§lO§6§lD §6§lH§e§lE§6§lL§e§lM§6§lE§e§lT§r §e§k;;§r",
                new String[] { "protection:5", "unbreaking:4" }, 1),

            rewardItem(Material.NETHERITE_CHESTPLATE, "§a§k;;§r §2§lG§a§lO§2§lD §2§lH§a§lE§2§lL§a§lM§2§lE§a§lT§r §a§k;;§r",
                new String[] { "protection:5", "unbreaking:4" }, 1),

            rewardItem(Material.NETHERITE_LEGGINGS, "§b§k;;§r §3§lG§b§lO§3§lD §3§lH§b§lE§3§lL§b§lM§3§lE§b§lT§r §b§k;;§r",
                new String[] { "protection:5", "unbreaking:4" }, 1),

            rewardItem(Material.NETHERITE_BOOTS, "§d§k;;§r §5§lG§d§lO§5§lD §5§lH§d§lE§5§lL§d§lM§5§lE§d§lT§r §d§k;;§r",
                new String[] { "protection:5", "unbreaking:4" }, 1),
        }, 5
    ),

    WEEKLY_TOOLS(
        new ItemStack[] {
            rewardItem(Material.NETHERITE_SWORD, "§e§k;;§r §6§lG§e§lO§6§lD §6§lS§e§lw§6§lo§e§lr§6§ld §e§k;;§r",
                new String[] { "sharpness:6", "unbreaking:4", "fire_aspect:3", "looting:4" }, 1),

            rewardItem(Material.NETHERITE_PICKAXE, "§a§k;;§r §2§lG§a§lO§2§lD §2§lP§a§li§2§lc§a§lk§2§la§a§lx§2§le §a§k;;§r",
                new String[] { "efficiency:6", "unbreaking:4", "fortune:4" }, 1),

            rewardItem(Material.NETHERITE_AXE, "§b§k;;§r §3§lG§b§lO§3§lD §3§lA§b§lx§3§le §b§k;;§r",
                new String[] { "sharpness:6", "unbreaking:4", "fire_aspect:3", "looting:4", "efficiency:6", "fortune:4" }, 1),

            rewardItem(Material.NETHERITE_SHOVEL, "§d§k;;§r §5§lG§d§lO§5§lD §5§lS§d§lh§5§lo§d§lv§5§le§d§ll §d§k;;§r",
                new String[] { "efficiency:6", "unbreaking:4", "fortune:4" }, 1),
        }, 5
    ),

    INSTANT_OBSTROYER(rewardItem(Material.NETHERITE_PICKAXE, "§d§k;§r §5§lInstant Obstroyer§r §d§k;§r", null, 1), 9),
    STOMPER(rewardItem(Material.NETHERITE_BOOTS, "§6§lS§e§lt§f§lo§6§lm§e§lp§f§le§6§lr", null, 1), 9),
    DEADLY_SWORDS(
        new ItemStack[] {
            rewardItem(Material.NETHERITE_SWORD, "§4§lDeath Enhancer",
                new String[] { "sharpness:5", "unbreaking:3", "fire_aspect:2", "looting:3" }, 1),

            rewardItem(Material.NETHERITE_SWORD, "§d§Decapitator",
                new String[] { "sharpness:5", "unbreaking:3", "fire_aspect:2", "looting:3" }, 1),
        }, 7
    );

    private ItemStack item;
    private ItemStack[] items;
    private final int percentage;

    /** Single item crate reward */
    CrateItem(ItemStack item, int percentage) {
        this.item = item;
        this.percentage = percentage;
    }

    /** Multi item crate reward */
    CrateItem(ItemStack[] items, int percentage) {
        this.items = items;
        this.percentage = percentage;
    }

    public ItemStack getItem() { return this.item; }
    public ItemStack[] getItems() { return this.items; }
    public int getPercentage() { return this.percentage; }
    public boolean isOneItem() { return items == null; }

    // Getting the GUI itemstack to display later
    // it's different in the event there's multiple items
    // I went with a book for multi items because
    // books open and I guess it makes sense
    public ItemStack getGuiItem() {
        //TODO: make display name for "name" look better for the book
        ItemStack displayItem = isOneItem() ? getItem() : rewardItem(Material.BOOK, "§a" + name(), null, 1);
        ItemMeta meta = displayItem.getItemMeta();

        if (meta != null) {
            meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
            List<String> lore = new ArrayList<>();
            lore.add("§f§lPercentage: §e" + getPercentage());

            // If there's more than 1 item (there will be another viewing menu)
            if (!isOneItem()) {
                lore.add("§r");
                lore.add("§7§oClick to view items");
            }

            meta.setLore(lore);
        }

        displayItem.setItemMeta(meta);
        return displayItem;
    }

    /**
     * This method checks if a particular item
     * matches the enum itemstack by checking
     * if the names match
     *
     * I'm doing it this way because the itemstack
     * itself can change into netherite or the
     * enchants can change, etc, but the name can't
     *
     * @param item item stack to check for
     * @return true/false if the items match
     */
    public boolean doesMatch(ItemStack item) {
        ItemMeta meta = item.getItemMeta();
        ItemMeta enumMeta = getItem().getItemMeta();
        boolean hasMeta = meta != null && enumMeta != null;

        // If I can check display name
        if (hasMeta)
            return meta.getDisplayName().toLowerCase().contains(enumMeta.getDisplayName().toLowerCase());

        return false;
    }

    /**
     * Creates item stack to be easily
     * used within the enumerators
     *
     * @param material material of item
     * @param name name of item
     * @param amount amount of item
     * @return ItemStack
     */
    private static ItemStack rewardItem(Material material, String name, String[] enchants, int amount) {
        ItemStack item = new ItemStack(material);
        ItemMeta meta = item.getItemMeta();

        if (meta != null) {
            if (name != null)
                meta.setDisplayName(name);

            if (enchants != null) {
                for (String s : enchants) {
                    String[] args = s.split(":");
                    Enchantment enchantment = EnchantmentWrapper.getByKey(NamespacedKey.minecraft(args[0]));
                    int level = Integer.parseInt(args[1].substring(args[1].lastIndexOf(":") + 1));

                    // Adding enchantment to item stack
                    if (enchantment != null)
                        meta.addEnchant(enchantment, level, true);
                }
            }
        }

        item.setAmount(amount);
        item.setItemMeta(meta);
        return item;
    }
}
