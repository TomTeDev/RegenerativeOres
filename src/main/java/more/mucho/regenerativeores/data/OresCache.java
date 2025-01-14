package more.mucho.regenerativeores.data;

import more.mucho.regenerativeores.RegenerativeOres;
import more.mucho.regenerativeores.ores.Ore;
import org.bukkit.Location;
import org.bukkit.World;
import org.checkerframework.checker.nullness.qual.NonNull;
import static more.mucho.regenerativeores.utils.LocationUtils.getChunkKey;
import java.util.HashMap;
import java.util.Optional;

public class OresCache {
    private static OresCache INSTANCE = null;
    private static RegenerativeOres plugin = null;
    private final HashMap<String, ChunkOreCache> chunkOres = new HashMap<>();

    private OresCache() {

    }

    public static synchronized OresCache i() {
        if (INSTANCE == null) {
            INSTANCE = new OresCache();
            plugin = RegenerativeOres.getPlugin(RegenerativeOres.class);
        }
        return INSTANCE;
    }

    public boolean isOre(Location location) {
        return getOre(location).isPresent();
    }
    public Optional<Ore> getOre(Location location) {
        return getChunkOreCache(location, true)
                .flatMap(chunkOreCache -> {
                    Optional<Integer> oreID = chunkOreCache.getOreID(location);
                    if(oreID.isEmpty())return Optional.empty();
                    return plugin.getOres().getOre(oreID.get());
                });
    }
    public void addOre(Location location, Ore ore) {
        getChunkOreCache(location,true).ifPresent(chunkOreCache -> chunkOreCache.addOre(location,ore.getID()));
    }
    private Optional<ChunkOreCache> getChunkOreCache(@NonNull Location location,boolean forceCreate) {
        ChunkOreCache chunkOreCache = chunkOres.getOrDefault(getChunkKey(location),null);
        if(!forceCreate)return Optional.ofNullable(chunkOreCache);
        if(chunkOreCache == null){
            chunkOreCache = new ChunkOreCache(location.getWorld().getName(),location.getBlockX()>>4,location.getBlockZ()>>4);
            chunkOres.put(getChunkKey(location),chunkOreCache);
        }
        return Optional.of(chunkOreCache);
    }
    private void removeOre(Location location) {
        getChunkOreCache(location,false).ifPresent(chunkOreCache -> chunkOreCache.removeOre(location));
    }
    public void loadChunkOresAsync(World world, int x, int z) {
        //TODO add loading;
    }
    public void clear(){
        chunkOres.clear();
    }

}
