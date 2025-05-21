package more.mucho.regenerativeores.ores_2.actions;

import more.mucho.regenerativeores.ores_2.requirements.Requirement;
import more.mucho.regenerativeores.utils.NumberRange;
import org.bukkit.event.block.BlockBreakEvent;

import java.util.Collection;
import java.util.Collections;
import java.util.UUID;

public class ExperienceDropAction extends BaseBreakAction {

    private final NumberRange experience;

    public ExperienceDropAction(UUID uuid, int chance, NumberRange experience) {
        this(uuid, chance, Collections.emptyList(), experience);
    }

    public ExperienceDropAction(UUID uuid, int chance, Collection<Requirement> requirements, NumberRange experience) {
        super(uuid, chance, requirements);
        this.experience = experience;
    }

    @Override
    public void onAction(BlockBreakEvent event) {
        if (!shouldExecute(event)) return;
        event.getPlayer().giveExp((int) experience.roll());
    }
}
