package com.codepunisher.mcaimcore.models;

import com.codepunisher.mcaimcore.CoreMain;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

/**
 * This class is just a simple
 * way to make bukkit tasks a bit easier
 *
 * This is specifically for task timers
 */
public abstract class ITask {
    private BukkitTask task;
    private int time;

    // Constructor for setting initial time
    public ITask(int time) { this.time = time; }

    public int getTime() { return this.time; }
    public void setTime(int time) { this.time = time; }

    // Running the bukkit task async or sync
    public void startTask(int timer, boolean async) {
        if (!async) {
            task = new BukkitRunnable() {
                @Override
                public void run() {
                    if (getTime() == 0) {
                        endTask();
                        task.cancel();
                    }

                    setTime(getTime() - 1);
                }
            }.runTaskTimer(CoreMain.getInstance(), 0L, timer);
        } else {
            task = new BukkitRunnable() {
                @Override
                public void run() {
                    if (getTime() == 0) {
                        endTask();
                        task.cancel();
                    }

                    setTime(getTime() - 1);
                }
            }.runTaskTimerAsynchronously(CoreMain.getInstance(), 0L, timer);
        }
    }

    // Stopping task
    public void stopTask() { if (!task.isCancelled()) task.cancel(); }

    // What will execute after the timer (if anything)
    public abstract void endTask();
}
