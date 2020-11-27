package com.codepunisher.mcaimcore.gui;

import com.codepunisher.mcaimcore.CoreMain;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.*;

/**
 * Abstract GUI class FOR NOW
 * there will not be a multi-page
 * system built in, I'll add it later
 */
public abstract class AbstractGUI implements Listener
{
    private final Player player;                                                // Player who is inside menu
    public final Inventory inventory;                                          // The literal inventory
    private final HashMap<Action, Clickable> clickables;                        // List of all clickables
    private final Collection<Action> nonClickables;                             // Clickables that get unregistered
    private final List<OnClose> closes;
    private boolean closeAction;

    /**
     * Constructor initializing values
     *
     * @param player player who is inside gui
     * @param title title of the gui
     * @param size size of gui
     */
    public AbstractGUI(Player player, String title, int size) {
        this.player = player;
        this.inventory = Bukkit.createInventory(new GUIHolder(), size, title);
        this.clickables = new HashMap<>();
        this.nonClickables = new HashSet<>();
        this.closes = new ArrayList<>();
        this.closeAction = true;
    }

    /**
     * Constructing and opening GUI
     */
    public void openGUI() {
        // Constructing gui
        constructGUI();

        // Registering close actions
        registerCloseActions();

        // Opening gui
        player.openInventory(inventory);
    }

    /** GUI Construction */
    public abstract void constructGUI();

    /** Registering close actions */
    public abstract void registerCloseActions();

    /**
     * Adding custom gui item
     *
     * @param slot slot to add item to
     * @param material material of item
     * @param name name of item stack
     * @param lore lore on item stack
     */
    protected void setButton(int slot, Material material, String name, String... lore) {
        ItemStack item = new ItemStack(material);
        ItemMeta meta = item.getItemMeta();

        if (meta != null) {
            if (name != null)
                meta.setDisplayName(ChatColor.translateAlternateColorCodes('&', name));

            if (lore != null) {
                List<String> newLore = new ArrayList<>();

                for (String string : lore)
                    newLore.add(ChatColor.translateAlternateColorCodes('&', string));

                meta.setLore(newLore);
            }
        }

        item.setItemMeta(meta);
        inventory.setItem(slot, item);
    }

    /**
     * Adds skull item to menu
     *
     * @param slot slot to add to
     * @param uuid uuid to get skull
     * @param name name to set item stack to
     * @param lore lore for the item stack
     */
    protected ItemStack setSkullButton(int slot, UUID uuid, String name, String... lore) {
        ItemStack item = new ItemStack(Material.PLAYER_HEAD);
        SkullMeta skullMeta = (SkullMeta) item.getItemMeta();

        if (skullMeta != null) {
            Player player = Bukkit.getPlayer(uuid);

            if (player != null) {
                skullMeta.setOwner(player.getName());
            } else {
                skullMeta.setOwner(Bukkit.getOfflinePlayer(uuid).getName());
            }

            if (name != null)
                skullMeta.setDisplayName(ChatColor.translateAlternateColorCodes('&', name));

            if (lore != null) {
                List<String> newLore = new ArrayList<>();

                for (String string : lore)
                    newLore.add(ChatColor.translateAlternateColorCodes('&', string));

                skullMeta.setLore(newLore);
            }
        }

        item.setItemMeta(skullMeta);
        inventory.setItem(slot, item);
        return item;
    }

    /**
     * This fills all empty slots
     * with a background panel
     */
    protected void fillBackGround() {
        for (int i = 0; i < inventory.getSize(); i++) {
            ItemStack item = inventory.getItem(i);

            if (item == null || item.getType() == Material.AIR)
                setBackPanel(i);
        }
    }

    /**
     * Easy method for setting back panel
     * @param slot slot to set panel
     */
    protected void setBackPanel(int slot) {
        inventory.setItem(slot, new ItemStack(Material.BLACK_STAINED_GLASS_PANE));
    }

    /**
     * Used to register a single clickable
     *
     * @param slot slot the clickable is on
     * @param clickable clickable object
     */
    protected void registerClickAction(int slot, Sound sound, Clickable clickable) {
        clickables.put(new Action(slot, sound), clickable);
    }

    /**
     * Unregistering click
     * @param slot slot to unregister
     */
    protected void unRegisterClickAction(int slot) {
        for (Action action : clickables.keySet()) {
            if (action.getSlot() == slot) {
                this.nonClickables.add(action);
                return;
            }
        }
    }

    /**
     * Unregistering all click actions
     */
    protected void unRegisterAllClickActions() { this.nonClickables.addAll(clickables.keySet()); }

    /**
     * Registering a specific close action
     *
     * @param onClose onclose object
     */
    protected void registerCloseAction(OnClose onClose) { this.closes.add(onClose); }

    /**
     * Cancels the inventory from closing
     */
    protected void cancelCloseAction() { this.closeAction = false; }

    /**
     * Initializing listeners for guis
     * this should only ever run on plugin enable
     *
     * @param plugin instance of plugin
     */
    public static void initializeListeners(JavaPlugin plugin) {
        Bukkit.getPluginManager().registerEvents(new Listener() {
            @EventHandler
            public void onClick(InventoryClickEvent event) {
                Player player = (Player) event.getWhoClicked();
                ItemStack item = event.getCurrentItem();
                Inventory inventory = event.getClickedInventory();

                if (item != null && inventory != null) {
                    AbstractGUI gui = getGUIFromInventory(inventory);

                    if (gui != null) {
                        // Just gonna straight cancel the click entirely
                        event.setCancelled(true);

                        // Making sure player is clicking in top menu (before making checks)
                        if (!Objects.equals(player.getInventory(), inventory)) {
                            if (!gui.clickables.isEmpty()) {
                                HashMap<Action, Clickable> entries = new HashMap<>(gui.clickables);

                                // Current item slot being clicked
                                int currentSlot = event.getSlot();

                                // Actually making the clickables function
                                for (Map.Entry<Action, Clickable> entry : entries.entrySet()) {
                                    // Making sure the clickable wasn't unregistered
                                    if (!gui.nonClickables.contains(entry.getKey())) {
                                        int slot = entry.getKey().getSlot();
                                        Sound sound = entry.getKey().getSound();
                                        Clickable clickable = entry.getValue();

                                        // If player clicks on a registered slot
                                        if (slot == currentSlot) {
                                            clickable.onClick(slot, item, event.getClick());

                                            if (sound != null)
                                                player.playSound(player.getLocation(), sound, 1.0f, 1.0f);
                                        }
                                    }
                                }
                            }
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
                    AbstractGUI gui = getGUIFromInventory(inventory);

                    if (gui != null) {
                        if (!gui.closes.isEmpty()) {
                            // Executing close tasks
                            for (OnClose onClose : gui.closes) {
                                onClose.onClose();
                            }

                            if (!gui.closeAction) {
                                gui.closeAction = true;

                                new BukkitRunnable() {
                                    @Override
                                    public void run() {
                                        player.openInventory(inventory);
                                    }
                                }.runTaskLater(CoreMain.getInstance(), 1L);
                            }
                        }
                    }
                }
            }

            // Returns abstract gui from gui like I guess? Sure why not
            private AbstractGUI getGUIFromInventory(Inventory inventory) {
                if (inventory.getHolder() == null) return null;
                InventoryHolder holder = inventory.getHolder();
                if (!(holder instanceof GUIHolder)) return null;

                return ((AbstractGUI.GUIHolder) holder).getGUI();
            }
        }, plugin);
    }

    /** Code taken from Songoda Core */
    public class GUIHolder implements InventoryHolder {
        @Override
        public Inventory getInventory() {
            return inventory;
        }

        public AbstractGUI getGUI() {
            return AbstractGUI.this;
        }
    }
}
