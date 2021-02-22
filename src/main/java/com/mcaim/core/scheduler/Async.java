package com.mcaim.core.scheduler;

/**
 * An easy static method for
 * running TaskTimer async
 */
public class Async {
    public static TaskTimer get() { return new TaskTimer(false); }
}
