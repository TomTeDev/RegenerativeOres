package more.mucho.regenerativeores.ores_2.actions;

import more.mucho.regenerativeores.ores_2.requirements.Requirement;
import org.bukkit.Bukkit;
import org.bukkit.event.block.BlockBreakEvent;

import java.util.Collection;
import java.util.UUID;

public class CommandRunAction extends BaseBreakAction {
    private final String command;

    public CommandRunAction(UUID uuid, int chance, Collection<Requirement> requirements, String command) {
        super(uuid, chance, requirements);
        this.command = command;
    }

    @Override
    public void onAction(BlockBreakEvent event) {
        if (!shouldExecute(event)) return;
        //TODO papi parse;
        Bukkit.dispatchCommand(Bukkit.getConsoleSender(), command);
    }
}
