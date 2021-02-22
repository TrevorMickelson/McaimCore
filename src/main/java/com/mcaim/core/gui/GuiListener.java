package com.mcaim.core.gui;

import com.mcaim.core.scheduler.Sync;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;

import java.util.Objects;

public class GuiListener implements Listener {
    @EventHandler
    public void onClick(InventoryClickEvent event) {
        Player player = (Player) event.getWhoClicked();
        ItemStack item = event.getCurrentItem();
        Inventory inventory = event.getClickedInventory();

        if (item != null && inventory != null) {
            // Getting inventory the player is looking at
            Gui gui = getGUIFromInventory(player.getOpenInventory().getTopInventory());

            if (gui == null)
                return;

            // Just gonna straight cancel the click entirely
            event.setCancelled(true);

            // Making sure player is clicking in top menu (before making checks)
            if (Objects.equals(player.getInventory(), inventory))
                return;

            if (gui.getClickActions().isEmpty())
                return;

            // Handling the click action
            GuiButton clickable = gui.getClickActions().get(event.getSlot());
            if (clickable != null) {
                ClickType clickType = clickable.getClickType();

                if (clickType == null || event.getClick() == clickType) {
                    clickable.getClickAction().onClick();

                    Sound sound = clickable.getSound();

                    if (sound != null)
                        player.playSound(player.getLocation(), sound, 1.0f, 1.0f);
                }
            }
        }
    }

    // Actions for inventory close
    @EventHandler
    public void onClose(InventoryCloseEvent event) {
        Player player = (Player) event.getPlayer();
        Inventory inventory = event.getInventory();

        // If player closes chest (narrows down checks)
        if (inventory.getType() == InventoryType.CHEST) {
            Gui gui = getGUIFromInventory(inventory);

            if (gui != null) {
                if (!gui.getCloseActions().isEmpty()) {
                    // Executing close tasks
                    for (CloseAction closeAction : gui.getCloseActions())
                        closeAction.onClose();

                    if (!gui.isCloseAction()) {
                        gui.setCloseAction(true);
                        Sync.get().delay(1L).run(() -> { player.openInventory(inventory); });
                    }
                }
            }
        }
    }

    private Gui getGUIFromInventory(Inventory inventory) {
        if (inventory.getHolder() == null) return null;
        InventoryHolder holder = inventory.getHolder();
        if (!(holder instanceof Gui.GUIHolder)) return null;

        return ((Gui.GUIHolder) holder).getGUI();
    }
}
