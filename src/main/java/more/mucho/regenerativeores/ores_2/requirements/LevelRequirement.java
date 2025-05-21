package more.mucho.regenerativeores.ores_2.requirements;

import org.bukkit.entity.Player;

import java.util.UUID;

public class LevelRequirement extends BaseRequirement {
    private final int requiredLevel;

    public LevelRequirement(UUID uuid, int requiredLevel) {
        super(uuid);
        this.requiredLevel = requiredLevel;
    }

    public LevelRequirement(UUID uuid, String message, int requiredLevel) {
        super(uuid, message);
        this.requiredLevel = requiredLevel;
    }

    @Override
    public boolean test(Player miner) {
        boolean value = miner.getLevel() >= requiredLevel;
        if (!value) {
            sendMessage(miner);
        }
        return value;
    }
}
