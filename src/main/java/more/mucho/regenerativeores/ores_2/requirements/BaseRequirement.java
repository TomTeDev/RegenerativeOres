package more.mucho.regenerativeores.ores_2.requirements;

import org.bukkit.entity.Player;

import java.util.Optional;
import java.util.UUID;

public abstract class BaseRequirement implements Requirement {
    private final UUID uuid;
    private final Optional<String> message;

    public BaseRequirement(UUID uuid) {
        this(uuid, "");
    }

    public BaseRequirement(UUID uuid, String message) {
        this.uuid = uuid;
        this.message = Optional.ofNullable(message);
    }

    public UUID getUuid() {
        return this.uuid;
    }

    public void sendMessage(Player miner) {
        //TODO papi parse
        message.ifPresent(miner::sendMessage);
    }

}
