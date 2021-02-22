package com.mcaim.core.util;
import java.awt.Color;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.apache.commons.lang.WordUtils;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 * This is a chat utility api
 * class used for messaging but
 * also string building for
 * things like custom items, etc
 */
public class C {
    /**
     * New additional colors (because 1.16 is awesome)
     * I'm not adding the bukkit color codes here because
     * I can just do that via the "translate" method, and
     * it already exists in another class
     *
     * These colors don't seem to have console support yet
     */
    public static final String BROWN = ChatColor.of(new Color(130, 67, 20)).toString();
    public static final String ORANGE = ChatColor.of(new Color(255, 140, 0)).toString();
    public static final String CRIMSON = ChatColor.of(new Color(220, 20, 60)).toString();
    public static final String PINK = ChatColor.of(new Color(255, 153, 255)).toString();
    public static final String DEEP_PINK = ChatColor.of(new Color(255, 20, 147)).toString();
    public static final String INDIGO = ChatColor.of(new Color(75, 0, 130)).toString();
    public static final String FOREST_GREEN = ChatColor.of(new Color(12, 144, 36)).toString();

    // Symbols
    public static final char COMMAND_SYMBOL = '●';
    public static final char INFO = '*';
    public static final char WARN_SYMBOL = '✘';

    // Prefixes for easier messaging
    public static final String FAIL = ChatColor.DARK_GRAY + "[" + CRIMSON + COMMAND_SYMBOL + ChatColor.DARK_GRAY + "] " + ChatColor.GRAY;
    public static final String SUCCESS = ChatColor.DARK_GRAY + "[" + FOREST_GREEN + COMMAND_SYMBOL + ChatColor.DARK_GRAY + "] " + ChatColor.GRAY;
    public static final String INFORM = ChatColor.DARK_GRAY + "" + ChatColor.BOLD + "> " + ChatColor.GRAY;
    public static final String WARN = ChatColor.DARK_RED + "" + WARN_SYMBOL + " " + ChatColor.RED;

    /**
     * Title message for player
     */
    public static void title(Player p, String title, String sub) {
        p.sendTitle(trans(title), trans(sub), 1, 60, 1);
    }

    /**
     * Action bar message for player
     */
    public static void action(Player p, String msg) {
        p.spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacyText(trans(msg)));
    }

    /**
     * Universal no permission message
     * so that everything matches
     */
    public static void noPerm(CommandSender s) {
        s.sendMessage(WARN + "You don't have permission to do this!");
    }

    public static void noPerm(Player p) {
        p.sendMessage(WARN + "You don't have permission to do this!");
    }

    public static void msg(CommandSender s, String msg) { s.sendMessage(C.trans(msg)); }

    public static void msg(Player p, String msg) { p.sendMessage(C.trans(msg)); }

    /**
     * This formats a string which basically
     * means that it would make "COW_SPAWNER"
     * or "the cat ran" look like "Cow Spawner"
     * and "The Cat Ran"
     *
     * @param s the string to format
     * @return String
     */
    public static String format(String s) {
        if (s.contains("_"))
            s = s.replaceAll("_", " ");

        return WordUtils.capitalizeFully(s);
    }

    /**
     * This translates the character '&'
     */
    public static String trans(String s) { return ChatColor.translateAlternateColorCodes('&', s); }
}
