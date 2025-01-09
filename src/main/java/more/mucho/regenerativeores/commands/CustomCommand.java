package more.mucho.regenerativeores.commands;

import org.bukkit.command.CommandExecutor;
import org.bukkit.command.TabCompleter;

public interface CustomCommand extends TabCompleter, CommandExecutor {
    String[]getCommands();
}
