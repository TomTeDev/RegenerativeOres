package more.mucho.regenerativeores.commands;

import org.bukkit.command.CommandSender;

public interface MessagesHandler {
    void sendMessage(CommandSender target,String text);
}
