package configuration;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.configuration.serialization.ConfigurationSerialization;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.Plugin;

import javax.xml.bind.DatatypeConverter;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.*;

/**
 * This class is designed to make my life
 * LESS FUCKING SHIT WHEN DOING CONFIGS
 */
public class ConfigAPI
{
    /**
     * @author CodePunisher
     * @param loc
     * @return string from location
     */
    public static String locationToString(Location loc) {
        // Location info
        World world = Objects.requireNonNull(loc.getWorld());
        double x = loc.getX();
        double y = loc.getY();
        double z = loc.getZ();
        float yaw = loc.getYaw();
        float pitch = loc.getPitch();

        // Returning string
        return world.getName() + ":" + x + ":" + y + ":" + z + ":" + yaw + ":" + pitch;
    }

    /**
     * @author CodePunisher
     * @param string
     * @return location from string
     */
    public static Location stringToLocation(String string) {
        String[] args = string.split(":");

        World world = Bukkit.getWorld(Objects.requireNonNull(args[0]));
        double x = Double.parseDouble(args[1]);
        double y = Double.parseDouble(args[2]);
        double z = Double.parseDouble(args[3]);
        float yaw = Float.parseFloat(args[4]);
        float pitch = Float.parseFloat(args[5]);

        Location loc = new Location(world, x, y, z);
        loc.setYaw(yaw);
        loc.setPitch(pitch);

        return loc;
    }

    /**
     * @author ElDzi
     * Gets items and puts them into a string
     */
    public static String itemsToString(ItemStack[] items) {
        try {
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            ObjectOutputStream oos = new ObjectOutputStream(bos);
            oos.writeObject(serializeItemStack(items));
            oos.flush();
            return DatatypeConverter.printBase64Binary(bos.toByteArray());
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    /**
     * @author ElDzi
     * Gets string and turns it into an itemstack array
     * @return itemstack array
     */
    @SuppressWarnings("unchecked")
    public static ItemStack[] stringToItems(String s) {
        try {
            ByteArrayInputStream bis = new ByteArrayInputStream(DatatypeConverter.parseBase64Binary(s));
            ObjectInputStream ois = new ObjectInputStream(bis);
            return deserializeItemStack((Map<String, Object>[]) ois.readObject());
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return new ItemStack[] {new ItemStack(Material.AIR) };
    }

    /** @author ElDzi */
    @SuppressWarnings("unchecked")
    private static Map<String, Object>[] serializeItemStack(ItemStack[] items) {

        Map<String, Object>[] result = new Map[items.length];

        for (int i = 0; i < items.length; i++) {
            ItemStack is = items[i];
            if (is == null) {
                result[i] = new HashMap<>();
            }
            else {
                result[i] = is.serialize();
                if (is.hasItemMeta()) {
                    result[i].put("meta", is.getItemMeta().serialize());
                }
            }
        }

        return result;
    }

    /** @author ElDzi */
    @SuppressWarnings("unchecked")
    private static ItemStack[] deserializeItemStack(Map<String, Object>[] map) {
        ItemStack[] items = new ItemStack[map.length];

        for (int i = 0; i < items.length; i++) {
            Map<String, Object> s = map[i];
            if (s.size() == 0) {
                items[i] = null;
            }
            else {
                try {
                    if (s.containsKey("meta")) {
                        Map<String, Object> im = new HashMap<>(
                                (Map<String, Object>) s.remove("meta"));
                        im.put("==", "ItemMeta");
                        ItemStack is = ItemStack.deserialize(s);
                        is.setItemMeta((ItemMeta) ConfigurationSerialization
                                .deserializeObject(im));
                        items[i] = is;
                    }
                    else {
                        items[i] = ItemStack.deserialize(s);
                    }
                }
                catch (Exception e) {
                    e.printStackTrace();
                    items[i] = null;
                }
            }

        }

        return items;
    }

    /**
     * @author CodePunisher
     * Loads a file for plugin
     * @param plugin
     * @param name
     */
    public static void loadFile(Plugin plugin, String name) {
        DataFile dataFile = new DataFile(name, plugin);
        dataFile.createFile();
    }

    /**
     * @author CodePunisher
     * @param plugin
     * @param name
     * @return data file
     */
    public static DataFile getDataFile(Plugin plugin, String name) {
        return new DataFile(name, plugin);
    }
}
