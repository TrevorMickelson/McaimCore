package com.codepunisher.mcaimcore.brawlstuff.crates;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.*;

/**
 * The crate type that also stores
 * the items themselves within the crate
 */
public enum CrateType {
    TEAM_CHALLENGES(
        "§b§lChallenge Key",
        new CrateItem[] {
            CrateItem.OBSTROYER,
            CrateItem.PICKAXE_OF_CONTAINMENT,
            CrateItem.TREE_FELLER,
            CrateItem.AUTO_SMELTER,
            CrateItem.GAPPLES,
            CrateItem.DIAMONDS,
            CrateItem.NETHERITE_INGOTS,
            CrateItem.BEACON,
            CrateItem.DRAGON_EGG,
            CrateItem.OBSIDIAN,
            CrateItem.SPAWN_EGGS
        },
        new Location(Bukkit.getWorld("world"), 0.5, 65, 0.5)
    ),

    KOTH(
        "§4§lKoTH Key",
        new CrateItem[] {
            CrateItem.KOTH_HELMET,
            CrateItem.KOTH_CHESTPLATE,
            CrateItem.KOTH_LEGGINGS,
            CrateItem.KOTH_BOOTS,
            CrateItem.KOTH_SWORD,
            CrateItem.KOTH_PICKAXE,
            CrateItem.KOTH_AXE,
            CrateItem.KOTH_SHOVEL,
            CrateItem.KOTH_BOWS,
            CrateItem.MENDING_BOOKS
        },
        new Location(Bukkit.getWorld("world"), 0.5, 65, 0.5)
    ),

    WEEKLY(
        "§6§lWeekly Key",
        new CrateItem[] {
            CrateItem.WEEKLY_ARMOR,
            CrateItem.WEEKLY_TOOLS,
            CrateItem.INSTANT_OBSTROYER,
            CrateItem.STOMPER,
            CrateItem.DEADLY_SWORDS,
        },
        new Location(Bukkit.getWorld("world"), 0.5, 65, 0.5)
    );

    private final String name;
    private final ItemStack key;
    private final CrateItem[] crateItems;
    private final Location location;

    /**
     * Setting up enum values
     *
     * @param name name of the crate key
     * @param crateItems the crate items associated to the crate
     * @param location the location of the crate in spawn
     */
    CrateType(String name, CrateItem[] crateItems, Location location) {
        ItemStack item = new ItemStack(Material.TRIPWIRE_HOOK);
        ItemMeta meta = item.getItemMeta();

        if (meta != null) {
            meta.setDisplayName(name);
            meta.setLore(Collections.singletonList("Open crate with key in /Spawn"));
            meta.addEnchant(Enchantment.DURABILITY, 1, true);
        }

        item.setItemMeta(meta);
        this.name = name;
        this.key = item;
        this.crateItems = crateItems;
        this.location = location;
    }

    public final String getName() { return this.name; }
    public final ItemStack getKey(int amount) {
        ItemStack key = this.key.clone();
        key.setAmount(amount);
        return key;
    }
    public final CrateItem[] getCrateItems() { return this.crateItems; }
    public final Location getLocation() { return this.location; }

    /**
     * Checks if an item matches
     * the crate key item stack
     *
     * checking by display name
     * because it makes life easier
     *
     * @param item item to check for
     * @return true/false if items match
     */
    public boolean doesMatch(ItemStack item) {
        ItemMeta meta = item.getItemMeta();
        ItemMeta enumMeta = getKey(1).getItemMeta();
        boolean hasMeta = meta != null && enumMeta != null;

        // If I can check display name
        if (hasMeta)
            return meta.getDisplayName().toLowerCase().contains(enumMeta.getDisplayName().toLowerCase());

        return false;
    }

    /**
     * Gives random item based on
     * all crate items and takes
     * their percentages into consideration
     */
    public void giveRandomReward(Player player) {
        int randomPercent = new Random().nextInt(100);
        int total = 0;

        for (CrateItem crateItem : getCrateItems()) {
            int chance = crateItem.getPercentage();
            total += chance;

            if (total >= randomPercent) {
                giveCrateReward(player, crateItem);
                return;
            }
        }

        // If the code reaches this point that means
        // the reward was never given (recursing)
        giveRandomReward(player);
    }

    /**
     * This is what actually gives
     * the player the crate item reward
     *
     * Drops on floor if inventory is empty
     * also checks for 1 or multi item
     *
     * @param player player to give reward
     * @param crateItem the crate reward
     */
    private void giveCrateReward(Player player, CrateItem crateItem) {
        List<ItemStack> items = new ArrayList<>();

        // Adding items to list
        if (crateItem.isOneItem()) {
            items.add(crateItem.getItem());
        } else {
            items.addAll(Arrays.asList(crateItem.getItems()));
        }

        // Giving player item (or dropping on floor if I have to)
        for (ItemStack reward : items) {
            Map<Integer, ItemStack> addMap = player.getInventory().addItem(reward);

            // If the item was not added
            // to the players inventory
            if (!addMap.isEmpty()) {
                // Dropping item on floor at player location
                player.getWorld().dropItemNaturally(player.getLocation(), reward);
            }
        }
    }
}
