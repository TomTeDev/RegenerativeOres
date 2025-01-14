package more.mucho.regenerativeores.ores;

import more.mucho.regenerativeores.ores.mining_blocks.MiningBlock;
import org.bukkit.configuration.ConfigurationSection;

public interface ConfigSerializable {
    void serialize(ConfigurationSection section);
}
