package more.mucho.regenerativeores.data;

import more.mucho.regenerativeores.ores.Ore;
import org.bukkit.Location;
import org.checkerframework.checker.nullness.qual.NonNull;

import java.util.HashMap;
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

    private Optional<ChunkOreCache> getChunkOreCache(@NonNull Location location, boolean forceCreate) {
        ChunkOreCache chunkOreCache = chunkOres.getOrDefault(getChunkKey(location), null);
        if (!forceCreate) return Optional.ofNullable(chunkOreCache);
        if (chunkOreCache == null) {
            chunkOreCache = new ChunkOreCache(location.getWorld().getName(), location.getBlockX() >> 4, location.getBlockZ() >> 4);
            chunkOres.put(getChunkKey(location), chunkOreCache);
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
