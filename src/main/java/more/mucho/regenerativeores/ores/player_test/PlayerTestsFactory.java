package more.mucho.regenerativeores.ores.player_test;

import org.bukkit.configuration.ConfigurationSection;

import javax.annotation.Nullable;

public class PlayerTestsFactory {

    public static PlayerTest fromConfig(@Nullable ConfigurationSection section) {
        if (section == null) throw new IllegalArgumentException("Section is null");
        String type = section.getString("type");
        if (type == null) {
            throw new IllegalArgumentException("PlayerTest type is not specified.");
        }

        return switch (type.toLowerCase()) {
            case "tool" -> BasicToolTest.deserialize(section);
            case "permission" -> BasicPermissionTest.deserialize(section);
            default -> throw new IllegalArgumentException("Unknown PlayerTest type: " + type);
        };
    }
}
