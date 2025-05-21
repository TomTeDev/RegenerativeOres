package more.mucho.regenerativeores.ores_2.requirements;

import org.bukkit.entity.Player;

public interface Requirement {
    boolean test(Player miner);
}
