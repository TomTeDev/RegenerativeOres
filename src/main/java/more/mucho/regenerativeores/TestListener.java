package more.mucho.regenerativeores;

import more.mucho.regenerativeores.data.OresCache;
import more.mucho.regenerativeores.ores.BasicOre;
import more.mucho.regenerativeores.ores.Ore;
import more.mucho.regenerativeores.ores.OresFactory;
import more.mucho.regenerativeores.ores.Range;
import more.mucho.regenerativeores.ores.mining_blocks.MaterialMiningBlock;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;

public class TestListener implements Listener {


    @EventHandler
    public void onBlockPlace(BlockPlaceEvent event){
        Bukkit.broadcastMessage("E "+event.getBlockPlaced().getType().name());
        if(event.getBlockPlaced().getType() != Material.GRANITE)return;
        event.setCancelled(true);
        Bukkit.broadcastMessage("E1");
        Ore ore = new BasicOre(1,5,new MaterialMiningBlock(Material.GRASS_BLOCK),new MaterialMiningBlock(Material.BEDROCK),
                null,null,null,null,null, OresFactory.craftMiningExp(
                        new Range<>(10,100),100,false
        ));
        Location location =event.getBlockPlaced().getLocation().clone();
        ore.regen(location);
        OresCache.i().addOre(location,ore);
    }
}
