package more.mucho.regenerativeores.ores_2.actions;

import more.mucho.regenerativeores.ores_2.requirements.Requirement;
import org.bukkit.event.block.BlockBreakEvent;

import java.util.Collection;
import java.util.UUID;

public class ParticleBlockAction extends BaseBreakAction {


    public ParticleBlockAction(UUID uuid, int chance, Collection<Requirement> requirements) {
        super(uuid, chance, requirements);
    }

    @Override
    public void onAction(BlockBreakEvent event) {
        if (!shouldExecute(event)) return;

    }
}
