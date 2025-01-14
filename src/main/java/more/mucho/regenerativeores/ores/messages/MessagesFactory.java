package more.mucho.regenerativeores.ores.messages;

import more.mucho.regenerativeores.ores.MiningMessage;
import org.bukkit.configuration.ConfigurationSection;

public class MessagesFactory {

    public static MiningMessage deserialize(ConfigurationSection section) {

        if (section == null) {
            throw new IllegalArgumentException("Section is null");
        }
        String type = section.getString("type");
        if (type == null || !type.equals("basic")) {
            throw new IllegalArgumentException("Unknown message type: " + type);
        }
        return switch (type) {
            case "basic" -> BaseMiningMessage.deserialize(section);
            default -> throw new IllegalArgumentException("Unknown message type: " + type);
        };
    }
}
