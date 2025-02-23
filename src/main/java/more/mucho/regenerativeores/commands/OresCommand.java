package more.mucho.regenerativeores.commands;

import more.mucho.regenerativeores.guis.CreatedOresGui;
import more.mucho.regenerativeores.guis.OreCreateGui;
import more.mucho.regenerativeores.ores.OreBuilder;
import more.mucho.regenerativeores.utils.Builders;
import more.mucho.regenerativeores.utils.OreItems;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import net.minecraft.network.chat.Component;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.List;
import java.util.Optional;

public class OresCommand extends BaseCommand {

    public OresCommand() {
        super("ores");
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(!(sender instanceof Player player)){
            sendAlert(sender,"Players only command!");
            return false;
        }
        if(args.length==0){
            new CreatedOresGui().open(player);
            return true;
        }
        switch (args[0].toLowerCase()){
            case "create"->{
                Optional<OreBuilder> oldBuilder = Builders.getOreBuilder(player);
                if(oldBuilder.isPresent()){
                    ComponentBuilder componentBuilder = new ComponentBuilder("Builder created already ").color(ChatColor.YELLOW);
                    TextComponent createNewOne = new TextComponent("[Create NEW ONE!]");
                    createNewOne.setColor(ChatColor.RED);
                    createNewOne.setHoverEvent(
                            new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder("Click").create())
                    );
                    createNewOne.setClickEvent(
                            new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/ores fcreate")
                    );
                    componentBuilder.append(createNewOne);

                    TextComponent openOldOne = new TextComponent("[Open OLD ONE!]");
                    openOldOne.setColor(ChatColor.GREEN);
                    openOldOne.setHoverEvent(
                            new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder("Click").create())
                    );
                    openOldOne.setClickEvent(
                            new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/ores open")
                    );
                    componentBuilder.append(openOldOne);
                    player.spigot().sendMessage(componentBuilder.create());
                    return true;
                }
                OreBuilder oreBuilder = Builders.createOreBuilder(player);
                new OreCreateGui(oreBuilder).open(player);
                return true;
            }
            case "fcreate"->{
                OreBuilder oreBuilder = Builders.createOreBuilder(player);
                new OreCreateGui(oreBuilder).open(player);
                return true;
            }
            case "open"->{
                Optional<OreBuilder> oldBuilder = Builders.getOreBuilder(player);
                if(oldBuilder.isEmpty()){
                    sendAlert(sender,"Builder not found! Use [ /ores create ] first.");
                    return true;
                }
                new OreCreateGui(oldBuilder.get()).open(player);
                return true;
            }
        }

        player.getInventory().addItem(OreItems.getOresEditor());
        return true;
    }

    private final String[] args0 = {"create","open"};

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String label, String[] args) {
        return List.of();
    }
}
