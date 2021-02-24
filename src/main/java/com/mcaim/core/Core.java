package com.mcaim.core;

import com.mcaim.core.chat.ChatListener;
import com.mcaim.core.events.listeners.ArmorListener;
import com.mcaim.core.events.listeners.CraftSuccessListener;
import com.mcaim.core.events.listeners.MoveEventManager;
import com.mcaim.core.gui.GuiListener;
import com.mcaim.core.models.cooldown.CooldownHandler;
import com.mcaim.core.mysql.MySQL;
import org.bukkit.plugin.java.JavaPlugin;

import java.sql.SQLException;

public class Core extends JavaPlugin {
    private static Core core;
    public static Core getInstance() { return core; }

    @Override
    public void onEnable() {
        core = this;

        getServer().getPluginManager().registerEvents(new ArmorListener(), this);
        getServer().getPluginManager().registerEvents(new CraftSuccessListener(), this);
        getServer().getPluginManager().registerEvents(new GuiListener(), this);
        getServer().getPluginManager().registerEvents(new ChatListener(), this);
        MoveEventManager.register(this);

        CooldownHandler.getInstance().initClearTask();
        saveDefaultConfig();
        dataBaseConnect();
    }

    @Override
    public void onDisable() {
        try {
            MySQL.disconnect();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void dataBaseConnect() {
        try {
            MySQL.connect();
        } catch (SQLException e) {
            System.out.println("Database not connected! Your information is wrong!");
        }

        if (MySQL.isConnected())
            System.out.println("Database connect successful!");
    }
}
