package configuration;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;

import java.io.File;

public class DataFile
{
    /** Instances variables for class */
    private FileConfiguration configManager;
    private File dataFile;
    private String fileName;

    /**
     * Creates data file object
     * @param fileName
     * @param plugin
     */
    public DataFile(String fileName, Plugin plugin) {
        this.fileName = fileName;
        dataFile = new File(plugin.getDataFolder(), fileName);
        configManager = YamlConfiguration.loadConfiguration(dataFile);
    }

    /** Creates and loads it */
    public void createFile() {
        try
        {
            if (!dataFile.exists()) {
                YamlConfiguration config = YamlConfiguration.loadConfiguration(dataFile);
                config.save(dataFile);
            }
        }
        catch(Exception e)
        {
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
