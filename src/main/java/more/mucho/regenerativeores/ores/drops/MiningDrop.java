package more.mucho.regenerativeores.ores.drops;

import more.mucho.regenerativeores.ores.ConfigSerializable;
import org.bukkit.Location;
import org.bukkit.entity.Player;

public interface MiningDrop extends ConfigSerializable {
    boolean testDrop();
    void drop(Player player, Location dropLocation);
}
