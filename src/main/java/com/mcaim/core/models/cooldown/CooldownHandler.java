package com.mcaim.core.models.cooldown;

import com.mcaim.core.scheduler.Sync;

import java.util.*;

public class CooldownHandler {
    private static final CooldownHandler cooldownHandler = new CooldownHandler();
    public static CooldownHandler getInstance() { return cooldownHandler; }

    private final Map<UUID, List<Cooldown>> cooldownUsers = new HashMap<>();

    /**
     * Adds a new cooldown for
     * user and stores it
     */
    public void addCooldown(Cooldown cooldown) {
        UUID uuid = cooldown.getUuid();

        if (!cooldownUsers.containsKey(uuid)) {
            List<Cooldown> cooldownList = new ArrayList<>();
            cooldownList.add(cooldown);
            cooldownUsers.put(uuid, cooldownList);
        } else {
            cooldownUsers.get(uuid).add(cooldown);
        }
    }

    public List<Cooldown> getCoolDowns(UUID uuid) {
        return cooldownUsers.get(uuid);
    }

    /**
     * Clears all cooldown maps every minute
     * to avoid any nasty memory leak
     */
    public void initClearTask() {
        Sync.get().interval(1200L).run(() -> {
            Iterator<Map.Entry<UUID, List<Cooldown>>> iterator = cooldownUsers.entrySet().iterator();

            while (iterator.hasNext()) {
                Map.Entry<UUID, List<Cooldown>> entry = iterator.next();
                entry.getValue().stream().filter(e -> !e.isInCooldown()).findAny().ifPresent(cooldown -> entry.getValue().remove(cooldown));

                if (entry.getValue().isEmpty())
                    iterator.remove();
            }
        });
    }
}
