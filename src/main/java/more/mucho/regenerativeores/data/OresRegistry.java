package more.mucho.regenerativeores.data;

import more.mucho.regenerativeores.RegenerativeOres;
import more.mucho.regenerativeores.ores.Ore;
import more.mucho.regenerativeores.utils.ConfigHandler;
import more.mucho.regenerativeores.utils.LocationUtils;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;

import java.io.File;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;

public class OresRegistry {
    public OresCache cache;

    public OresRegistry(OresCache cache) {
        this.cache = cache;
    }

    public OresCache cache(){
        return this.cache;
    }

    public void addOre(Location location,int oreID){
        cache.addOre(location,oreID);
        updateOresFileInChunk(location,oreID);
    }
    public void removeOre(Location oreLocation, int oreID){
        cache.removeOre(oreLocation);
        updateOresFileInChunk(location,oreID);
    }

    public void loadChunkOresAsync(World world, int chunkX, int chunkZ) throws Exception{
        RegenerativeOres plugin = RegenerativeOres.getPlugin(RegenerativeOres.class);
        String fileName = chunkX+"_"+chunkZ+".yml";

        ConfigHandler configHandler = new ConfigHandler(plugin,fileName,new File(plugin.getDataFolder().getAbsolutePath() +"\\chunks\\"+world.getName().toLowerCase()));
        FileConfiguration cfg = configHandler.getConfig();
        ConfigurationSection section = cfg.getConfigurationSection("ores");
        if(section == null||section.getKeys(false).isEmpty())return;
        ChunkOreCache chunkOreCache = new ChunkOreCache(world.getName(),chunkX,chunkZ);
        for(String key : section.getKeys(false)){
            int oreID = section.getInt(key);
            Optional<Ore> ore = plugin.getOres().getOre(oreID);
            if(ore.isEmpty())continue;
            List<String> oreLocations = section.getStringList(key);
            oreLocations.stream().forEach(locationString -> {
                chunkOreCache.addOre(locationString,oreID);
            });
        }

        String chunkKey = LocationUtils.getChunkKey(world,chunkX,chunkZ);
        chunkOres.put(chunkKey,chunkOreCache);
        //TODO add loading;

    }

    public void saveOreLocations(Location location,int oreID)throws Exception{
        ChunkOreCache chunkOreCache = getChunkOreCache(location,true).get();
        HashSet<String> oreLocations =  chunkOreCache.getOresLocations(oreID);
        List<String> oreLocationsArray = new ArrayList<>(oreLocations);
        int chunkX = location.getBlockX()>>4;
        int chunkZ = location.getBlockZ()>>4;
        String fileName = chunkX+"_"+chunkZ+".yml";
        ConfigHandler configHandler = new ConfigHandler(plugin,fileName,new File(plugin.getDataFolder().getAbsolutePath() +"\\chunks\\"+location.getWorld().getName().toLowerCase()));
        FileConfiguration cfg = configHandler.getConfig();
        ConfigurationSection section = cfg.getConfigurationSection("ores");
        if(section == null)return;
        cfg.set("ores."+oreID,oreLocationsArray);
        configHandler.saveConfig(cfg);
    }
}
