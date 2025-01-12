package more.mucho.regenerativeores.ores;

import org.bukkit.entity.Player;

public interface MiningCommand {
    boolean test();
    void execute(Player target);
}
