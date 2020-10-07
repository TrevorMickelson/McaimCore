package main;

import listeners.ArmorListener;
import listeners.EntityDamage;
import listeners.PlayerMoving;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

public class CoreMain extends JavaPlugin implements Listener
{
    private static CoreMain coreMain;
    public static CoreMain getInstance() { return coreMain; }

    @Override
    public void onEnable()
    {
        coreMain = this;
        getServer().getPluginManager().registerEvents(new PlayerMoving(), this);
        getServer().getPluginManager().registerEvents(new ArmorListener(), this);
        getServer().getPluginManager().registerEvents(new EntityDamage(), this);
    }

    public void onDisable() {}
}
