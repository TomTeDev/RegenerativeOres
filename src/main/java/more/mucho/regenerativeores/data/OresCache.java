package more.mucho.regenerativeores.data;

import more.mucho.regenerativeores.ores.Ore;
import org.bukkit.Location;
import org.checkerframework.checker.nullness.qual.NonNull;

import java.util.List;
import java.util.Optional;

public interface OresCache {
    boolean isOre(Location location);

    Optional<Integer> getOreAt(Location location);

    void addOre(Location location, int oreID);
    void addBatch(String worldName, int chunkX, int chunkZ, List<String> oresArray, int oreID);
     Optional<ChunkOreCache> getChunkOreCache(@NonNull Location location, boolean forceCreate);
    void removeOre(Location location);

    void clear();
    void clearChunk(String worldName,int chunkX,int chunkZ);

}
