package more.mucho.regenerativeores.data;

import more.mucho.regenerativeores.ores.Ore;
import org.bukkit.Bukkit;
import org.bukkit.Location;

import java.util.HashMap;
import java.util.Optional;

import static more.mucho.regenerativeores.utils.LocationUtils.getOreKey;

public class ChunkOreCache {
    private final String worldName;
    private final int chunkX;
    private final int chunkZ;
    private final HashMap<String, Ore> ores;

    public ChunkOreCache(String worldName, int chunkX, int chunkZ) {
        this.worldName = worldName;
        this.chunkX = chunkX;
        this.chunkZ = chunkZ;
        this.ores = new HashMap<>();
    }

    public ChunkOreCache(String worldName, int chunkX, int chunkZ, HashMap<String, Ore> ores) {
        this.worldName = worldName;
        this.chunkX = chunkX;
        this.chunkZ = chunkZ;
        this.ores = ores;
    }

    public Optional<Ore> getOre(Location location) {
        Bukkit.broadcastMessage("Get ore for key:"+getOreKey(location));
        return Optional.ofNullable(ores.get(getOreKey(location)));
    }


    public void addOre(Location location, Ore ore) {
        Bukkit.broadcastMessage("Add ore for key: "+getOreKey(location));
        ores.put(getOreKey(location), ore);
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
