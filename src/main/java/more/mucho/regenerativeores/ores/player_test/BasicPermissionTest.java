package more.mucho.regenerativeores.ores.player_test;

import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;

public class BasicPermissionTest implements PlayerTest {
    private String permission;
    private String denyMessage;

    public BasicPermissionTest(String permission, String denyMessage) {
        this.permission = permission;
        this.denyMessage = denyMessage;
    }

    @Override
    public boolean test(Player player) {
        return permission!=null&&player.hasPermission(permission);
    }

    @Override
    public String getDenyMessage() {
        return denyMessage;
    }

    @Override
    public void serialize(ConfigurationSection section) {
        section.set("type", "permission");
        section.set("permission", permission);
        section.set("denyMessage", denyMessage);
    }
    public static PlayerTest deserialize(ConfigurationSection section) {
        if(section == null)throw new IllegalArgumentException("Section is null");
        String type = section.getString("type");
        if (type == null) {
            throw new IllegalArgumentException("PlayerTest type is not specified.");
        }

        if (!type.equalsIgnoreCase("permission")) {
            throw new IllegalArgumentException("Unknown PlayerTest type: " + type);
        }
        String permission = section.getString("permission");
        String denyMessage = section.getString("denyMessage");
        return new BasicPermissionTest(permission,denyMessage);
    }
}
