package com.codepunisher.mcaimcore.brawlstuff;

import com.codepunisher.mcaimcore.events.CraftSuccessEvent;
import org.bukkit.Material;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.UUID;

public class BuffedArmor implements Listener {
    @EventHandler
    public void onCraftSuccess(CraftSuccessEvent event) {
        if (event.getCraftType() == CraftSuccessEvent.CraftType.SMITHING_TABLE) {
            ItemStack result = event.getResult();
            Material material = result.getType();
            ItemStack newItem = null;

            switch (material) {
                case NETHERITE_HELMET:
                    newItem = buffedArmorPiece(material, 4, EquipmentSlot.HEAD, "helmet");
                    break;

                case NETHERITE_CHESTPLATE:
                    newItem = buffedArmorPiece(material, 10, EquipmentSlot.CHEST, "chestplate");
                    break;

                case NETHERITE_LEGGINGS:
                    newItem = buffedArmorPiece(material, 7, EquipmentSlot.LEGS, "leggings");
                    break;

                case NETHERITE_BOOTS:
                    newItem = buffedArmorPiece(material, 4, EquipmentSlot.FEET, "boots");
                    break;
            }

            // If the item gets set
            if (newItem != null)
                event.setResult(newItem);
        }
    }

    /** Buffs netherite armor based on material input */
    public ItemStack buffedArmorPiece(Material material, int buffAmount, EquipmentSlot slot, String name) {
        // Setting up item
        ItemStack armor = new ItemStack(material);
        ItemMeta meta = armor.getItemMeta();

        if (meta != null) {
            AttributeModifier buffedArmor = new AttributeModifier(UUID.randomUUID(), "generic." + name, buffAmount, AttributeModifier.Operation.ADD_NUMBER, slot);
            AttributeModifier buffedToughness = new AttributeModifier(UUID.randomUUID(), "generic." + name + "_toughness", 3, AttributeModifier.Operation.ADD_NUMBER, slot);
            meta.addAttributeModifier(Attribute.GENERIC_ARMOR, buffedArmor);
            meta.addAttributeModifier(Attribute.GENERIC_ARMOR_TOUGHNESS, buffedToughness);
        }

        // Setting meta
        armor.setItemMeta(meta);
        return armor;
    }
}
