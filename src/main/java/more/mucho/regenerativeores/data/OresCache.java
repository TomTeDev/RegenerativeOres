package more.mucho.regenerativeores.data;

import more.mucho.regenerativeores.ores.Ore;
import org.bukkit.Location;

import java.util.Optional;

public interface OresCache {
    boolean isOre(Location location);

    Optional<Integer> getOreAt(Location location);

    void addOre(Location location, int oreID);

    // Optional<ChunkOreCache> getChunkOreCache(@NonNull Location location, boolean forceCreate);
    void removeOre(Location location);

    void clear();

}
