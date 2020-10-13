package com.codepunisher.mcaimcore;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Objects;

public abstract class CommandHandler implements CommandExecutor
{
    public abstract void execute(CommandSender sender, Command cmd, String[] args);

    private final String permission;
    private final boolean canConsoleUse;
    private final String[] catchMessage;
    private final boolean printTrace;

    public CommandHandler(JavaPlugin plugin, String[] commands, String permission, boolean canConsoleUse, String[] catchMessage, boolean printTrace) {
        this.permission = permission;
        this.canConsoleUse = canConsoleUse;
        this.catchMessage = catchMessage;
        this.printTrace = printTrace;

        // Registering commands
        for (String command : commands) {
            Objects.requireNonNull(plugin.getCommand(command)).setExecutor(this);
        }
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (this.permission != null && !sender.hasPermission(this.permission)) {
            sender.sendMessage(ChatColor.RED + "You are unworthy of using this great command!");
            return true;
        }

        if (!this.canConsoleUse && !(sender instanceof Player)) {
            sender.sendMessage(ChatColor.RED + "Sorry, but only players can use this command!");
            return true;
        }

        try {
            execute(sender, cmd, args);
        } catch (Exception e) {
            if (this.printTrace)
                e.printStackTrace();

            if (this.catchMessage != null) {
                for (String string : this.catchMessage)
                    sender.sendMessage(string);
            }
        }
        return true;
    }
}
