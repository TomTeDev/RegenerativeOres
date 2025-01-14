package more.mucho.regenerativeores.ores;

import more.mucho.regenerativeores.ores.mining_blocks.MiningBlock;
import org.bukkit.Location;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;

public interface Ore {
    int getID();
    int getDelay();
    MiningBlock getMaterial();
    MiningBlock getReplacement();
    void mine(Player miner,Location location);
    void regen(Location location);
    void replace(Location location);
    boolean serialize (ConfigurationSection section);



}
