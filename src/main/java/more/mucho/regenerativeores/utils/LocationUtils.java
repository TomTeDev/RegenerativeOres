package more.mucho.regenerativeores.utils;

import org.bukkit.Location;

import java.util.HashMap;
import java.util.Map;

public class LocationUtils {

    private static final Map<Location, String> chunkKeyCache = new HashMap<>();
    private static final Map<Location, String> oreKeyCache = new HashMap<>();

    public static String getChunkKey(Location location) {
        return chunkKeyCache.computeIfAbsent(location, loc -> {
            int chunkX = loc.getBlockX() >> 4;
            int chunkZ = loc.getBlockZ() >> 4;
            return loc.getWorld().getName() + "," + chunkX + "," + chunkZ;
        });
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
