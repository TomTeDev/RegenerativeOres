package more.mucho.regenerativeores.ores.mining_blocks;


import more.mucho.regenerativeores.ores.ConfigSerializable;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.inventory.ItemStack;

public interface MiningBlock extends ConfigSerializable{
    ItemStack asItem();
    boolean matchesType(Block block);
    void place(Location location);
}
