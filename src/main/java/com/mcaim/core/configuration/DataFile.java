package com.mcaim.core.configuration;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;

public class DataFile
{
    /** Instances variables for class */
    private final FileConfiguration configManager;
    private final File dataFile;
    private final String fileName;

    /**
     * Creates data file object
     */
    public DataFile(JavaPlugin plugin, String name) {
        this.fileName = name;
        this.dataFile = new File(plugin.getDataFolder(), fileName);
        this.configManager = YamlConfiguration.loadConfiguration(dataFile);
        createFile();
    }

    /** Creates and loads it */
    public void createFile() {
        try {
            if (!dataFile.exists()) {
                YamlConfiguration config = YamlConfiguration.loadConfiguration(dataFile);
                config.save(dataFile);
            }
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    /** @return string name of file */
    public String getFileName() { return this.fileName; }

    /** @return file configuration object */
    public FileConfiguration getData() { return this.configManager; }

    /** Saves data to the file */
    public void saveData() {
        try {
            configManager.save(dataFile);
        } catch(Exception e) {
            e.printStackTrace();
        }
    }
}
