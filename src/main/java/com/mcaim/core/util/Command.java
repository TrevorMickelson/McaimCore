package com.mcaim.core.util;

import com.mcaim.core.models.cooldown.Cooldown;
import org.bukkit.Bukkit;
import org.bukkit.command.*;
import org.bukkit.entity.Player;

import javax.annotation.Nonnull;
import java.lang.reflect.Field;
import java.util.*;

/**
 * @author Trevor Mickelson
 */
public abstract class Command extends org.bukkit.command.Command {
    // This is what allows me to register/unregister commands
    private static CommandMap COMMAND_MAP;

    private final boolean consoleUsage;
    private final String permission;
    private final List<SubCommand> subCommands = new ArrayList<>();

    private int cooldownTime = 0;
    private String cooldownColor = "&f";
    private boolean printTracerError = false;

    // This is sent to the user when the
    // command isn't typed correctly
    private String errorDisplay = "";

    private int playerTabComplete = -2;

    // If the current object is a super command or not
    private final boolean superCommand;

    // Used specifically for tab completion
    private final HashMap<Integer, List<String>> subCompleters = new HashMap<>();

    /**
     * IMPORTANT: sub commands can NOT have
     *            any aliases set, only main
     *            or super commands can
     */
    protected Command(boolean consoleUsage, String permission, String command, String... aliases) {
        super(command);

        if (aliases.length > 0)
            setAliases(Arrays.asList(aliases));

        // Removing default usage display
        setUsage("");

        this.consoleUsage = consoleUsage;
        this.permission = permission;

        Command superCommand = getSuperCommand();
        this.superCommand = superCommand == null || superCommand == this;
    }

    /**
     * Handles Tab-Completion for commands
     *
     * Handles both types of sub commands
     * and automatic online player tab
     */
    @Nonnull
    @Override
    public List<String> tabComplete(CommandSender sender, String alias, String[] args) {
        List<String> list = new ArrayList<>();

        // Not allowing tab completion unless they have permission
        if (!senderHasPermission(sender))
            return list;

        // If I want to display online players
        // at the end of the command argument
        boolean doPlayerTabCompletion = true;
        int argsLength = args.length - 1;

        if (!superCommand)
            doPlayerTabCompletion = false;

        // Sub commands (Multi Class Type)
        if (!subCommands.isEmpty()) {
            for (SubCommand subCommand : subCommands) {
                int param = subCommand.getParam();
                String subCmd = subCommand.getSubCmd().getName().toLowerCase();

                if (argsLength == param) {

                    if (subCmd.startsWith(args[param])) {
                        list.add(subCmd);
                        doPlayerTabCompletion = false;
                    }
                }

                // For sub commands within sub commands
                if (args[param].toLowerCase().equals(subCmd))
                    list.addAll(subCommand.getSubCmd().tabComplete(sender, alias, args));
            }
        }

        // Sub commands (In Class Type)
        if (!subCompleters.isEmpty()) {
            List<String> arguments = subCompleters.get(argsLength);

            if (arguments != null) {
                for (String string : arguments) {
                    if (string.toLowerCase().startsWith(args[argsLength])) {
                        list.add(string);
                        doPlayerTabCompletion = false;
                    }
                }
            }
        }

        // Player tab completion checkers
        if (doPlayerTabCompletion && playerTabComplete != -1) {
            if (argsLength == playerTabComplete)
                Bukkit.getOnlinePlayers().forEach((p) -> { list.add(p.getName()); });

            // If I should player tab complete LAST
            if (playerTabComplete == -2 && argsLength + 1 <= getMaxArguments())
                Bukkit.getOnlinePlayers().forEach((p) -> { list.add(p.getName()); });
        }

        // Returning list
        return list;
    }

    /**
     * Executing the command as well
     * as any potential sub commands
     */
    @Override
    public boolean execute(CommandSender sender, String cmd, String[] args) {
        // If not sub command
        if (subCommands.isEmpty() || args.length == 0) {
            runExecutor(sender, cmd, args);
        } else {
            boolean executed = false;

            // This basically just checks all sub
            // commands, and checks if the current argument
            // matches, then runs the sub command code and breaks
            for (SubCommand subCommand : subCommands) {
                if (!subCommand.getSubCmd().getName().equalsIgnoreCase(args[subCommand.getParam()]))
                    continue;

                executed = true;
                subCommand.getSubCmd().runExecutor(sender, cmd, args);
                break;
            }

            // If this gets hit that means the
            // user executed an invalid argument
            if (!executed)
                sender.sendMessage(C.FAIL + "That is not a valid argument!");
        }
        return false;
    }

    /**
     * This method gets passed into the
     * command method which allows me
     * to easily wrap a try catch around it
     * displaying the CMD usage to the sender
     *
     * Also allows me to check for things
     * like console usage and permission
     */
    private void runExecutor(CommandSender sender, String cmd, String[] args) {
        if (!consoleUsage && sender instanceof ConsoleCommandSender) {
            sender.sendMessage(C.FAIL + "Sorry, but consoles can't type this command!");
            return;
        }

        if (!senderHasPermission(sender)) {
            C.noPerm(sender);
            return;
        }

        try {
            // No cooldown
            if (!hasCooldown(sender)) {
                onExecute(sender, cmd, args);
            } else {
                // Cooldown implementation
                Player player = (Player) sender;
                Cooldown.get(player, getName()).color(cooldownColor).time(cooldownTime).run(() -> {
                    onExecute(sender, cmd, args);
                });
            }
        } catch (Exception e) {
            sendErrorDisplay(sender);

            if (printTracerError)
                e.printStackTrace();
        }
    }

    private int getMaxArguments() {
        int max = 0;

        if (!subCommands.isEmpty())
            max += subCommands.size();

        if (!subCompleters.isEmpty())
            max += subCompleters.size();

        if (playerTabComplete != -1)
            max += 1;

        return max;
    }

    // This is so that I can get the
    // super command of the current
    // class instance (if there is one)
    private Command getSuperCommand() {
        if (subCommands.isEmpty())
            return this;

        for (SubCommand subCommand : subCommands) {
            if (subCommand.getSubCmd() == this) {
                return subCommand.getSuperCmd();
            }
        }

        return null;
    }

    private boolean senderHasPermission(CommandSender sender) {
        return permission == null || sender.hasPermission(permission);
    }

    /**
     * Determines if there's a cooldown or not
     */
    private boolean hasCooldown(CommandSender sender) {
        return cooldownTime > 0 && sender instanceof Player;
    }

    /**
     * @param sender The sender executing the command
     * @param alias which alias the sender inputs
     * @param args all of the arguments after the command
     */
    protected abstract void onExecute(CommandSender sender, String alias, String[] args);

    protected void printError() { printTracerError = true; }

    /**
     * This is designed in the event you
     * want your super command to simply be
     * a help page for the sub commands
     */
    protected void displaySubCommands(CommandSender sender, String color) {
        String nameFormat = C.format(getName());
        sender.sendMessage("");
        sender.sendMessage(C.trans("&8" + C.INFO + " " + color + "&l" + nameFormat + " Commands &8" + C.INFO));
        sender.sendMessage("");

        for (SubCommand sub : subCommands)
            sender.sendMessage(C.trans(color + "/" + nameFormat + " " + sub.getSubCmd().getName() + " &8- &7" + sub.getDesc()));
    }


    protected void setErrorDisplay(String display) {
        Command superCmd = getSuperCommand();
        String cmd = superCmd == null ? C.format(getName()) : C.format(superCmd.getName());
        errorDisplay = display.replaceAll("<command>", cmd);
    }

    protected void sendErrorDisplay(CommandSender sender) {
        sender.sendMessage(C.FAIL + "Usage: /" + errorDisplay);
    }

    /**
     * Where within the command should all
     * online players be tab-able
     *
     * Set to "-1" to disable the feature
     * Set to "-2" to default to the last value
     */
    protected void setPlayerTabCompletion(int argument) { playerTabComplete = argument; }

    public void registerCommand() { COMMAND_MAP.register("", this); }

    /**
     * This registers a sub command
     * this is specifically designed for
     * multi-class support and should really
     * only be used for something that has
     * a large amount of sub commands
     */
    protected void registerSubCommand(Command cmd, String desc, int param) {
        subCommands.add(new SubCommand(this, cmd, desc, param));
    }

    /**
     * This registers sub commands designed
     * for in-class sub commands, in the event
     * you only need like 1-3 sub commands
     */
    protected void registerSubCommand(int arg, String... arguments) {
        if (subCompleters.containsKey(arg)) {
            subCompleters.get(arg).addAll(Arrays.asList(arguments));
            return;
        }

        subCompleters.put(arg, Arrays.asList(arguments));
    }

    protected void registerCooldown(int time, String color) {
        this.cooldownTime = time;
        this.cooldownColor = color;
    }

    /**
     * Same as above, but with a different parameter
     */
    protected void registerSubCommand(int arg, List<String> arguments) {
        subCompleters.put(arg, arguments);
    }

    /*
      This is the static constructor
      for the command class, this sets
      the command map which allows me
      to register commands without
      the plugin.yml
     */
    static {
        try {
            // Initializing command map
            final Field bukkitCommandMap = Bukkit.getServer().getClass().getDeclaredField("commandMap");
            bukkitCommandMap.setAccessible(true);
            COMMAND_MAP = (CommandMap) bukkitCommandMap.get(Bukkit.getServer());
        } catch (Exception e) {
            System.err.println("Couldn't access command map");
        }
    }

    private static class SubCommand {
        private final Command superCmd;
        private final Command subCmd;
        private final String desc;
        private final int param;

        public SubCommand(Command superCmd, Command cmd, String desc, int param) {
            this.superCmd = superCmd;
            this.subCmd = cmd;
            this.desc = desc;
            this.param = param;
        }

        public final Command getSuperCmd() { return superCmd; }
        public final Command getSubCmd() { return subCmd; }
        public final String getDesc() { return desc; }
        public final int getParam() { return param; }
    }
}
