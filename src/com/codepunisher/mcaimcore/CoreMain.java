package com.codepunisher.mcaimcore;

import com.codepunisher.mcaimcore.listeners.MoveEventManager;
import com.codepunisher.mcaimcore.listeners.ArmorListener;
import com.codepunisher.mcaimcore.listeners.EntityDamage;
import org.bukkit.plugin.java.JavaPlugin;

public class CoreMain extends JavaPlugin
{
    private static CoreMain coreMain;
    public static CoreMain getInstance() { return coreMain; }

    @Override
    public void onEnable()
    {
        coreMain = this;
        MoveEventManager.register(this);
        getServer().getPluginManager().registerEvents(new ArmorListener(), this);
        getServer().getPluginManager().registerEvents(new EntityDamage(), this);
    }

    public void onDisable() {}
}
