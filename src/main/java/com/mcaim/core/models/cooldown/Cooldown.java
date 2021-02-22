package com.mcaim.core.models.cooldown;

import com.mcaim.core.util.C;
import com.mcaim.core.util.Util;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.List;
import java.util.UUID;

public class Cooldown {
    private static final CooldownHandler handler = CooldownHandler.getInstance();

    private final UUID uuid;
    private final String id;

    private long startTime;
    private int time = 0;
    private String color = null;
    private boolean runInCooldown = false;

    // If the cooldown has started or not
    private boolean inCooldown = false;

    // If the cooldown ignores an operator
    private boolean ignoreOp = false;

    /**
     * Stores cooldown user
     */
    public Cooldown(Player player, String id) {
        this.uuid = player.getUniqueId();
        this.id = id;
        updateStartTime();
        handler.addCooldown(this);
    }

    public UUID getUuid() { return uuid; }
    public String getId() { return id; }

    public boolean isInCooldown() {
        return getTimeLeft() > 0;
    }

    /**
     * Allows for easy setting of color
     */
    public Cooldown color(String color) {
        if (color != null)
            this.color = color;

        return this;
    }

    public Cooldown time(int time) {
        if (this.time <= 0)
            this.time = time;

        return this;
    }

    public Cooldown ignoreOp() {
        if (!this.ignoreOp)
            this.ignoreOp = true;

        return this;
    }

    private String getColor() {
        return color == null ? "&f" : color;
    }

    private long getTimeLeft() {
        return (startTime + (time * 1000)) - System.currentTimeMillis();
    }

    private void informUserOfCooldown(long timeLeft) {
        Player player = Bukkit.getPlayer(uuid);
        String color = getColor();

        if (player != null)
            player.sendMessage(C.trans(color + C.format(id) + " " + C.INFORM + "This is on cooldown for " + color + Util.formatTime(timeLeft)));
    }

    private void updateStartTime() {
        this.startTime = System.currentTimeMillis();
    }

    /**
     * Handles the actual cooldown itself
     */
    public void run(CooldownAction cooldownAction) {
        if (ignoreOp)
            return;

        // If cooldown isn't enabled
        // enabling it, and running the
        // abstract method
        if (!inCooldown) {
            inCooldown = true;

            if (!runInCooldown)
                cooldownAction.action();
            return;
        }

        long timeLeft = getTimeLeft();

        // If the cooldown is enabled,
        // but the user is still in cooldown
        if (timeLeft > 0) {
            informUserOfCooldown(timeLeft);

            if (runInCooldown)
                cooldownAction.action();
            return;
        }

        // If the cooldown is enabled
        // but the user is no longer in cooldown
        // removing the cooldown
        inCooldown = false;
        updateStartTime();
        run(cooldownAction);
    }

    /**
     * This runs the cooldown action
     * inside of the cooldown, rather than waiting
     * for the cooldown to be over to run it
     */
    public void runInCooldown(CooldownAction cooldownAction) {
        if (!runInCooldown)
            runInCooldown = true;

        run(cooldownAction);
    }

    /**
     * Gets cooldown for user that's
     * associated with a specific ID
     */
    public static Cooldown get(Player p, String id) {
        List<Cooldown> list = handler.getCoolDowns(p.getUniqueId());

        // If player doesn't have a single cooldown
        if (list == null || list.isEmpty())
            return new Cooldown(p, id);

        // Getting cooldown from players list
        Cooldown cooldown = list.stream().filter(c -> c.getId().equalsIgnoreCase(id)).findAny().orElse(null);

        // Returning cooldown if exists, other wise, returning new cooldown
        return cooldown == null ? new Cooldown(p, id) : cooldown;
    }
}
