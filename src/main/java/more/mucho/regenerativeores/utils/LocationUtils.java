package more.mucho.regenerativeores.utils;

import org.bukkit.Location;
import org.bukkit.World;

import java.util.HashMap;
import java.util.Map;

public class LocationUtils {


    private static final Map<Location, String> oreKeyCache = new HashMap<>();

    public static String getChunkKey(Location location) {
        int chunkX = location.getBlockX() >> 4;
        int chunkZ = location.getBlockZ() >> 4;
        return getChunkKey(location.getWorld(),chunkX,chunkZ);
    }
    public static String getChunkKey(World world,int chunkX,int chunkZ) {
        return world.getName() + "," + chunkX + "," + chunkZ;
    }

    public static String getOreKey(Location location) {
        return oreKeyCache.computeIfAbsent(location, loc ->
                loc.getBlockX() + "," + loc.getBlockY() + "," + loc.getBlockZ()
        );
    }

    public static int chunkX(Location location) {
        return location.getBlockX() >> 4;
    }
    public static int chunkZ(Location location) {
        return location.getBlockZ() >> 4;
    }
}
