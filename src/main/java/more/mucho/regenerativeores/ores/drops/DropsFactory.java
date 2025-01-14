package more.mucho.regenerativeores.ores.drops;

import org.bukkit.configuration.ConfigurationSection;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;

public class DropsFactory {

    public static List<MiningDrop> dropsFromConfig(@Nullable ConfigurationSection section) {
        if (section == null) throw new IllegalArgumentException("Section is null");
        List<MiningDrop> out = new ArrayList<>();
        for(String key : section.getKeys(false)){
            ConfigurationSection dropSection = section.getConfigurationSection(key);
            if(dropSection != null){
                out.add(deserialize(dropSection));
            }
        }
        return out;
    }
    public static MiningDrop deserialize(ConfigurationSection section) {
        if(section == null){
            throw new IllegalArgumentException("Section is null");
        }

        String type = section.getString("type");
        if(type == null){
            throw new IllegalArgumentException("Section does not contain 'type'");
        }
        return switch (type) {
            case "expDrop" -> BaseExpDrop.deserialize(section);
            case "itemDrop"-> BaseItemDrop.deserialize(section);
            default -> throw new IllegalArgumentException("Unknown drop type: " + type);
        };

    }
}
