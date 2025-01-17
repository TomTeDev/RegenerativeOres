package more.mucho.regenerativeores.data;

import more.mucho.regenerativeores.RegenerativeOres;
import more.mucho.regenerativeores.ores.Ore;
import more.mucho.regenerativeores.utils.ConfigHandler;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;

import java.io.File;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;

public class OresService {
    private final RegenerativeOres plugin;
    private final Ores ores;
    public final OresCache cache;

    public OresService(RegenerativeOres plugin, Ores ores,OresCache cache) {
        this.plugin = plugin;
        this.cache = cache;
        this.ores = ores;
    }

    public OresCache cache() {
        return this.cache;
    }
    public Ores getOres() {
        return ores;
    }
    public Optional<Ore> getOre(Location location){
        Optional<Integer> oreIDAtLocation = cache.getOreAt(location);
        return oreIDAtLocation.flatMap(ores::getOre);
    }
    public void addOre(Location oreLocation, int oreID) {
        cache.addOre(oreLocation, oreID);
        updateChunkOresFile(oreLocation, oreID);
    }

    public void removeOre(Location oreLocation, int oreID) {
        cache.removeOre(oreLocation);
        updateChunkOresFile(oreLocation, oreID);
    }

    private CompletableFuture<Boolean> updateChunkOresFile(Location location, int oreID) {
        int chunkX = location.getBlockX()>>4;
        int chunkZ = location.getBlockZ()>>4;
        String worldName = location.getWorld().getName();

        return CompletableFuture.supplyAsync(() -> {
            try {
                Optional<ChunkOreCache>optionalChunkCache = cache.getChunkOreCache(location,true);
                if(optionalChunkCache.isEmpty())return true;
                ChunkOreCache chunkCache = optionalChunkCache.get();
                HashSet<String> oreLocationsSet = chunkCache.getOresLocations(oreID);

                String fileName = chunkX + "_" + chunkZ + ".yml";

                ConfigHandler configHandler = new ConfigHandler(plugin, fileName, new File(plugin.getDataFolder().getAbsolutePath() + "/chunks/" + worldName.toLowerCase()));
                FileConfiguration cfg = configHandler.getConfig();
                List<String>oreList = new ArrayList<>(oreLocationsSet);
                cfg.set(String.valueOf(oreID),oreList);
                configHandler.saveConfig(cfg);

            }catch (Exception exception){
                exception.printStackTrace();
                return false;
            }

            return true;
        });
    }

    /**
     * Called when chunk is loaded
     */
    public void addChunkToCache(String worldName, int chunkX, int chunkZ) {
        String fileName = chunkX + "_" + chunkZ + ".yml";

        CompletableFuture.supplyAsync(() -> {
            try {
                ConfigHandler configHandler = new ConfigHandler(plugin, fileName, new File(plugin.getDataFolder().getAbsolutePath() + "/chunks/" + worldName.toLowerCase()));
                FileConfiguration cfg = configHandler.getConfig();
                for(String key : cfg.getKeys(false)){
                    int oreID = -1;
                    try {
                        oreID = Integer.parseInt(key);
                    }catch (Exception ignored){}
                    if(oreID<0){
                        Bukkit.getLogger().severe("Incorrect oreID in file " + fileName+" key: "+key);
                        return null;
                    }
                    List<String> oresArray = cfg.getStringList(key);
                    cache.addBatch(worldName,chunkX,chunkZ,oresArray,oreID);
                }
            }catch (Exception exception){
                exception.printStackTrace();
            }
            return null;
        });

    }

    /**
     * Called when chunkIs unloaded
     */
    public void removeChunkFromCache(String worldName, int chunkX, int chunkZ) {
        cache.clearChunk(worldName,chunkX,chunkZ);
    }



}
