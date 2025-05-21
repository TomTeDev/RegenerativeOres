package more.mucho.regenerativeores.ores_2.requirements;

import org.bukkit.entity.Player;

import java.util.UUID;

public class PermissionRequirement extends BaseRequirement {
    private final String requiredPermission;

    public PermissionRequirement(UUID uuid, String permission) {
        super(uuid);
        this.requiredPermission = permission;
    }

    public PermissionRequirement(UUID uuid, String message, String permission) {
        super(uuid, message);
        this.requiredPermission = permission;
    }

    @Override
    public boolean test(Player miner) {
        boolean value = miner.hasPermission(requiredPermission);
        if(!value){
            sendMessage(miner);
        }
        return value;
    }
}
