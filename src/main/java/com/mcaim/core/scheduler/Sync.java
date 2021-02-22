package com.mcaim.core.scheduler;

/**
 * An easy static method for
 * running TaskTimer sync
 */
public class Sync {
    public static TaskTimer get() { return new TaskTimer(true); }
}
