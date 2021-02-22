package com.mcaim.core.util;
;
import com.mojang.authlib.GameProfile;
import net.minecraft.server.v1_16_R3.*;
import org.bukkit.Bukkit;
import org.bukkit.craftbukkit.v1_16_R3.CraftServer;
import org.bukkit.craftbukkit.v1_16_R3.CraftWorld;
import org.bukkit.craftbukkit.v1_16_R3.entity.CraftHumanEntity;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Objects;
import java.util.UUID;

/**
 * IMPORTANT: If you update any data,
 * such as health or an inventory, you must
 * use the saveData() method afterwards
 */
public class OfflinePlayerWrapper {
    private final UUID uuid;
    private final EntityHuman entityHuman;
    private Player player;

    public OfflinePlayerWrapper(UUID uuid) {
        this.uuid = uuid;
        this.entityHuman = initEntityHuman();
    }

    public EntityHuman getEntityHuman() { return entityHuman; }

    private EntityHuman initEntityHuman() {
        String name = Bukkit.getOfflinePlayer(uuid).getName();
        MinecraftServer server = ((CraftServer) Bukkit.getServer()).getServer();
        WorldServer ws = ((CraftWorld) Objects.requireNonNull(Bukkit.getWorlds().get(0))).getHandle();
        EntityPlayer entity = new EntityPlayer(server, ws, new GameProfile(uuid, name), new PlayerInteractManager(ws));
        Player target = (Player) getBukkitEntity(entity);

        if (target == null)
            return null;

        player = target;
        target.loadData();
        return ((CraftHumanEntity) target).getHandle();
    }

    public void saveData() { player.saveData(); }

    private Entity getBukkitEntity(Object o) {
        Method getBukkitEntity = makeMethod(o.getClass(), new Class[0]);
        return (Entity) callMethod(getBukkitEntity, o, new Object[0]);
    }

    private Method makeMethod(Class<?> clazz, Class<?>[] paramaters) {
        try {
            return clazz.getDeclaredMethod("getBukkitEntity", paramaters);
        } catch (NoSuchMethodException ex) {
            return null;
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }

    private Object callMethod(Method method, Object instance, Object[] paramaters) {
        if (method == null)
            throw new RuntimeException("No such method");
        method.setAccessible(true);
        try {
            return method.invoke(instance, paramaters);
        } catch (InvocationTargetException ex) {
            throw new RuntimeException(ex.getCause());
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }
}
