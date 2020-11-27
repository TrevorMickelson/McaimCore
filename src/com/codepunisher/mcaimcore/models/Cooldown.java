package com.codepunisher.mcaimcore.models;

import java.util.*;

/**
 * This is a simple cooldown handler
 */
public class Cooldown {
    /** Static cooldown storage*/
    public final static HashMap<String, Cooldown> staticCooldowns = new HashMap<>();

    /** Player cooldown storage */
    private final static HashMap<UUID, Cooldown> cooldownUsers = new HashMap<>();

    private final Map<String, Range> cooldowns = new HashMap<>();       // This is used for players
    private Range range;                                                // This is used for static cooldowns

    // Constructor for player cooldown
    public Cooldown(final UUID uuid, final String type, final int cooldown) {
        cooldowns.put(type, new Range(cooldown));

        if (!cooldownUsers.containsKey(uuid)) {
            cooldownUsers.put(uuid, this);
        }
    }

    // Constructor for static cooldown
    public Cooldown(final String type, final int cooldown) {
        this.range = new Range(cooldown);

        if (!staticCooldowns.containsKey(type))
            staticCooldowns.put(type, this);
    }

    /** Class object methods */
    public boolean noMoreCooldown(String type) { return getTimeLeft(type) <= 0; }
    public long getTimeLeft(String type) {
        Range newRange = range != null ? range : cooldowns.get(type);
        return ((newRange.getStartTime() / 1000) + newRange.getCooldown()) - System.currentTimeMillis() / 1000;
    }

    /** Individual player cooldowns */
    public static boolean isInCooldown(UUID uuid, String type) {
        // Checking if they're in the map first
        if (cooldownUsers.containsKey(uuid)) {
            // Now I know they're in the map
            // I need to see if the cooldown is expired
            // so I can remove them
            Cooldown cooldown = cooldownUsers.get(uuid);

            if (cooldown.cooldowns.containsKey(type)) {
                if (cooldown.noMoreCooldown(type)) {
                    cooldown.cooldowns.remove(type);
                    return false;
                } else {
                    return true;
                }
            }

            return false;
        }

        return false;
    }
    public static void startCooldown(UUID uuid, String type, int time) {
        if (cooldownUsers.containsKey(uuid)) {
            Cooldown cooldown = cooldownUsers.get(uuid);
            cooldown.cooldowns.put(type, new Range(time));
        } else {
            new Cooldown(uuid, type, time);
        }
    }
    public static void stopCooldown(UUID uuid, String type) {
        if (isInCooldown(uuid, type)) {
            Cooldown cooldown = cooldownUsers.get(uuid);
            cooldown.cooldowns.remove(type);
        }
    }
    public static void stopAllCooldowns(UUID uuid) {
        List<String> remove = new ArrayList<>();
        if (cooldownUsers.containsKey(uuid)) {
            Cooldown cooldown = cooldownUsers.get(uuid);
            remove.addAll(cooldown.cooldowns.keySet());
        }

        for (String string : remove)
            stopCooldown(uuid, string);
    }
    public static long getTimeLeft(UUID uuid, String type) { return cooldownUsers.get(uuid).getTimeLeft(type); }

    /** "static" cooldowns (which just puts every user in the same cooldown */
    public static boolean isStaticCooldown(String type) {
        if (staticCooldowns.containsKey(type)) {
            Cooldown cooldown = staticCooldowns.get(type);

            if (cooldown.noMoreCooldown(type)) {
                staticCooldowns.remove(type);
                return false;
            }

            return true;
        }

        return false;
    }
    public static void startStaticCooldown(String type, int time) {
        if (staticCooldowns.containsKey(type)) {
            Cooldown cooldown = staticCooldowns.get(type);
            cooldown.cooldowns.put(type, new Range(time));
        } else {
            new Cooldown(type, time);
        }
    }
    public static void stopStaticCooldown(String type) { staticCooldowns.remove(type); }
    public static void stopAllStaticCooldowns() { staticCooldowns.clear(); }
    public static long getStaticTimeLeft(String type) { return staticCooldowns.get(type).getTimeLeft(type); }

    // This is the range object used for each
    // Cooldown, because each cooldown needs
    // a different start time + cooldown time
    private final static class Range {
        private final long startTime;
        private final int cooldown;

        public Range(int cooldown) {
            this.startTime = System.currentTimeMillis();
            this.cooldown = cooldown;
        }

        public long getStartTime() { return this.startTime; }
        public int getCooldown() { return this.cooldown; }
    }
}
