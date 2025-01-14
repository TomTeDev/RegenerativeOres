package more.mucho.regenerativeores.ores.commands;

import more.mucho.regenerativeores.ores.ConfigSerializable;
import org.bukkit.entity.Player;

public interface MiningCommand extends ConfigSerializable {
    boolean test();
    void execute(Player target);
}
