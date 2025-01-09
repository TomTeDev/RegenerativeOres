package more.mucho.regenerativeores.listeners;

import more.mucho.regenerativeores.data.OresCache;
import more.mucho.regenerativeores.guis.OreGui;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.player.PlayerInteractEvent;

public class BlockBreakListener implements Listener {
    private final OresCache oresCache;
    public BlockBreakListener(OresCache oresCache) {
        this.oresCache = oresCache;
    }
    @EventHandler
    public void onBlockBreak(BlockBreakEvent event){
        oresCache.getOre(event.getPlayer().getLocation()).ifPresent(ore -> ore.mine(event.getPlayer()));
    }
    @EventHandler
    public void onInteract(PlayerInteractEvent event){
        if(event.getAction()!= Action.RIGHT_CLICK_BLOCK)return;
        if(event.getPlayer().getInventory().getItemInMainHand().getType()!= Material.STICK)return;
        new OreGui().open(event.getPlayer());
    }
}
