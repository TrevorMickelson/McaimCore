package listeners;

import events.PlayerStartMovingEvent;
import events.PlayerStopMovingEvent;
import main.CoreMain;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.HashMap;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * This class is used to make the
 * player stop moving event function
 */
public class PlayerMoving implements Listener
{
    // Instance of main class
    private CoreMain plugin = CoreMain.getInstance();

    /** User moving hash map storage */
    private HashMap<UUID, Boolean> moving = new HashMap<>();
    private boolean isMoving(UUID uuid) { return this.moving.get(uuid); }
    private void setMoving(UUID uuid, boolean moving) { this.moving.replace(uuid, moving); }
    private void removeMoveUser(UUID uuid) { this.moving.remove(uuid); }

    /**
     * Stores player in move hash and
     * starts the async move timer
     * Updates users move to true
     *
     * @param event event
     */
    @EventHandler
    public void onMove(PlayerMoveEvent event) {
        Player player = event.getPlayer();

        if (!this.moving.containsKey(player.getUniqueId())) {
            this.moving.put(player.getUniqueId(), true);
            asyncMoveTask(player);

            // Calling player start moving event (this only when they start moving)
            PlayerStartMovingEvent e = new PlayerStartMovingEvent(player);
            Bukkit.getPluginManager().callEvent(e);

        } else {
            setMoving(player.getUniqueId(), true);
        }
    }

    /**
     * This basically functions by setting
     * the player move to false constantly
     * whilst incrementing a counter
     *
     * The player stop event gets called
     * when the counter is equal to 2
     *
     * However if the player keeps moving
     * the counter gets reset to 0
     *
     * @param player player
     */
    private void asyncMoveTask(Player player) {
        AtomicInteger counter = new AtomicInteger();
        new BukkitRunnable() {
            @Override
            public void run()
            {
                if (!player.isOnline()) {
                    cancel();
                    removeMoveUser(player.getUniqueId());
                }

                if (isMoving(player.getUniqueId())) {
                    counter.set(0);
                    setMoving(player.getUniqueId(), false);
                } else {
                    // Counter (this is how to determine if they stop moving)
                    // I'm basing this off of is they stop moving for <x> time
                    counter.set(counter.get() + 1);
                }

                // Setting move to false
                if (counter.get() >= 2) {

                    new BukkitRunnable()
                    {
                        @Override
                        public void run() {
                            PlayerStopMovingEvent event = new PlayerStopMovingEvent(player);
                            Bukkit.getPluginManager().callEvent(event);
                            setMoving(player.getUniqueId(), false);
                            removeMoveUser(player.getUniqueId());
                            cancel();
                        }
                    }.runTaskLater(plugin, 1L);

                    cancel();
                }
            }

        }.runTaskTimerAsynchronously(plugin, 0L, 1L);
    }
}
