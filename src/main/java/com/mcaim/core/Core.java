package com.mcaim.core;

import com.mcaim.core.chat.ChatListener;
import com.mcaim.core.events.listeners.ArmorListener;
import com.mcaim.core.events.listeners.CraftSuccessListener;
import com.mcaim.core.events.listeners.MoveEventManager;
import com.mcaim.core.gui.GuiListener;
import com.mcaim.core.models.cooldown.CooldownHandler;
import org.bukkit.plugin.java.JavaPlugin;

public class Core extends JavaPlugin {
    private static Core core;

    @Override
    public void onEnable() {
        core = this;

        getServer().getPluginManager().registerEvents(new ArmorListener(), this);
        getServer().getPluginManager().registerEvents(new CraftSuccessListener(), this);
        getServer().getPluginManager().registerEvents(new GuiListener(), this);
        getServer().getPluginManager().registerEvents(new ChatListener(), this);
        MoveEventManager.register(this);

        CooldownHandler.getInstance().initClearTask();
        //new Balance();

        // SQL DATA TESTING
        //SQLite.initSQL();
    }

    public void onDisable() {
    }

    public static Core getInstance() { return core; }
}
