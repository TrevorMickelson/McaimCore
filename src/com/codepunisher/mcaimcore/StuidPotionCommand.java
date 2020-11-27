package com.codepunisher.mcaimcore;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class StuidPotionCommand extends CommandHandler
{
    public StuidPotionCommand() {
        super(CoreMain.getInstance(), new String[] { "blindness" }, "blindness.use", false, null, false);
    }

    @Override
    public void execute(CommandSender sender, Command cmd, String[] args) {
        boolean blindness = args[0].equalsIgnoreCase("on");

        for (Player p : Bukkit.getOnlinePlayers()) {
            if (blindness) {
                if (!p.hasPotionEffect(PotionEffectType.BLINDNESS)) {
                    p.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, 10000, 1));
                    p.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 10000, 2));
                }
            } else {
                for (PotionEffect potionEffect : p.getActivePotionEffects())
                    p.removePotionEffect(potionEffect.getType());
            }
        }

        sender.sendMessage(ChatColor.GREEN + "Command executed!");
    }
}
