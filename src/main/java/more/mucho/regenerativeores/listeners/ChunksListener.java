package more.mucho.regenerativeores.listeners;

import more.mucho.regenerativeores.data.OresService;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.world.ChunkLoadEvent;
import org.bukkit.event.world.ChunkUnloadEvent;

public class ChunksListener implements Listener {
    private final OresService oresService;

    public ChunksListener(OresService oresService) {
        this.oresService = oresService;
    }

    @EventHandler
    public void onChunkLoad(ChunkLoadEvent event) {
        oresService.addChunkToCache(event.getWorld().getName(), event.getChunk().getX(), event.getChunk().getZ());
    }

    @EventHandler
    public void onChunkLoad(ChunkUnloadEvent event) {
        oresService.removeChunkFromCache(event.getWorld().getName(), event.getChunk().getX(), event.getChunk().getZ());
    }
}
