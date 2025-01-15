package more.mucho.regenerativeores.commands;

import org.bukkit.command.CommandSender;

public interface MessagesHandler {
    void senDirectMessage(CommandSender target,String message);
    void sendPathMessage(CommandSender target,String path);
}
