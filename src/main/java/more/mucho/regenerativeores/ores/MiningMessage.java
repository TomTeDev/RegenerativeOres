package more.mucho.regenerativeores.ores;

import org.bukkit.entity.Player;

public interface MiningMessage extends ConfigSerializable{

    void send(Player target);
}
