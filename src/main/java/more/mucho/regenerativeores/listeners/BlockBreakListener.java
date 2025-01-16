package more.mucho.regenerativeores.listeners;

import more.mucho.regenerativeores.data.OresCacheImpl;
import more.mucho.regenerativeores.guis.OreGui;
import more.mucho.regenerativeores.ores.Ore;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.player.PlayerInteractEvent;

import java.util.Optional;

public class BlockBreakListener implements Listener {
    private final OresCacheImpl oresCache;
    public BlockBreakListener(OresCacheImpl oresCache) {
        this.oresCache = oresCache;
    }
    @EventHandler
    public void onBlockBreak(BlockBreakEvent event){
        if(event.isCancelled())return;
        Optional<Ore> ore = oresCache.getOre(event.getBlock().getLocation());
        if(ore.isEmpty())return;
        event.setCancelled(true);
        boolean mined = ore.get().mine(event.getPlayer(),event.getBlock().getLocation());

    }
    @EventHandler
    public void onInteract(PlayerInteractEvent event){
        if(event.getAction()!= Action.RIGHT_CLICK_BLOCK)return;
        if(event.getPlayer().getInventory().getItemInMainHand().getType()!= Material.STICK)return;
        new OreGui().open(event.getPlayer());
    }
}
