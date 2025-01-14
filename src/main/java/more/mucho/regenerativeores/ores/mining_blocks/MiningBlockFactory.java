package more.mucho.regenerativeores.ores.mining_blocks;

import org.bukkit.configuration.ConfigurationSection;

import javax.annotation.Nullable;

public class MiningBlockFactory {
    public static MiningBlock fromConfig(@Nullable ConfigurationSection section) {
        if(section == null)throw new IllegalArgumentException("Section is null");
        String type = section.getString("type");
        if (type == null) {
            throw new IllegalArgumentException("MiningBlock type is not specified.");
        }

        return switch (type.toLowerCase()) {
            case "material" -> MaterialMiningBlock.deserialize(section);
            case "player_head" -> PlayerHeadMiningBlock.deserialize(section);
            default -> throw new IllegalArgumentException("Unknown MiningBlock type: " + type);
        };
    }
}