package more.mucho.regenerativeores;

import more.mucho.regenerativeores.ores.Ore;
import org.bukkit.Location;

public interface Regenerator {
    void scheduleRegen(Ore ore, Location location);
}
