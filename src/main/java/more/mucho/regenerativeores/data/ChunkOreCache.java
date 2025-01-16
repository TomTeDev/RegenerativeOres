package more.mucho.regenerativeores.data;

import org.bukkit.Location;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Optional;

import static more.mucho.regenerativeores.utils.LocationUtils.getOreKey;

public class ChunkOreCache {
    private final String worldName;
    private final int chunkX;
    private final int chunkZ;
    private final HashMap<String, Integer> ores;

    public ChunkOreCache(String worldName, int chunkX, int chunkZ) {
        this.worldName = worldName;
        this.chunkX = chunkX;
        this.chunkZ = chunkZ;
        this.ores = new HashMap<>();
    }

    public HashSet<String> getOresLocations(int oreID){
        HashSet<String> oreLocations = new HashSet<>();
        ores.forEach((key, value) -> {
            if(value == oreID)oreLocations.add(key);
        });
        return oreLocations;
    }

    public ChunkOreCache(String worldName, int chunkX, int chunkZ, HashMap<String, Integer> ores) {
        this.worldName = worldName;
        this.chunkX = chunkX;
        this.chunkZ = chunkZ;
        this.ores = ores;
    }

    public Optional<Integer> getOreID(Location location) {
        return Optional.ofNullable(ores.get(getOreKey(location)));
    }


    public void addOre(Location location, int oreID) {
        addOre(getOreKey(location),oreID);

    }
    public void addOre(String key,int oreID){
        ores.put(key, oreID);
    }

    public void removeOre(Location location) {
        ores.remove(getOreKey(location));
    }

    private void checkChunk(Location location) {
        int chunkX = location.getBlockX() >> 4;
        int chunkZ = location.getBlockZ() >> 4;
        if (chunkX != this.chunkX || chunkZ != this.chunkZ)
            throw new RuntimeException("Tried to obtain ore from chunk " + chunkX + "/" + chunkZ + " out of stored" + this.chunkX + "/" + this.chunkZ + " chunk");
    }
}
