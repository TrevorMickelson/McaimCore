package com.mcaim.core.scheduler;

import com.mcaim.core.Core;
import org.bukkit.Bukkit;
import org.bukkit.scheduler.BukkitScheduler;
import org.bukkit.scheduler.BukkitTask;

/**
 * This is essentially a BukkitTask
 * wrapper, designed to make runnables
 * a bit easier/shorter to code
 */
public class TaskTimer {
    private final boolean sync;             // If task is sync or async
    private long delay = 0;                 // Delay on task
    private long interval = 0;              // The task interval
    private BukkitTask task;                // The bukkit task itself

    /**
     * Constructor for setting sync boolean
     */
    public TaskTimer(boolean sync) { this.sync = sync; }

    /**
     * Updating delay long value
     * @param delay The delay on runnable
     */
    public TaskTimer delay(long delay) {
        this.delay = delay;
        return this;
    }

    /**
     * Updating the interval value
     * @param interval The interval on runnable
     */
    public TaskTimer interval(long interval) {
        this.interval = interval;
        return this;
    }

    /**
     * This is what actually initializes
     * the BukkitTask scheduler
     *
     * Instantiating different types of
     * Runnables based on if the user updates
     * the delay/interval values/wants sync or async
     *
     * @param runnable Abstract runnable method
     */
    public void run(Runnable runnable) {
        Core plugin = Core.getInstance();
        BukkitScheduler sch = Bukkit.getScheduler();

        // Sync check
        if (sync) {
            if (delay == 0 && interval == 0) {
                task = sch.runTask(Core.getInstance(), runnable);
            } else if (delay > 0 && interval == 0) {
                task = sch.runTaskLater(plugin, runnable, delay);
            } else {
                task = sch.runTaskTimer(plugin, runnable, delay, interval);
            }
        } else { // Async check
            if (delay == 0 && interval == 0) {
                task = sch.runTaskAsynchronously(plugin, runnable);
            } else if (delay > 0 && interval == 0) {
                task = sch.runTaskLaterAsynchronously(plugin, runnable, delay);
            } else {
                task = sch.runTaskTimerAsynchronously(plugin, runnable, delay, interval);
            }
        }

    }

    /**
     * Cancelling the BukkitTask
     */
    public void cancel() { task.cancel(); }
}
