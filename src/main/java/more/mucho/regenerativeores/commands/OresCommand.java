package more.mucho.regenerativeores.commands;

import more.mucho.regenerativeores.guis.CreatedOresGui;
import more.mucho.regenerativeores.guis.OreCreateGui;
import more.mucho.regenerativeores.utils.OreItems;
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
        if(!(sender instanceof Player player)){
            sendAlert("Players only command!");
            return false;
        }
        if(args.length==0){
            new CreatedOresGui().open(player);
            return true;
        }
        switch (args[0].toLowerCase()){
            case "create"->{
                Builders.getOreBuilder();
                new OreCreateGui().open(player);
                return true;
            }
        }

        player.getInventory().addItem(OreItems.getOresEditor());
        return false;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String label, String[] args) {
        return List.of();
    }
}
