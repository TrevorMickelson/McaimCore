package com.codepunisher.mcaimcore;

import com.codepunisher.mcaimcore.brawlstuff.BuffedArmor;
import com.codepunisher.mcaimcore.brawlstuff.CraftFix;
import com.codepunisher.mcaimcore.brawlstuff.OldEnchanting;
import com.codepunisher.mcaimcore.brawlstuff.SoupHeal;
import com.codepunisher.mcaimcore.brawlstuff.crates.OpenListener;
import com.codepunisher.mcaimcore.brawlstuff.crates.custom_items.Obstroyer;
import com.codepunisher.mcaimcore.gui.AbstractGUI;
import com.codepunisher.mcaimcore.listeners.ArmorListener;
import org.bukkit.plugin.java.JavaPlugin;

public class CoreMain extends JavaPlugin
{
    private static CoreMain coreMain;
    public static CoreMain getInstance() { return coreMain; }

    @Override
    public void onEnable()
    {
        // Setting the core main object to class instance
        coreMain = this;

        //MoveEventManager.register(this);
        getServer().getPluginManager().registerEvents(new ArmorListener(), this);
        //getServer().getPluginManager().registerEvents(new HungerFix(), this);
        getServer().getPluginManager().registerEvents(new SoupHeal(), this);
        getServer().getPluginManager().registerEvents(new BuffedArmor(), this);
        getServer().getPluginManager().registerEvents(new CraftFix(), this);
        getServer().getPluginManager().registerEvents(new OldEnchanting(), this);

        getServer().getPluginManager().registerEvents(new OpenListener(), this);
        getServer().getPluginManager().registerEvents(new Obstroyer(), this);

        // Registering abstract gui listener
        AbstractGUI.initializeListeners(this);

        // Registering command
        new StuidPotionCommand();
    }

    public void onDisable() {}
}
