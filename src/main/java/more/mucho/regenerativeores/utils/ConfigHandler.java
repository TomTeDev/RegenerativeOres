package more.mucho.regenerativeores.utils;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;
import org.checkerframework.checker.nullness.qual.NonNull;

import java.io.File;
import java.io.InputStream;
import java.io.InputStreamReader;

public class ConfigHandler {
    private final Plugin plugin;
    private FileConfiguration fileConfig = null;
    private File file = null;
    private final String fileName;
    private final File parentFolder;

    public ConfigHandler(Plugin plugin, @NonNull String fileName) throws Exception{
        this(plugin, fileName, plugin.getDataFolder());
    }

    public ConfigHandler(Plugin plugin, @NonNull String fileName, File parentFolder) throws Exception {
        this.plugin = plugin;
        this.fileName = fileName;
        this.parentFolder = parentFolder;
        //Create File
        saveDeafaultConfig();
        this.fileConfig = YamlConfiguration.loadConfiguration(this.file);
    }

    public void reloadConfig() {
        if (this.file == null) {
            this.file = new File(parentFolder, fileName);
        }
        this.fileConfig = YamlConfiguration.loadConfiguration(this.file);
        InputStream defaultStream = this.plugin.getResource(fileName);
        if (defaultStream != null) {
            YamlConfiguration yamlConfiguration = YamlConfiguration.loadConfiguration(new InputStreamReader(defaultStream));
            this.fileConfig.setDefaults(yamlConfiguration);
        }
    }

    public FileConfiguration getConfig() {
        if (this.fileConfig == null) reloadConfig();
        return this.fileConfig;
    }

    public void saveConfig(FileConfiguration cfg) {
        if (this.file == null) return;
        Bukkit.getScheduler().runTaskAsynchronously(this.plugin, () -> {
            try {
                cfg.save(file);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    private void saveDeafaultConfig() {
        if (this.file == null) {
            this.file = new File(parentFolder, fileName);
        }
        if (!this.file.exists()) {
            if (this.plugin.getResource(fileName) != null) {
                this.plugin.saveResource(fileName, false);
            }
        }
    }
}
