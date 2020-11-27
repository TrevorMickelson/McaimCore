package com.codepunisher.mcaimcore.gui;

import org.bukkit.Sound;

/**
 * Used to execute certain actions
 * via the registered click action
 */
public class Action
{
    private final int slot;
    private final Sound sound;

    public Action(int slot, Sound sound) {
        this.slot = slot;
        this.sound = sound;
    }

    public int getSlot() { return this.slot; }
    public Sound getSound() { return this.sound; }
}
