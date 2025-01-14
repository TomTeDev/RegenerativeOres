package more.mucho.regenerativeores.ores.mining_blocks;


import more.mucho.regenerativeores.ores.ConfigSerializable;
import org.bukkit.Location;

public interface MiningBlock extends ConfigSerializable{
    void place(Location location);
}
