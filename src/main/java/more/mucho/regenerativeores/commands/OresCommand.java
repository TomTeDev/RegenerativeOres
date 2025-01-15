package more.mucho.regenerativeores.commands;

import more.mucho.regenerativeores.guis.CreatedOresGui;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.List;

public class OresCommand extends AbstractCommand {

    public OresCommand(MessagesHandler messagesHandler, String... commandNames) {
        super(messagesHandler, commandNames);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(!(sender instanceof Player player))return false;
        new CreatedOresGui().open(player);
        return false;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String label, String[] args) {
        return List.of();
    }
}
