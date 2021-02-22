package com.mcaim.core.gui;

import com.mcaim.core.models.ItemBuild;
import com.mcaim.core.util.C;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

//TODO: Make everything work with multiple pages
//TODO: Do this by making setItem(slot) take any number (like 100)
public class Gui {
    private final Player player;
    private final Inventory inventory;

    private final HashMap<Integer, GuiButton> clickActions = new HashMap<>();      // All the click actions for the gui
    private final List<CloseAction> closeActions = new ArrayList<>();              // List of inventory close actions
    private String permission = null;                                              // The permission to open the gui
    private boolean closeAction = true;                                            // If there's a close action or not

    public Gui(Player player, String title, int size) {
        this.player = player;
        this.inventory = Bukkit.createInventory(new GUIHolder(), size, title);
    }

    public HashMap<Integer, GuiButton> getClickActions() { return clickActions; }
    public List<CloseAction> getCloseActions() { return closeActions; }

    public String getPermission() { return permission; }
    public void setPermission(String permission) { this.permission = permission; }

    public boolean isCloseAction() { return closeAction; }
    public void setCloseAction(boolean closeAction) { this.closeAction = closeAction; }

    public void open() {
        if (permission == null || player.hasPermission(permission)) {
            player.openInventory(inventory);
            return;
        }

        C.noPerm(player);
        player.playSound(player.getLocation(), Sound.ENTITY_VILLAGER_NO, 1.0f, 1.0f);
    }
    public void forceOpen() { player.openInventory(inventory); }
    public void close() { player.closeInventory(); }

    //TODO: Have this method function with multi-page support AS IN setItem(100) would work with a new Gui
    public void setItem(int slot, ItemStack itemStack) { inventory.setItem(slot, itemStack); }

    /**
     * Sets the official background panel
     */
    public void setBackPanel(int slot) {
        setItem(slot, ItemBuild.of(Material.BLACK_STAINED_GLASS_PANE).name("&8☠").getItem());
    }

    /**
     * Adds a list of items to inventory
     * //TODO: Make work with multiple pages as well
     */
    public void fillItems(List<ItemStack> items) {
        int index = inventory.firstEmpty();

        for (ItemStack item : items) {
            setItem(index, item);
            index++;
        }
    }

    /**
     * This fills all empty slots
     * with a background panel
     * //TODO: Make work with all pages
     */
    public void fillBackGround() {
        for (int i = 0; i < inventory.getSize(); i++) {
            ItemStack item = inventory.getItem(i);

            if (item == null || item.getType() == Material.AIR)
                setBackPanel(i);
        }
    }

    public void fillBackGround(int slotMin, int slotMax) {
        for (int i = slotMin; i <= slotMax; i++)
            setBackPanel(i);
    }


    public void registerButton(int slot, ClickAction clickAction) {
        registerButton(slot, null, null, clickAction);
    }

    public void registerButton(int slot, ClickType clickType, ClickAction clickAction) {
        registerButton(slot, clickType, null, clickAction);
    }

    public void registerButton(int slot, Sound sound, ClickAction clickAction) {
        registerButton(slot, null, sound, clickAction);
    }

    public void registerButton(int slot, ClickType clickType, Sound sound, ClickAction clickAction) {
        clickActions.put(slot, new GuiButton(clickType, sound, clickAction));
    }

    public void unRegisterButton(int slot) {
        clickActions.remove(slot);
    }

    public void unRegisterAllButtons() { clickActions.clear(); }


    public void registerCloseAction(CloseAction closeAction) { closeActions.add(closeAction); }

    /**
     * Cancels the inventory from closing
     */
    public void cancelCloseAction() { this.closeAction = false; }

    public class GUIHolder implements InventoryHolder {
        @Override @Nonnull
        public Inventory getInventory() { return inventory; }

        public Gui getGUI() { return Gui.this; }
    }
}
