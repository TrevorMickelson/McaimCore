package models;

import org.bukkit.inventory.ItemStack;

public class Armor
{
    private final ItemStack itemStack;
    private final ArmorType armorType;
    private final ArmorTier armorTier;

    public Armor(ItemStack itemStack, ArmorType armorType, ArmorTier armorTier) {
        this.itemStack = itemStack;
        this.armorType = armorType;
        this.armorTier = armorTier;
    }

    public ItemStack getItemStack() { return this.itemStack; }
    public ArmorType getType() { return this.armorType; }
    public ArmorTier getTier() { return this.armorTier; }
}
