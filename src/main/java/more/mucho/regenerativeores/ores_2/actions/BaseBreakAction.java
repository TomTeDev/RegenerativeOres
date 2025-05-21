package more.mucho.regenerativeores.ores_2.actions;

import more.mucho.regenerativeores.ores_2.requirements.Requirement;
import org.bukkit.entity.Player;
import org.bukkit.event.block.BlockBreakEvent;

import java.util.Collection;
import java.util.UUID;

public abstract class BaseBreakAction extends BaseAction<BlockBreakEvent> {
    protected BaseBreakAction(UUID uuid, int chance, Collection<Requirement> requirements) {
        super(BlockBreakEvent.class, uuid, chance,requirements);
    }
    protected BaseBreakAction(UUID uuid, int chance) {
        super(BlockBreakEvent.class, uuid, chance);
    }

    @Override
    protected Player extractPlayer(BlockBreakEvent event) {
        return event.getPlayer();
    }

}
