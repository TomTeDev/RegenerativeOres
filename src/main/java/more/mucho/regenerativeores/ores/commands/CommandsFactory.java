package more.mucho.regenerativeores.ores.commands;

import org.bukkit.configuration.ConfigurationSection;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;

public class CommandsFactory {
    public static List<MiningCommand> commandsFromConfig(@Nullable ConfigurationSection section) {
        if (section == null) throw new IllegalArgumentException("Section is null");
        List<MiningCommand> out = new ArrayList<>();
        for(String key : section.getKeys(false)){
            ConfigurationSection commandSection = section.getConfigurationSection(key);
            if(commandSection != null){
                try {
                    out.add(deserialize(commandSection));
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        }
        return out;
    }
    public static MiningCommand deserialize(@Nullable ConfigurationSection section) {
        if(section == null){
            throw new IllegalArgumentException("Section is null");
        }
        String type = section.getString("type");
        if(type == null){
            throw new IllegalArgumentException("Section does not contain 'type'");
        }
        return switch (type) {
            case "basic" -> BaseMiningCommand.deserialize(section);
            default -> throw new IllegalArgumentException("Unknown command type: " + type);
        };
    }
}
