package more.mucho.regenerativeores.ores;

import org.bukkit.Location;
import org.bukkit.entity.Player;

public interface MiningExp {
    Range<Integer, Integer> getRage();
    int getDropChance();
    boolean testDrop();
    boolean isDirectInventoryDrop();
    void addExp(Player target, Location location);
}
