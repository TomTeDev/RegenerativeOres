package more.mucho.regenerativeores.ores_2.actions;

import more.mucho.regenerativeores.ores_2.requirements.Requirement;
import org.bukkit.event.block.BlockBreakEvent;

import java.util.Collection;
import java.util.UUID;

public class MessageAction extends BaseBreakAction {

    private final String message;


    public MessageAction(UUID uuid, int chance, Collection<Requirement> requirements, String message) {
        super(uuid, chance, requirements);
        this.message = message;
    }

    @Override
    public void onAction(BlockBreakEvent event) {
        if (!shouldExecute(event)) return;
        //TODO papi parse;
        event.getPlayer().sendMessage(message);
    }
}
