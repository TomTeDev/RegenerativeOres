package more.mucho.regenerativeores.data;

import more.mucho.regenerativeores.ores.Ore;
import more.mucho.regenerativeores.utils.LocationUtils;
import org.bukkit.Location;
import org.bukkit.configuration.ConfigurationSection;
import org.checkerframework.checker.nullness.qual.NonNull;

import java.util.HashMap;
import java.util.List;
import java.util.Optional;

import static more.mucho.regenerativeores.utils.LocationUtils.getChunkKey;

public class OresCacheImpl implements OresCache{
    private final HashMap<String, ChunkOreCache> chunkOres = new HashMap<>();

    public OresCacheImpl() {

    }

    public boolean isOre(Location location) {
        return getOreAt(location).isPresent();
    }

    public Optional<Integer> getOreAt(Location location) {
        return getChunkOreCache(location, true)
                .flatMap(chunkOreCache -> {
                    return chunkOreCache.getOreID(location);
                });
    }

    public void addOre(Location location, int oreID) {
        getChunkOreCache(location, true).ifPresent(chunkOreCache -> chunkOreCache.addOre(location, oreID));
    }

    @Override
    public void addBatch(String worldName, int chunkX, int chunkZ, List<String> oresArray, int oreID) {
        getChunkOreCache(worldName,chunkX,chunkZ,true).get().addBatch(oresArray,oreID);

    }
    public void clearChunk(String worldName,int chunkX,int chunkZ){
        String chunkKey = getChunkKey(worldName, chunkX, chunkZ);
        chunkOres.remove(chunkKey);
    }

    public Optional<ChunkOreCache> getChunkOreCache(@NonNull Location location, boolean forceCreate) {
        return getChunkOreCache(location.getWorld().getName(), location.getBlockX() >> 4, location.getBlockZ() >> 4, forceCreate);
    }
    public Optional<ChunkOreCache> getChunkOreCache(String worldName, int chunkX, int chunkZ, boolean forceCreate) {
        String chunkKey = getChunkKey(worldName, chunkX, chunkZ);
        ChunkOreCache chunkOreCache = chunkOres.getOrDefault(chunkKey, null);
        if (!forceCreate) return Optional.ofNullable(chunkOreCache);
        if (chunkOreCache == null) {
            chunkOreCache = new ChunkOreCache(worldName.toLowerCase(), chunkX,chunkZ);
            chunkOres.put(chunkKey, chunkOreCache);
        }
        return Optional.of(chunkOreCache);
    }

    public void removeOre(Location location) {
        getChunkOreCache(location, false).ifPresent(chunkOreCache -> chunkOreCache.removeOre(location));
    }

    public void clear() {
        chunkOres.clear();
    }

}
