package com.mcaim.core.models;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.PotionMeta;
import org.bukkit.potion.PotionData;
import org.bukkit.potion.PotionType;

public enum Potions {
    NIGHT_VISION(Material.POTION, PotionType.NIGHT_VISION, false, false),
    NIGHT_VISION_LONG(Material.POTION, PotionType.NIGHT_VISION, true, false),
    NIGHT_VISION_SPLASH(Material.SPLASH_POTION, PotionType.NIGHT_VISION, false, false),
    NIGHT_VISION_SPLASH_LONG(Material.SPLASH_POTION, PotionType.NIGHT_VISION, true, false),
    NIGHT_VISION_LINGER(Material.LINGERING_POTION, PotionType.NIGHT_VISION, false, false),
    NIGHT_VISION_LINGER_LONG(Material.LINGERING_POTION, PotionType.NIGHT_VISION, true, false),

    INVISIBILITY(Material.POTION, PotionType.INVISIBILITY, false, false),
    INVISIBILITY_LONG(Material.POTION, PotionType.INVISIBILITY, true, false),
    INVISIBILITY_SPLASH(Material.SPLASH_POTION, PotionType.INVISIBILITY, false, false),
    INVISIBILITY_SPLASH_LONG(Material.SPLASH_POTION, PotionType.INVISIBILITY, true, false),
    INVISIBILITY_LINGER(Material.LINGERING_POTION, PotionType.INVISIBILITY, false, false),
    INVISIBILITY_LINGER_LONG(Material.LINGERING_POTION, PotionType.INVISIBILITY, true, false),

    JUMP(Material.POTION, PotionType.JUMP, false, false),
    JUMP_LONG(Material.POTION, PotionType.JUMP, true, false),
    JUMP_UPGRADE(Material.POTION, PotionType.JUMP, false, true),
    JUMP_SPLASH(Material.SPLASH_POTION, PotionType.JUMP, false, false),
    JUMP_SPLASH_LONG(Material.SPLASH_POTION, PotionType.JUMP, true, false),
    JUMP_SPLASH_UPGRADE(Material.SPLASH_POTION, PotionType.JUMP, false, true),
    JUMP_LINGER(Material.LINGERING_POTION, PotionType.JUMP, false, false),
    JUMP_LINGER_LONG(Material.LINGERING_POTION, PotionType.JUMP, true, false),
    JUMP_LINGER_UPGRADE(Material.LINGERING_POTION, PotionType.JUMP, false, true),

    FIRE_RESISTANCE(Material.POTION, PotionType.FIRE_RESISTANCE, false, false),
    FIRE_RESISTANCE_LONG(Material.POTION, PotionType.FIRE_RESISTANCE, true, false),
    FIRE_RESISTANCE_SPLASH(Material.SPLASH_POTION, PotionType.FIRE_RESISTANCE, false, false),
    FIRE_RESISTANCE_SPLASH_LONG(Material.SPLASH_POTION, PotionType.FIRE_RESISTANCE, true, false),
    FIRE_RESISTANCE_LINGER(Material.LINGERING_POTION, PotionType.FIRE_RESISTANCE, false, false),
    FIRE_RESISTANCE_LINGER_LONG(Material.LINGERING_POTION, PotionType.FIRE_RESISTANCE, true, false),

    SPEED(Material.POTION, PotionType.SPEED, false, false),
    SPEED_LONG(Material.POTION, PotionType.SPEED, true, false),
    SPEED_UPGRADE(Material.POTION, PotionType.SPEED, false, true),
    SPEED_SPLASH(Material.SPLASH_POTION, PotionType.SPEED, false, false),
    SPEED_SPLASH_LONG(Material.SPLASH_POTION, PotionType.SPEED, true, false),
    SPEED_SPLASH_UPGRADE(Material.SPLASH_POTION, PotionType.SPEED, false, true),
    SPEED_LINGER(Material.LINGERING_POTION, PotionType.SPEED, false, false),
    SPEED_LINGER_LONG(Material.LINGERING_POTION, PotionType.SPEED, true, false),
    SPEED_LINGER_UPGRADE(Material.LINGERING_POTION, PotionType.SPEED, false, true),

    SLOWNESS(Material.POTION, PotionType.SLOWNESS, false, false),
    SLOWNESS_LONG(Material.POTION, PotionType.SLOWNESS, true, false),
    SLOWNESS_UPGRADE(Material.POTION, PotionType.SLOWNESS, false, true),
    SLOWNESS_SPLASH(Material.SPLASH_POTION, PotionType.SLOWNESS, false, false),
    SLOWNESS_SPLASH_LONG(Material.SPLASH_POTION, PotionType.SLOWNESS, true, false),
    SLOWNESS_SPLASH_UPGRADE(Material.SPLASH_POTION, PotionType.SLOWNESS, false, true),
    SLOWNESS_LINGER(Material.LINGERING_POTION, PotionType.SLOWNESS, false, false),
    SLOWNESS_LINGER_LONG(Material.LINGERING_POTION, PotionType.SLOWNESS, true, false),
    SLOWNESS_LINGER_UPGRADE(Material.LINGERING_POTION, PotionType.SLOWNESS, false, true),

    WATER_BREATHING(Material.POTION, PotionType.WATER_BREATHING, false, false),
    WATER_BREATHING_LONG(Material.POTION, PotionType.WATER_BREATHING, true, false),
    WATER_BREATHING_SPLASH(Material.SPLASH_POTION, PotionType.WATER_BREATHING, false, false),
    WATER_BREATHING_SPLASH_LONG(Material.SPLASH_POTION, PotionType.WATER_BREATHING, true, false),
    WATER_BREATHING_LINGER(Material.LINGERING_POTION, PotionType.WATER_BREATHING, false, false),
    WATER_BREATHING_LINGER_LONG(Material.LINGERING_POTION, PotionType.WATER_BREATHING, true, false),

    INSTANT_HEAL(Material.POTION, PotionType.INSTANT_HEAL, false, false),
    INSTANT_HEAL_UPGRADE(Material.POTION, PotionType.INSTANT_HEAL, false, true),
    INSTANT_HEAL_SPLASH(Material.SPLASH_POTION, PotionType.INSTANT_HEAL, false, false),
    INSTANT_HEAL_SPLASH_UPGRADE(Material.SPLASH_POTION, PotionType.INSTANT_HEAL, false, true),
    INSTANT_HEAL_LINGER(Material.LINGERING_POTION, PotionType.INSTANT_HEAL, false, false),
    INSTANT_HEAL_LINGER_UPGRADE(Material.LINGERING_POTION, PotionType.INSTANT_HEAL, false, true),

    INSTANT_DAMAGE(Material.POTION, PotionType.INSTANT_DAMAGE, false, false),
    INSTANT_DAMAGE_UPGRADE(Material.POTION, PotionType.INSTANT_DAMAGE, false, true),
    INSTANT_DAMAGE_SPLASH(Material.SPLASH_POTION, PotionType.INSTANT_DAMAGE, false, false),
    INSTANT_DAMAGE_SPLASH_UPGRADE(Material.SPLASH_POTION, PotionType.INSTANT_DAMAGE, false, true),
    INSTANT_DAMAGE_LINGER(Material.LINGERING_POTION, PotionType.INSTANT_DAMAGE, false, false),
    INSTANT_DAMAGE_LINGER_UPGRADE(Material.LINGERING_POTION, PotionType.INSTANT_DAMAGE, false, true),

    POISON(Material.POTION, PotionType.POISON, false, false),
    POISON_LONG(Material.POTION, PotionType.POISON, true, false),
    POISON_UPGRADE(Material.POTION, PotionType.POISON, false, true),
    POISON_SPLASH(Material.SPLASH_POTION, PotionType.POISON, false, false),
    POISON_SPLASH_LONG(Material.SPLASH_POTION, PotionType.POISON, true, false),
    POISON_SPLASH_UPGRADE(Material.SPLASH_POTION, PotionType.POISON, false, true),
    POISON_LINGER(Material.LINGERING_POTION, PotionType.POISON, false, false),
    POISON_LINGER_LONG(Material.LINGERING_POTION, PotionType.POISON, true, false),
    POISON_LINGER_UPGRADE(Material.LINGERING_POTION, PotionType.POISON, false, true),

    REGEN(Material.POTION, PotionType.REGEN, false, false),
    REGEN_LONG(Material.POTION, PotionType.REGEN, true, false),
    REGEN_UPGRADE(Material.POTION, PotionType.REGEN, false, true),
    REGEN_SPLASH(Material.SPLASH_POTION, PotionType.REGEN, false, false),
    REGEN_SPLASH_LONG(Material.SPLASH_POTION, PotionType.REGEN, true, false),
    REGEN_SPLASH_UPGRADE(Material.SPLASH_POTION, PotionType.REGEN, false, true),
    REGEN_LINGER(Material.LINGERING_POTION, PotionType.REGEN, false, false),
    REGEN_LINGER_LONG(Material.LINGERING_POTION, PotionType.REGEN, true, false),
    REGEN_LINGER_UPGRADE(Material.LINGERING_POTION, PotionType.REGEN, false, true),

    STRENGTH(Material.POTION, PotionType.STRENGTH, false, false),
    STRENGTH_LONG(Material.POTION, PotionType.STRENGTH, true, false),
    STRENGTH_UPGRADE(Material.POTION, PotionType.STRENGTH, false, true),
    STRENGTH_SPLASH(Material.SPLASH_POTION, PotionType.STRENGTH, false, false),
    STRENGTH_SPLASH_LONG(Material.SPLASH_POTION, PotionType.STRENGTH, true, false),
    STRENGTH_SPLASH_UPGRADE(Material.SPLASH_POTION, PotionType.STRENGTH, false, true),
    STRENGTH_LINGER(Material.LINGERING_POTION, PotionType.STRENGTH, false, false),
    STRENGTH_LINGER_LONG(Material.LINGERING_POTION, PotionType.STRENGTH, true, false),
    STRENGTH_LINGER_UPGRADE(Material.LINGERING_POTION, PotionType.STRENGTH, false, true),

    WEAKNESS(Material.POTION, PotionType.WEAKNESS, false, false),
    WEAKNESS_LONG(Material.POTION, PotionType.WEAKNESS, true, false),
    WEAKNESS_SPLASH(Material.SPLASH_POTION, PotionType.WEAKNESS, false, false),
    WEAKNESS_SPLASH_LONG(Material.SPLASH_POTION, PotionType.WEAKNESS, true, false),
    WEAKNESS_LINGER(Material.LINGERING_POTION, PotionType.WEAKNESS, false, false),
    WEAKNESS_LINGER_LONG(Material.LINGERING_POTION, PotionType.WEAKNESS, true, false),

    LUCK(Material.POTION, PotionType.LUCK, false, false),
    LUCK_SPLASH(Material.SPLASH_POTION, PotionType.LUCK, false, false),
    LUCK_LINGER(Material.LINGERING_POTION, PotionType.LUCK, false, false),

    SLOW_FALLING(Material.POTION, PotionType.SLOW_FALLING, false, false),
    SLOW_FALLING_LONG(Material.POTION, PotionType.SLOW_FALLING, true, false),
    SLOW_FALLING_SPLASH(Material.SPLASH_POTION, PotionType.SLOW_FALLING, false, false),
    SLOW_FALLING_SPLASH_LONG(Material.SPLASH_POTION, PotionType.SLOW_FALLING, true, false),
    SLOW_FALLING_LINGER(Material.LINGERING_POTION, PotionType.SLOW_FALLING, false, false),
    SLOW_FALLING_LINGER_LONG(Material.LINGERING_POTION, PotionType.SLOW_FALLING, true, false);

    private final ItemStack item;

    Potions(Material material, PotionType potionType, boolean extended, boolean upgraded) {
        ItemStack potion = new ItemStack(material);
        PotionMeta potionMeta = (PotionMeta) potion.getItemMeta();

        if (potionMeta != null)
            potionMeta.setBasePotionData(new PotionData(potionType, extended, upgraded));

        potion.setItemMeta(potionMeta);
        item = potion;
    }

    public ItemStack getItem() { return item; }
}
