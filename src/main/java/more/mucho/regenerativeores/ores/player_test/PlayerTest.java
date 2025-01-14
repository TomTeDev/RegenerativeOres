package more.mucho.regenerativeores.ores.player_test;

import more.mucho.regenerativeores.ores.ConfigSerializable;
import org.bukkit.entity.Player;

public interface PlayerTest extends ConfigSerializable {
    boolean test(Player player);
    String getDenyMessage();
}
